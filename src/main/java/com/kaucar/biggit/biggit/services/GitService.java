package com.kaucar.biggit.biggit.services;

import com.kaucar.biggit.biggit.models.Repository;

public interface GitService {
	RepositoryService getRepositories();
	CommitService getCommits(Repository repo);
}
