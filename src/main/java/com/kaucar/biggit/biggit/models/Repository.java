package com.kaucar.biggit.biggit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Repositories")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public interface Repository {
	@Column(name="FULL_NAME")
	String getFullName();
	void setFullName(String fullName);
	
	String getName();
	void setName(String name);
	
	int getId();
	
	void setId(int id);

	@Id
	@GeneratedValue(generator="increment", strategy=GenerationType.TABLE)
	@GenericGenerator(name="increment", strategy="increment")
	int getInternalId();
	void setInternalId(int internalId);
}
