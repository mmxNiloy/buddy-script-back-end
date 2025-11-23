package com.mmxniloy.buddyscript.features.post

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/post")
@Validated
class PostController {
    @GetMapping
    fun getPosts(@PageableDefault(value=10, page=0) pageable: Pageable) {

    }
}