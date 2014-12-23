package org.jboss.arquillian.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author jhuska
 */
@SessionScoped
public class BasicAuthSessionStore implements Serializable {

    private String login;

    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
