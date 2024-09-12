package com.SpringBootREST.integrationtests.controller.withYAML;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.SpringBootREST.configs.TestsConfigs;
import com.SpringBootREST.integrationtests.VO.AccountCredentialsVO;
import com.SpringBootREST.integrationtests.VO.PersonVO;
import com.SpringBootREST.integrationtests.VO.TokenVO;
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
public class PersonControllerYamlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YAMLMapper objectMapper;

	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YAMLMapper();
		person = new PersonVO();
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
				.setBasePath("/api/person/v1")
				.setPort(TestsConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertNotNull(persistedPerson.getEnabled());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet", persistedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertEquals(true, persistedPerson.getEnabled());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setLastName("Piquet Souto Maior");
		
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PersonVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertNotNull(persistedPerson.getEnabled());
		
		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertEquals(true, persistedPerson.getEnabled());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestsConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.accept(TestsConfigs.CONTENT_TYPE_YML)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PersonVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertNotNull(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertEquals(true, persistedPerson.getEnabled());
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
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
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
					.statusCode(200)
						.extract()
						.body()
						.as(PersonVO[].class, objectMapper);
		
		List<PersonVO> people = Arrays.asList(content);
		
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());
		assertNotNull(foundPersonOne.getEnabled());
		
		assertEquals(1, foundPersonOne.getId());
		
		assertEquals("Ayrton", foundPersonOne.getFirstName());
		assertEquals("Senna", foundPersonOne.getLastName());
		assertEquals("São Paulo", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());
		assertEquals(true, foundPersonOne.getEnabled());
		
		PersonVO foundPersonSix = people.get(5);
		
		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getFirstName());
		assertNotNull(foundPersonSix.getLastName());
		assertNotNull(foundPersonSix.getAddress());
		assertNotNull(foundPersonSix.getGender());
		assertNotNull(foundPersonSix.getEnabled());
		
		assertEquals(9, foundPersonSix.getId());
		
		assertEquals("Nelson", foundPersonSix.getFirstName());
		assertEquals("Mvezo", foundPersonSix.getLastName());
		assertEquals("Mvezo – South Africa", foundPersonSix.getAddress());
		assertEquals("Male", foundPersonSix.getGender());
		assertEquals(true, foundPersonSix.getEnabled());
	}

	
	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.setBasePath("/api/person/v1")
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
	
	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasília - DF - Brasil");
		person.setGender("Male");
		person.setEnabled(true);
	}
}
