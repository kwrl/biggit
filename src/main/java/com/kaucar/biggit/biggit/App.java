package com.kaucar.biggit.biggit;

import com.kaucar.biggit.biggit.models.Repository;
import com.kaucar.biggit.biggit.services.PublicGithubService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	PublicGithubService ghService = new PublicGithubService();
    	for(Repository repo : ghService.getRepositoryIterable()) {
    		ghService.getCommits(repo).forEach(System.out::println);
    	}
    }
}
