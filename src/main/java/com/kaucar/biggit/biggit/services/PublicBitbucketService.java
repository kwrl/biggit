package com.kaucar.biggit.biggit.services;

import java.util.List;
import java.util.stream.Stream;

import com.google.gson.annotations.SerializedName;
import com.kaucar.biggit.biggit.models.BitBucketRepository;
import com.kaucar.biggit.biggit.models.Commit;
import com.kaucar.biggit.biggit.models.Repository;

public class PublicBitbucketService implements GitService {

	@Override
	public Repository getNextRepository() {
		return null;
	}

	@Override
	public Stream<? extends Commit> getCommits(Repository repo) {
		return null;
	}
	
	private class RepositoriesPage {
		private String next;
		
		@SerializedName("values")
		List<BitBucketRepository> repositories;
	}

	private interface BitBucketRetrofitService {
	}
	
}
