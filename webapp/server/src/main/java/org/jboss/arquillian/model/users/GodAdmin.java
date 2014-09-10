package org.jboss.arquillian.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="GOD_ADMIN")
@DiscriminatorValue("GOD_ADMIN")
public class GodAdmin extends User {
    
}
