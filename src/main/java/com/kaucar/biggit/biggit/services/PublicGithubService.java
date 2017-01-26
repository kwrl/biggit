package com.kaucar.biggit.biggit.services;

import java.io.IOException;
import java.util.Deque;
import java.util.stream.Stream;

import com.kaucar.biggit.biggit.models.Commit;
import com.kaucar.biggit.biggit.models.GithubCommit;
import com.kaucar.biggit.biggit.models.GithubRepository;
import com.kaucar.biggit.biggit.models.Repository;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PublicGithubService implements GitService {
	private String baseURL = "https://api.github.com/";
	private GithubRetrofitService retrofitService = new Retrofit.Builder().baseUrl(baseURL)
			.addConverterFactory(GsonConverterFactory.create()).build().create(GithubRetrofitService.class);

	private Deque<GithubRepository> fetchedRepositories = null;
	private int lastId = 0;

	public synchronized GithubRepository getNextRepository() {
		if (fetchedRepositories == null || fetchedRepositories.isEmpty()) {
			try {
				fetchedRepositories = retrofitService.getRepositories(lastId).execute().body();
				lastId = fetchedRepositories.getLast().getId();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fetchedRepositories.poll();
	}

	public synchronized Stream<? extends Commit> getCommits(Repository repo) {
		try {
			return retrofitService.getCommits(repo.getId()).execute().body().stream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}

	private interface GithubRetrofitService {
		@GET("/repositories")
		Call<Deque<GithubRepository>> getRepositories(@Query("since") int since);
		@GET("/repositories/{id}/commits")
		Call<Deque<GithubCommit>> getCommits(@Path("id") int id);
	}

}
