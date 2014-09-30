package org.jboss.arquillian.model.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "CONTACT_INFO")
public class ContactInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long contactInfoId;

	private String value;

	@Override
	public String toString() {
		return "ContactInfo [value=" + value + "]";
	}

	public long getId() {
		return contactInfoId;
	}

	public void setId(long id) {
		this.contactInfoId = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
