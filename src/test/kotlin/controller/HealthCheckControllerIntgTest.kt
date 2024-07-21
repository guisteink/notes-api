package com.example.notesAPI.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HealthCheckControllerIntgTest {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var baseUrl: String

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
        baseUrl = "http://localhost:$port/health-check"
    }

    @Test
    fun `retrieveHealthCheck should return OK`() {
        val response: ResponseEntity<String> = restTemplate.getForEntity(baseUrl, String::class.java)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body?.contains("OK") == true)
    }

    @Test
    fun `checkDependencies should return UP for both database and externalService`() {
        val response: ResponseEntity<String> = restTemplate.getForEntity("$baseUrl/dependencies", String::class.java)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body?.contains("\"database\":\"UP\"") == true)
        assert(response.body?.contains("\"externalService\":\"UP\"") == true)
    }

    @Test
    fun `getVersion should return the correct version`() {
        val response: ResponseEntity<String> = restTemplate.getForEntity("$baseUrl/version", String::class.java)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body?.contains("\"version\":\"v1\"") == true)
    }

    @Test
    fun `getUptime should return uptime in seconds`() {
        val response: ResponseEntity<String> = restTemplate.getForEntity("$baseUrl/uptime", String::class.java)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body?.contains("uptime") == true)
    }
}
