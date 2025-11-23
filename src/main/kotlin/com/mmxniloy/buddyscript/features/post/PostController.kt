package com.mmxniloy.buddyscript.features.post

import com.mmxniloy.buddyscript.features.post.dto.CreatePostDto
import com.mmxniloy.buddyscript.features.post.dto.PostDto
import com.mmxniloy.buddyscript.features.profile.dto.ProfileDto
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.security.Principal

@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/post")
@Validated
class PostController(
    private val postService: PostService
) {
    @GetMapping
    fun getPosts(
        @RequestParam @PositiveOrZero page: Int? = 0,
        @RequestParam @Min(2) pageSize: Int? = 10,
        principal: Principal
    ): Page<PostDto> {
        val subject = principal.name

        val posts = postService.getPaginatedPosts(PaginationParams(page, pageSize), userId=subject)

        return posts
    }

    @GetMapping("/my-posts")
    fun getMyPosts(
        @RequestParam @PositiveOrZero page: Int? = 0,
        @RequestParam @Min(2) pageSize: Int? = 10,
        principal: Principal
    ): Page<PostDto> {
        val subject = principal.name

        val posts = postService.getPaginatedPostsOfAUser(PaginationParams(page, pageSize), subject)

        return posts
    }

    @GetMapping("/{postId}")
    fun getPostById(@PathVariable @NotEmpty postId: String): PostDto {
        return postService.getPostById(postId)
    }

    @PostMapping
    fun createPost(@RequestBody @Valid createPostDto: CreatePostDto, principal: Principal): ResponseEntity<ProfileDto> {
        val subject = principal.name

        val createdPost = postService.createPost(createPostDto, subject)

        return ResponseEntity.created(URI("/api/v1/post/${createdPost.id}")).build()
    }
}