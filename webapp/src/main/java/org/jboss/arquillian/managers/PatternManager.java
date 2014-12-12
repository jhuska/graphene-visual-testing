package org.jboss.arquillian.managers;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.jboss.arquillian.model.testSuite.Pattern;

/**
 *
 * @author jhuska
 */
@Stateless
public class PatternManager {

    @Inject
    private EntityManager em;
    
    public Pattern createPattern(Pattern pattern) {
        em.persist(pattern);
        return pattern;
    }
    
    public List<Pattern> getTestSuite(Long testSuiteID) {
        Query query = em.createQuery("SELECT e FROM PATTERN e WHERE e.testSuite.testSuiteID = :testSuiteID");
        query.setParameter("testSuiteID", testSuiteID);
        return query.getResultList();
    }
}
