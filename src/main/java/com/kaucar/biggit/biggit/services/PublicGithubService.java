package com.kaucar.biggit.biggit.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kaucar.biggit.biggit.PageIterator;
import com.kaucar.biggit.biggit.models.Commit;
import com.kaucar.biggit.biggit.models.GithubCommit;
import com.kaucar.biggit.biggit.models.GithubRepository;
import com.kaucar.biggit.biggit.models.Repository;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PublicGithubService implements GitService {
	private String baseURL = "https://api.github.com/";
	private GithubRetrofitService retrofitService;

	public PublicGithubService() {
		Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseURL)
			.addConverterFactory(GsonConverterFactory.create());
		
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(new BasicAuthenticationInterceptor("kwrl", "a7a816c659beda56aaaf4b803aab547ce4a22c50"));
		retrofitService = builder.client(httpClient.build()).build().create(GithubRetrofitService.class);
	}
	
	public CommitService getCommits(Repository repo) {
		return new CommitService() {
			@Override
			public PageIterator<Commit> iterator() {
				return new PageIterator<Commit>() {
					private int page = 0;

					@Override
					protected List<GithubCommit> fetchItems() throws Exception {
						return retrofitService.getCommits(repo.getId(), page++).execute().body();
					}
				};
			}
		};
	}

	@Override
	public RepositoryService getRepositories() {
		return new RepositoryService() {
			@Override
			public PageIterator<Repository> iterator() {
				return new PageIterator<Repository>() {
					private int lastId = 0;

					@Override
					protected List<GithubRepository> fetchItems() throws Exception {
						 List<GithubRepository> items = retrofitService.getRepositories(lastId).execute().body();

						 if(items == null || items.isEmpty() ) {
							 return new ArrayList<>(0);
						 }

						 lastId = items.get(items.size()-1).getId();
						 return items;
					}
				};
			}
		};
	}

	private interface GithubRetrofitService {
		@GET("/repositories")
		Call<List<GithubRepository>> getRepositories(@Query("since") int since);

		@GET("/repositories/{id}/commits")
		Call<List<GithubCommit>> getCommits(@Path("id") int id, @Query("page") int page);
	}
	
	private class BasicAuthenticationInterceptor implements Interceptor {
		private final String userName;
	    private final String password;

	    public BasicAuthenticationInterceptor(String userName, String password) {
	        this.userName = userName;
	        this.password = password;
	    }

	    @Override
	    public Response intercept(Chain chain) throws IOException {
	        final Request request = chain.request()
	                .newBuilder()
	                .addHeader("Authorization", Credentials.basic(userName, password))
	                .build();

	        return chain.proceed(request);
	    }	
	}
}
