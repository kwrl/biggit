package com.kaucar.biggit.biggit;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.kaucar.biggit.biggit.models.BitBucketRepository;
import com.kaucar.biggit.biggit.models.GithubRepository;
import com.kaucar.biggit.biggit.models.Repository;
import com.kaucar.biggit.biggit.services.GitService;
import com.kaucar.biggit.biggit.services.PublicBitbucketService;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Configuration cfg = new Configuration()
				.addAnnotatedClass(Repository.class)
				.addAnnotatedClass(BitBucketRepository.class)
				.addAnnotatedClass(GithubRepository.class)
				.configure();
		SessionFactory factory = cfg.buildSessionFactory();

		EntityManager entityManager = factory.createEntityManager();
		GitService service = new PublicBitbucketService();
		for (Repository repo : service.getRepositories()) {
			entityManager.getTransaction().begin();
			System.out.println("Starting:\t:" + repo);
			entityManager.persist(repo);
			System.out.println("Done:\t:" + repo);
			entityManager.getTransaction().commit();
		}
		entityManager.close();
	}
}
