package com.example.notesAPI

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.notesAPI"])
class NotesApiApplication

fun main(args: Array<String>) {
	runApplication<NotesApiApplication>(*args)
}
