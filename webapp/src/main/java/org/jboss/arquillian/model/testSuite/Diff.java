package org.jboss.arquillian.model.testSuite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author jhuska
 */
@Entity(name = "DIFF")
public class Diff {

    public static final int STRING_COLUMN_LENGTH = 5000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIFF_ID")
    private Long diffID;

    @Column(name = "DIFF_NAME", length = STRING_COLUMN_LENGTH)
    private String name;

    @Column(name = "URL_SCREENSHOT", unique = true, length = STRING_COLUMN_LENGTH)
    private String urlOfScreenshot;

    @ManyToOne
    @JoinColumn(name = "TEST_SUITE_RUN_ID")
    @JsonBackReference(value = "test-suite-run-diff")
    private TestSuiteRun testSuiteRun;

    @OneToOne
    @JoinColumn(name = "SAMPLE_ID")
    private Sample sample;

    @ManyToOne
    @JoinColumn(name = "PATTERN_ID")
    @JsonBackReference(value = "pattern-diff")
    private Pattern pattern;

    public Long getDiffID() {
        return diffID;
    }

    public void setDiffID(Long diffID) {
        this.diffID = diffID;
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

    public TestSuiteRun getTestSuiteRun() {
        return testSuiteRun;
    }

    public void setTestSuiteRun(TestSuiteRun testSuiteRun) {
        this.testSuiteRun = testSuiteRun;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.urlOfScreenshot);
        hash = 47 * hash + Objects.hashCode(this.testSuiteRun);
        hash = 47 * hash + Objects.hashCode(this.sample);
        hash = 47 * hash + Objects.hashCode(this.pattern);
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
        final Diff other = (Diff) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.urlOfScreenshot, other.urlOfScreenshot)) {
            return false;
        }
        if (!Objects.equals(this.testSuiteRun, other.testSuiteRun)) {
            return false;
        }
        if (!Objects.equals(this.sample, other.sample)) {
            return false;
        }
        if (!Objects.equals(this.pattern, other.pattern)) {
            return false;
        }
        return true;
    }
}
