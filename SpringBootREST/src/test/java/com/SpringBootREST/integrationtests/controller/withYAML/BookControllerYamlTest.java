package com.SpringBootREST.integrationtests.controller.withYAML;

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
import com.SpringBootREST.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerYamlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YAMLMapper objectMapper;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static BookVO book;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YAMLMapper();
		book = new BookVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestsConfigs.SERVER_PORT)
					.contentType(TestsConfigs.CONTENT_TYPE_YML)
					.accept(TestsConfigs.CONTENT_TYPE_YML)
				.body(user, objectMapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class, objectMapper)
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
		mokcBook();
		
		var persistedBook = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.body(book, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(BookVO.class, objectMapper);
		
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
		
		var persistedBook = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.body(book, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(BookVO.class, objectMapper);
		
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
		mokcBook();
			
		var persistedBook = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.pathParam("id", book.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(BookVO.class, objectMapper);
		
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
			.config(
				RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(
							TestsConfigs.CONTENT_TYPE_YML,
							ContentType.TEXT)))
			.contentType(TestsConfigs.CONTENT_TYPE_YML)
			.accept(TestsConfigs.CONTENT_TYPE_YML)
				.pathParam("id", book.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException, ParseException {
		
		var wrapper = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 0, "limit", 10, "direction", "asc")
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PagedModelBook.class, objectMapper);
		
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
		
		var wrapper = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.pathParam("author", "ls")
				.queryParams("page", 0, "limit", 10, "direction", "asc")
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.when()
					.get("/findBookByAuthor/{author}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PagedModelBook.class, objectMapper);
		
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
			.config(
				RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(
							TestsConfigs.CONTENT_TYPE_YML,
							ContentType.TEXT)))
			.contentType(TestsConfigs.CONTENT_TYPE_YML)
			.accept(TestsConfigs.CONTENT_TYPE_YML)
				.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	private void mokcBook() throws ParseException {
		book.setAuthor("Michael");
		book.setLaunchDate(sdf.parse("01/01/2001"));
		book.setPrice(5.00);
		book.setTitle("Teste");
	}
}
