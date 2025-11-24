package com.mmxniloy.buddyscript.features.comment

import com.mmxniloy.buddyscript.features.comment.dto.CommentDto
import com.mmxniloy.buddyscript.features.comment.dto.CreateCommentDto
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.security.Principal

@RestController
@RequestMapping("/api/v1/comment")
@Validated
class CommentController(
    private val commentService: CommentService
) {
    @GetMapping("/post/{postId}")
    fun getCommentsOfAPost(
        @PathVariable @NotEmpty postId: String,
        @RequestParam page: Int?,
        @RequestParam pageSize: Int?
    ): Page<CommentDto> {
        val comments = commentService.getCommentsOfAPost(postId, page, pageSize)

        return comments
    }

    @GetMapping("/reply/{commentId}")
    fun getRepliesOfAComment(
        @PathVariable @NotEmpty commentId: String,
        @RequestParam page: Int?,
        @RequestParam pageSize: Int?
    ): Page<CommentDto> {
        val comments = commentService.getRepliesOfAComment(commentId, page, pageSize)

        return comments
    }

    @PostMapping("/reply/{commentId}")
    fun createReplyForAComment(
        @PathVariable @NotEmpty commentId: String,
        @RequestBody @Valid createCommentDto: CreateCommentDto,
        principal: Principal
    ): ResponseEntity<Void> {
        val subject = principal.name
        val comment = commentService.createReplyForAComment(commentId, subject, createCommentDto)

        return ResponseEntity.created(URI("/api/v1/comment/${comment.id}")).build()
    }

    @PostMapping("/post/{postId}")
    fun createCommentForAPost(
        @PathVariable @NotEmpty postId: String,
        @RequestBody @Valid createCommentDto: CreateCommentDto,
        principal: Principal
    ): ResponseEntity<Void> {
        val subject = principal.name
        val comment = commentService.createCommentForAPost(postId, subject, createCommentDto)

        return ResponseEntity.created(URI("/api/v1/comment/${comment.id}")).build()
    }
}