package org.jboss.arquillian.model.testSuite;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author jhuska
 */
@Entity(name = "PATTERN")
public class Pattern {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATTERN_ID")
    private Long patternID;
    
    @Column(name = "PATTERN_NAME", unique=true)
    private String name;
    
    @Column(name = "URL_SCREENSHOT", unique=true)
    private String urlOfScreenshot;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_SUITE_ID")
//    @JsonManagedReference
    private TestSuite testSuite;

    public Long getPatternID() {
        return patternID;
    }

    public void setPatternID(Long patternID) {
        this.patternID = patternID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlOfScreenshot() {
        return urlOfScreenshot;
    }

    public void setUrlOfScreenshot(String urlOfScreenshot) {
        this.urlOfScreenshot = urlOfScreenshot;
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(TestSuite testSuite) {
        this.testSuite = testSuite;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.urlOfScreenshot);
        hash = 17 * hash + Objects.hashCode(this.testSuite);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pattern other = (Pattern) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.urlOfScreenshot, other.urlOfScreenshot)) {
            return false;
        }
        if (!Objects.equals(this.testSuite, other.testSuite)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pattern{" + "patternID=" + patternID + ", name=" + name + ", urlOfScreenshot=" + urlOfScreenshot + ", testSuite=" + testSuite + '}';
    }
    
}
