package org.jboss.arquillian.jcr;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Named("cdiController")
@RequestScoped
public class CDIController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(CDIController.class.getSimpleName());

    @Resource(mappedName = "java:/jcr/graphene-visual-testing")
    private javax.jcr.Repository repository;

    public void storeSomePictures2() throws RepositoryException {
        char[] password = {'g', 'r', 'a', 'p', 'h', 'e', 'n', 'e', '-', 'v', 'i', 's', 'u', 'a', 'l', '-', 't', 'e', 's', 't', 'i', 'n', 'g'};
        Session session = repository.login(new SimpleCredentials("graphene-visual-testing", password));
        InputStream stream = new BufferedInputStream(getClass().getResourceAsStream("/white_rose.jpg"));
        Node rootNode = session.getRootNode();
        
        Node file = rootNode.addNode("whiterose.jpg", "nt:file");
        Node content = file.addNode("jcr:content", "nt:resource");
        Binary binary = session.getValueFactory().createBinary(stream);
        content.setProperty("jcr:data", binary);
        content.setProperty("jcr:mimeType", "image/gif");
        session.save();
        
    }

}
