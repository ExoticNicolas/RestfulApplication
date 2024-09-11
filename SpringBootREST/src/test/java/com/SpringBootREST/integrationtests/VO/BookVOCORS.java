package com.SpringBootREST.integrationtests.VO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BookVOCORS implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String author;
	private Date launchDate;
	private Double price;
	private String title;

	public BookVOCORS() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, id, launchDate, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		BookVOCORS other = (BookVOCORS) obj;
		return Objects.equals(author, other.author) && Objects.equals(id, other.id)
				&& Objects.equals(launchDate, other.launchDate) && Objects.equals(price, other.price)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "BookVOCORS [id=" + id + ", author=" + author + ", launchDate=" + launchDate + ", price=" + price
				+ ", title=" + title + "]";
	}

}
