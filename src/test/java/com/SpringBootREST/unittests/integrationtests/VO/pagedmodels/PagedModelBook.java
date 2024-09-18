package com.SpringBootREST.unittests.integrationtests.VO.pagedmodels;

import java.util.List;

import com.SpringBootREST.unittests.integrationtests.VO.BookVO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelBook {
	
	@XmlElement
	List<BookVO> content;

	public PagedModelBook() {
		super();
	}

	public List<BookVO> getContent() {
		return content;
	}

	public void setContent(List<BookVO> content) {
		this.content = content;
	}

}
