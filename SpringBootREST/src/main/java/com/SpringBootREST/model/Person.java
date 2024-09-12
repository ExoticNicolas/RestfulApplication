package com.SpringBootREST.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name",length = 80)
	private String firstName;
	@Column(name = "last_name", length = 80)
	private String lastName;
	@Column(name = "address", length = 100)
	private String address;
	@Column(name = "gender",length = 6)
	private String gender;
	@Column(nullable = false)
	private Boolean enabled;

	public Person() {

	}

	public Person(Long id, String firstName, String lastName, String address, String gender, Boolean enabled) {
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
		Person other = (Person) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(enabled, other.enabled);
	}


}
