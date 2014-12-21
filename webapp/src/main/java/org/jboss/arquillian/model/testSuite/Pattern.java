package org.jboss.arquillian.model.testSuite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

    @Column(name = "PATTERN_NAME", length = Diff.STRING_COLUMN_LENGTH)
    private String name;

    @Column(name = "URL_SCREENSHOT", unique = true, length = Diff.STRING_COLUMN_LENGTH)
    private String urlOfScreenshot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_SUITE_ID")
    @JsonBackReference(value = "test-suite-patterns")
    private TestSuite testSuite;

    @OneToMany(mappedBy = "pattern", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "pattern-diff")
    private List<Diff> diffs;

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

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.urlOfScreenshot);
        hash = 23 * hash + Objects.hashCode(this.testSuite);
        hash = 23 * hash + Objects.hashCode(this.diffs);
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
        if (!Objects.equals(this.diffs, other.diffs)) {
            return false;
        }
        return true;
    }
}
