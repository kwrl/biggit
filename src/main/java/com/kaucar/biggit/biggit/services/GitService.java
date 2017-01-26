package com.kaucar.biggit.biggit.services;

import java.util.Iterator;
import java.util.stream.Stream;

import com.kaucar.biggit.biggit.models.Commit;
import com.kaucar.biggit.biggit.models.Repository;

public interface GitService {
	Repository getNextRepository();
	Stream<? extends Commit> getCommits(Repository repo);
	
	default Iterable<Repository> getRepositoryIterable() {
		return new Iterable<Repository>() {
			@Override
			public Iterator<Repository> iterator() {
				return new Iterator<Repository> () {

					@Override
					public boolean hasNext() {
						return true;
					}

					@Override
					public Repository next() {
						return getNextRepository();
					}
				};
			}
		};
	}
}
