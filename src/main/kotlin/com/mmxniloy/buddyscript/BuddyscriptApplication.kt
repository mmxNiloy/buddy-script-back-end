package com.mmxniloy.buddyscript

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BuddyscriptApplication

fun main(args: Array<String>) {
	runApplication<BuddyscriptApplication>(*args)
}
