package org.jboss.arquillian.managers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.model.users.UserAccount;

@Stateful
public class UserAccountManager {

    @PersistenceContext
    private EntityManager em;

    public UserAccount findById(String login) {
        return em.find(UserAccount.class, login);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
