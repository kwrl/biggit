package com.kaucar.biggit.biggit.services;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.kaucar.biggit.biggit.PageIterator;
import com.kaucar.biggit.biggit.models.BitBucketCommit;
import com.kaucar.biggit.biggit.models.BitBucketRepository;
import com.kaucar.biggit.biggit.models.Commit;
import com.kaucar.biggit.biggit.models.Repository;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PublicBitbucketService implements GitService {
	private String baseURL = "https://api.bitbucket.org/";
	private BitBucketRetrofitService retrofitService = new Retrofit.Builder().baseUrl(baseURL)
			.addConverterFactory(GsonConverterFactory.create()).build().create(BitBucketRetrofitService.class);
	
	@Override
	public RepositoryService getRepositories() {
		return new RepositoryService() {
			
			@Override
			public Iterator<Repository> iterator() {
				return new PageIterator<Repository>() {
					private String next = null;
					@Override
					protected List<? extends Repository> fetchItems() throws Exception {
						ResponsePage<BitBucketRepository> response = retrofitService.getRepositoriesPage(next).execute().body();
						next = URLDecoder.decode(response.getNext().split("after=")[1], "UTF-8");
						if(response.getValues() == null) {
							return Collections.emptyList();
						}
						return response.getValues();
					}
				};
			}
		};
	}

	@Override
	public CommitService getCommits(Repository repo) {
		return new CommitService() {
			@Override
			public Iterator<Commit> iterator() {
				return new PageIterator<Commit>() {
					private int page = 1;
					@Override
					protected List<? extends Commit> fetchItems() throws Exception {
						Response<ResponsePage<BitBucketCommit>> res = retrofitService.getCommits(repo.getFullName(), page++).execute();
						ResponsePage<BitBucketCommit> response = res.body();
						if(response == null || response.getValues() == null ) {
							return Collections.emptyList();
						}
						return response.getValues();
					}
				};
			}
		};
	}
	

	private class ResponsePage<T> {
		
		@SerializedName("next")
		private String next;

		@SerializedName("values")
		List<T> values;

		public String getNext() {
			return next;
		}

		public void setNext(String next) {
			this.next = next;
		}

		public List<T> getValues() {
			return values;
		}

		public void setValues(List<T> values) {
			this.values = values;
		}
	}

	private interface BitBucketRetrofitService {
		@GET("/2.0/repositories")
		Call<ResponsePage<BitBucketRepository>> getRepositoriesPage(@Query("after") String after);

		@GET("/2.0/repositories/{repositoryFullName}/commits")
		Call<ResponsePage<BitBucketCommit>> getCommits(@Path("repositoryFullName") String repositoryFullName, @Query("page") int page);
	}

}
