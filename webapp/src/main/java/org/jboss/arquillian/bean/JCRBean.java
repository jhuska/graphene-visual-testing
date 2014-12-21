package org.jboss.arquillian.bean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Named("jcrBean")
@RequestScoped
public class JCRBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JCRBean.class.getSimpleName());

    @Resource(mappedName = "java:/jcr/graphene-visual-testing")
    private javax.jcr.Repository repository;
    
    public Session getSession() throws RepositoryException {
        //TODO retrieve credentials from app authentisation system
        char[] password = {'g', 'r', 'a', 'p', 'h', 'e', 'n', 'e', '-', 'v', 'i', 's', 'u', 'a', 'l', '-', 't', 'e', 's', 't', 'i', 'n', 'g'};
        return repository.login(new SimpleCredentials("graphene-visual-testing", password));
    }

    public void removeTestSuite(String testSuiteName) {
        Session session;
        try {
            session = getSession();
            session.getRootNode().getNode(testSuiteName).remove();
            session.save();
        } catch (RepositoryException ex) {
            Logger.getLogger(JCRBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeTestSuiteRun(String timestampOfRun, String testSuiteName) {
        Session session;
        try {
            session = getSession();
            Node testSuiteRunNode = session.getRootNode()
                    .getNode(testSuiteName)
                    .getNode("runs")
                    .getNode(timestampOfRun);
            testSuiteRunNode.remove();
            session.save();
        }
        catch (RepositoryException ex) {
            Logger.getLogger(JCRBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changePatternForSample(String testSuiteName, String timestampOfRun, String sampleName) {
        Session session;
        try {
            session = getSession();
            Node sample = session.getRootNode()
                            .getNode(testSuiteName)
                            .getNode("runs")
                            .getNode(timestampOfRun)
                            .getNode("samples")
                            .getNode(sampleName);
            Node pattern = session.getRootNode()
                            .getNode(testSuiteName)
                            .getNode("patterns")
                            .getNode(sampleName);
            String patternPath = pattern.getPath();
            pattern.remove();
            session.save();
            session.move(sample.getPath(), patternPath);
            session.save();
        }
        catch (RepositoryException ex) {
            Logger.getLogger(JCRBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
