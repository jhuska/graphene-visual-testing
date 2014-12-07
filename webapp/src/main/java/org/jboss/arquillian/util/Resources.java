package org.jboss.arquillian.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext
    private EntityManager em;

}
