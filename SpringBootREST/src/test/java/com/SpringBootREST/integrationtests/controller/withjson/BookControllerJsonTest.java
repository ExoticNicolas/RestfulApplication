package com.SpringBootREST.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.SpringBootREST.integrationtests.VO.BookVOCORS;
import com.SpringBootREST.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static BookVOCORS book;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		book = new BookVOCORS();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException, ParseException {
		mockBookVOCORS();

		specification = new RequestSpecBuilder().addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/book/v1").setPort(TestsConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestsConfigs.CONTENT_TYPE_JSON).body(book).when().post()
				.then().statusCode(200).extract().body().asString();

		BookVOCORS persistedBook = objectMapper.readValue(content, BookVOCORS.class);
		book = persistedBook;
		
		System.out.println(book);
		System.out.println(persistedBook);
		
		assertNotNull(persistedBook);
		
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());
		
		assertTrue(persistedBook.getId() > 0);
		
		assertEquals("TESTE", persistedBook.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), persistedBook.getLaunchDate());
		assertEquals(5.0, persistedBook.getPrice());
		assertEquals("TESTE", persistedBook.getTitle());
	}

	/*@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPersonCORS();

		specification = new RequestSpecBuilder().addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_SEMERU)
				.setBasePath("/api/person/v1").setPort(TestsConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestsConfigs.CONTENT_TYPE_JSON).body(person).when().post()
				.then().statusCode(403).extract().body().asString();

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		
		mockPersonCORS();

		specification = new RequestSpecBuilder().addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/person/v1").setPort(TestsConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestsConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", person.getId()).when().get("{id}").then().statusCode(200).extract().body().asString();

		PersonVOCORS persistedPerson = objectMapper.readValue(content, PersonVOCORS.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);

		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());

		assertTrue(persistedPerson.getId() > 0);

		assertEquals("Richard", persistedPerson.getFirstName());
		assertEquals("Stallman", persistedPerson.getLastName());
		assertEquals("New York City, New York, US", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}*/

	private void mockBookVOCORS() throws ParseException {
		
		book.setAuthor("TESTE");
		book.setLaunchDate(sdf.parse("01/01/2001"));
		book.setPrice(5.0);
		book.setTitle("TESTE");

	}
}
