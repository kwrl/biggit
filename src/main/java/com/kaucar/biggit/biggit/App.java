package com.kaucar.biggit.biggit;

import com.kaucar.biggit.biggit.models.Commit;
import com.kaucar.biggit.biggit.models.Repository;
import com.kaucar.biggit.biggit.services.GitService;
import com.kaucar.biggit.biggit.services.PublicBitbucketService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	GitService service = new PublicBitbucketService();
    	for(Repository repo : service.getRepositories()) {
    		for(Commit commit : service.getCommits(repo)) {
    			System.out.println(commit);
    		}
    		System.out.println(repo);
    	}
    }
}
