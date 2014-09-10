package org.jboss.arquillian.model.users;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "USER_ACCOUNT")
public class UserAccount {

	@Id
	@Size(min = 6, max = 30)
	private String login;

	@NotNull
	private Byte[] password;

	@OneToOne(optional = false)
	@JoinColumn(name = "contactInfoId")
	private ContactInfo contactInfo;

	@OneToOne(optional = false)
	@JoinColumn(name = "userId")
	private User user;

	public UserAccount() {
	}

	public UserAccount(String login, Byte[] password, ContactInfo contactInfo,
			User user) {
		this.login = login;
		this.password = password;
		this.contactInfo = contactInfo;
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserAccount [login=" + login + ", password="
				+ Arrays.toString(password) + ", contactInfo=" + contactInfo
				+ ", user=" + user + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Byte[] getPassword() {
		return password;
	}

	public void setPassword(Byte[] password) {
		this.password = password;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactInfo == null) ? 0 : contactInfo.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (contactInfo == null) {
			if (other.contactInfo != null)
				return false;
		} else if (!contactInfo.equals(other.contactInfo))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (!Arrays.equals(password, other.password))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
