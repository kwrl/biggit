package com.kaucar.biggit.biggit.models;

public class GithubCommit implements Commit {
	private CommitSummary commit;

	public CommitSummary getCommit() {
		return commit;
	}

	public void setCommit(CommitSummary commit) {
		this.commit = commit;
	}

	@Override
	public String getMessage() {
		return commit.getMessage();
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
	
	class CommitSummary {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
