package org.jboss.arquillian.managers;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.jboss.arquillian.model.testSuite.TestSuite;

/**
 *
 * @author jhuska
 */
@ApplicationScoped
public class TestSuiteManager {
    
    @Inject
    private EntityManager em;

    public TestSuite findById(long id) {
        return em.find(TestSuite.class, id);
    }
    
    public List<TestSuite> getAllTestSuites() {
        Query query = em.createQuery("SELECT e FROM TEST_SUITE e");
        List<TestSuite> result = query.getResultList();
        return result;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}