package com.SpringBootREST.integrationtests.controller.withXML;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.SpringBootREST.configs.TestsConfigs;
import com.SpringBootREST.integrationtests.VO.AccountCredentialsVO;
import com.SpringBootREST.integrationtests.VO.BookVO;
import com.SpringBootREST.integrationtests.VO.PersonVO;
import com.SpringBootREST.integrationtests.VO.TokenVO;
import com.SpringBootREST.integrationtests.VO.pagedmodels.PagedModelBook;
import com.SpringBootREST.integrationtests.VO.pagedmodels.PagedModelPerson;
import com.SpringBootREST.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import jakarta.xml.bind.JAXBException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerXmlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static XmlMapper objectMapper;

	private static BookVO book;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@BeforeAll
	public static void setup() {
		objectMapper = new XmlMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		book = new BookVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException, JAXBException {
		
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
	     
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestsConfigs.SERVER_PORT)
					.contentType(TestsConfigs.CONTENT_TYPE_XML)
					.accept(TestsConfigs.CONTENT_TYPE_XML)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestsConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/book/v1")
				.setPort(TestsConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException, ParseException {
		mockBook();
		
		var content = given().spec(specification)
				.contentType(TestsConfigs.CONTENT_TYPE_XML)
				.accept(TestsConfigs.CONTENT_TYPE_XML)
					.body(book)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());
		
		assertTrue(persistedBook.getId() > 0);
		
		assertEquals("Michael", persistedBook.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), persistedBook.getLaunchDate());
		assertEquals(5.00, persistedBook.getPrice());
		assertEquals("Teste", persistedBook.getTitle());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException, ParseException {
		book.setPrice(7.00);
		
		var content = given().spec(specification)
				.contentType(TestsConfigs.CONTENT_TYPE_XML)
				.accept(TestsConfigs.CONTENT_TYPE_XML)
					.body(book)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());
		
		assertEquals(book.getId(), persistedBook.getId());
		
		assertEquals("Michael", persistedBook.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), persistedBook.getLaunchDate());
		assertEquals(7.00, persistedBook.getPrice());
		assertEquals("Teste", persistedBook.getTitle());
	}
	
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException, ParseException {
		mockBook();
			
		var content = given().spec(specification)
				.contentType(TestsConfigs.CONTENT_TYPE_XML)
				.accept(TestsConfigs.CONTENT_TYPE_XML)
					.pathParam("id", book.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());

		assertEquals(book.getId(), persistedBook.getId());
		
		assertEquals("Michael", persistedBook.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), persistedBook.getLaunchDate());
		assertEquals(7.00, persistedBook.getPrice());
		assertEquals("Teste", persistedBook.getTitle());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
			.contentType(TestsConfigs.CONTENT_TYPE_XML)
			.accept(TestsConfigs.CONTENT_TYPE_XML)
				.pathParam("id", book.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException, ParseException {
		
		var content = given().spec(specification)
				.contentType(TestsConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "limit", 10, "direction", "asc")
				.accept(TestsConfigs.CONTENT_TYPE_XML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PagedModelBook wrapper = objectMapper.readValue(content, PagedModelBook.class);
		var book = wrapper.getContent();
		
		BookVO foundBookOne = book.get(0);
		
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getLaunchDate());
		assertNotNull(foundBookOne.getPrice());
		assertNotNull(foundBookOne.getTitle());
		
		assertEquals(15, foundBookOne.getId());
		
		assertEquals("Aguinaldo Aragon Fernandes e Vladimir Ferraz de Abreu", foundBookOne.getAuthor());
		assertEquals(sdf.parse("07/11/2017"), foundBookOne.getLaunchDate());
		assertEquals(54.0, foundBookOne.getPrice());
		assertEquals("Implantando a governança de TI", foundBookOne.getTitle());
		
		BookVO foundBookSix = book.get(5);
		
		assertNotNull(foundBookSix.getId());
		assertNotNull(foundBookSix.getAuthor());
		assertNotNull(foundBookSix.getLaunchDate());
		assertNotNull(foundBookSix.getPrice());
		assertNotNull(foundBookSix.getTitle());
		
		assertEquals(14, foundBookSix.getId());
		
		assertEquals("Marc J. Schiller", foundBookSix.getAuthor());
		assertEquals(sdf.parse("07/11/2017"), foundBookSix.getLaunchDate());
		assertEquals(45.0, foundBookSix.getPrice());
		assertEquals("Os 11 segredos de líderes de TI altamente influentes", foundBookSix.getTitle());
	}

	@Test
	@Order(6)
	public void testFindBookByAuthor() throws JsonMappingException, JsonProcessingException, ParseException {
		
		var content = given().spec(specification)
				.contentType(TestsConfigs.CONTENT_TYPE_XML)
				.pathParam("author", "ls")
				.queryParams("page", 0, "limit", 10, "direction", "asc")
				.accept(TestsConfigs.CONTENT_TYPE_XML)
					.when()
					.get("/findBookByAuthor/{author}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PagedModelBook wrapper = objectMapper.readValue(content, PagedModelBook.class);
		var book = wrapper.getContent();
		
		BookVO foundBookOne = book.get(0);
		
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getLaunchDate());
		assertNotNull(foundBookOne.getPrice());
		assertNotNull(foundBookOne.getTitle());
		
		assertEquals(9, foundBookOne.getId());
		
		assertEquals("Brian Goetz e Tim Peierls", foundBookOne.getAuthor());
		assertEquals(sdf.parse("07/11/2017"), foundBookOne.getLaunchDate());
		assertEquals(80.0, foundBookOne.getPrice());
		assertEquals("Java Concurrency in Practice", foundBookOne.getTitle());
		
	}

	
	@Test
	@Order(7)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.setBasePath("/api/book/v1")
			.setPort(TestsConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		given().spec(specificationWithoutToken)
			.contentType(TestsConfigs.CONTENT_TYPE_XML)
			.accept(TestsConfigs.CONTENT_TYPE_XML)
				.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	@Test
	@Order(8)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestsConfigs.CONTENT_TYPE_XML)
				.accept(TestsConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "limit", 15, "direction", "asc" )
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		
		assertTrue(content.contains("<href>http://localhost:8080/api/book/v1/12</href>"));
		assertTrue(content.contains("<href>http://localhost:8080/api/book/v1/10</href>"));
		assertTrue(content.contains("<href>http://localhost:8080/api/book/v1/5</href>"));
		
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8080/api/book/v1?page=0&amp;limit=15&amp;direction=asc</href></links>"));
		
		assertTrue(content.contains("<page><size>15</size><totalElements>15</totalElements><totalPages>1</totalPages><number>0</number></page>"));
	}
	
	
	private void mockBook() throws ParseException {
		book.setAuthor("Michael");
		book.setLaunchDate(sdf.parse("01/01/2001"));
		book.setPrice(5.00);
		book.setTitle("Teste");
	}
}
