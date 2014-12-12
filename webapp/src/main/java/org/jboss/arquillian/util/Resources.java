package org.jboss.arquillian.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class Resources {

    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager em;

}
