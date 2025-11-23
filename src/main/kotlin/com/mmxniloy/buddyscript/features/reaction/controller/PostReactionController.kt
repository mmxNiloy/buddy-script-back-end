package com.mmxniloy.buddyscript.features.reaction.controller

import com.mmxniloy.buddyscript.features.reaction.dto.ReactionDto
import com.mmxniloy.buddyscript.features.reaction.service.PostReactionService
import com.mmxniloy.buddyscript.features.reaction.util.ReactionType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/reaction")
@Validated
class PostReactionController(
    private val postReactionService: PostReactionService
) {
    @GetMapping("/my/post/{postId}")
    fun getMyReactionsOfAPost(@PathVariable @NotEmpty postId: String, principal: Principal): ReactionDto? {
        val subject = principal.name

        val myReaction = postReactionService.getMyReactionsOfAPost(postId, subject)

        return myReaction
    }

    @PutMapping("/post/{postId}/{reactionType}")
    fun reactToPost(
        @PathVariable @NotEmpty postId: String,
        principal: Principal,
        @PathVariable @NotNull reactionType: ReactionType
    ): ResponseEntity<Void> {
        val subject = principal.name

        postReactionService.reactToPost(postId, subject, reactionType)

        return ResponseEntity.noContent().build()
    }
}