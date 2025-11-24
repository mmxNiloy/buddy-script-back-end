package com.mmxniloy.buddyscript.features.reaction.controller

import com.mmxniloy.buddyscript.features.reaction.dto.ReactionDto
import com.mmxniloy.buddyscript.features.reaction.service.CommentReactionService
import com.mmxniloy.buddyscript.features.reaction.service.PostReactionService
import com.mmxniloy.buddyscript.features.reaction.util.ReactionType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/reaction/comment")
@Validated
class CommentReactionController(
    private val commentReactionService: CommentReactionService
) {
    @GetMapping("/{commentId}")
    fun getReactionsOfAPost(@PathVariable @NotEmpty commentId: String,
                            @RequestParam page: Int?,
                            @RequestParam pageSize: Int?): Page<ReactionDto> {
        val reactions = commentReactionService.getReactionsOfAComment(commentId, page, pageSize)

        return reactions
    }

    @GetMapping("/my/{commentId}")
    fun getMyReactionsOfAPost(@PathVariable @NotEmpty commentId: String, principal: Principal): ReactionDto? {
        val subject = principal.name

        val myReaction = commentReactionService.getMyReactionsOfAComment(commentId, subject)

        return myReaction
    }

    @PutMapping("/{commentId}/{reactionType}")
    fun reactToPost(
        @PathVariable @NotEmpty commentId: String,
        principal: Principal,
        @PathVariable @NotNull reactionType: ReactionType
    ): ResponseEntity<Void> {
        val subject = principal.name

        commentReactionService.reactToComment(commentId, subject, reactionType)

        return ResponseEntity.noContent().build()
    }
}