package com.example.notesAPI.controller

import model.HealthCheckResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/health-check")
class HealthCheckController {

    @Value("\${spring.application.name}")
    private lateinit var applicationName: String

    @Value("\${server.version}")
    private lateinit var version: String

    private val startTime = Instant.now()

    private val logger = LoggerFactory.getLogger(HealthCheckController::class.java)

    @GetMapping
    fun retrieveHealthCheck(): ResponseEntity<HealthCheckResponse> {
        logger.info("Health check requested")
        val uptime = Instant.now().epochSecond - startTime.epochSecond
        val response = HealthCheckResponse(
            status = "OK",
            applicationName = applicationName,
            version = version,
            timestamp = Instant.now(),
            uptime = uptime
        )
        logger.info("Health check response: {}", response)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/dependencies")
    fun checkDependencies(): ResponseEntity<Map<String, String>> {
        logger.info("Dependencies check requested")
        val dbStatus = checkDatabase()
        val serviceStatus = checkExternalService()
        val response = mapOf(
            "database" to dbStatus,
            "externalService" to serviceStatus
        )
        logger.info("Dependencies check response: {}", response)
        return ResponseEntity.ok(response)
    }

    fun checkDatabase(): String {
        // Lógica para verificar o estado do banco de dados
        logger.debug("Checking database status")
        val status = "UP"
        logger.debug("Database status: {}", status)
        return status
    }

    fun checkExternalService(): String {
        // Lógica para verificar o estado de um serviço externo
        logger.debug("Checking external service status")
        val status = "UP"
        logger.debug("External service status: {}", status)
        return status
    }

    @GetMapping("/version")
    fun getVersion(): ResponseEntity<Map<String, String>> {
        logger.info("Version check requested")
        val response = mapOf(
            "version" to version,
        )
        logger.info("Version check response: {}", response)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/uptime")
    fun getUptime(): ResponseEntity<Map<String, Long>> {
        logger.info("Uptime check requested")
        val uptime = Instant.now().epochSecond - startTime.epochSecond
        val response = mapOf(
            "uptime" to uptime,
        )
        logger.info("Uptime check response: {}", response)
        return ResponseEntity.ok(response)
    }

}
