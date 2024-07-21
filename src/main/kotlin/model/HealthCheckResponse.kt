package model

import java.time.Instant

data class HealthCheckResponse(
    val status: String,
    val applicationName: String,
    val version: String,
    val timestamp: Instant,
    val uptime: Long
)