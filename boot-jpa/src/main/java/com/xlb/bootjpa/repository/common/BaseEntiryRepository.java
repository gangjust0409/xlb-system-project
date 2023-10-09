package com.xlb.bootjpa.repository.common;

import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@NoRepositoryBean
public class BaseEntiryRepository {

    @PersistenceContext
    protected EntityManager entityManager;

}
