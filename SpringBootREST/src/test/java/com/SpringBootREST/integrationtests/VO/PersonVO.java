package com.SpringBootREST.integrationtests.VO;

import java.io.Serializable;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
	private Boolean enabled;

	public PersonVO() {

	}

	public PersonVO(Long id, String firstName, String lastName, String address, String gender, Boolean enabled) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, gender, id, lastName, enabled);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		PersonVO other = (PersonVO) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(enabled, other.enabled);
	}



	@Override
	public String toString() {
		return "PersonVOCORS [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", gender=" + gender + ", enabled=" + enabled + "]";
	}



}
