package com.SpringBootREST.integrationtests.controller.withYAML;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.SpringBootREST.configs.TestsConfigs;
import com.SpringBootREST.integrationtests.VO.AccountCredentialsVO;
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
public class AuthControllerTestWithYAML extends AbstractIntegrationTest {
	
	private static TokenVO tokenVO;
	private static YAMLMapper mapper;
	
	@BeforeAll
	public static void YAMLMapper() {
		mapper = new YAMLMapper();
		
	}

	@Test
	@Order(1)
	public void testSignIn() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsVO user = new AccountCredentialsVO("leandro","admin123");
		
		
		RequestSpecification specification = new RequestSpecBuilder()
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		tokenVO = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.accept(TestsConfigs.CONTENT_TYPE_YML)
				.basePath("/auth/signin")
				.port(TestsConfigs.SERVER_PORT)
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.body(user, mapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(TokenVO.class, mapper);
							
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsVO user = new AccountCredentialsVO("leandro","admin123");
		
		var newTokenVO = given()
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.accept(TestsConfigs.CONTENT_TYPE_YML)
				.basePath("/auth/refresh")
				.port(TestsConfigs.SERVER_PORT)
				.contentType(TestsConfigs.CONTENT_TYPE_YML)
				.pathParam("username", tokenVO.getUsername())
				.header(TestsConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
					.when()
					.put("{username}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(TokenVO.class, mapper);
		
		
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
	

}
