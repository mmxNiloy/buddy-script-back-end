package com.mmxniloy.buddyscript.features.reaction.service

import com.mmxniloy.buddyscript.features.post.PostRepository
import com.mmxniloy.buddyscript.features.profile.ProfileRepository
import com.mmxniloy.buddyscript.features.reaction.dto.ReactionDto
import com.mmxniloy.buddyscript.features.reaction.entity.PostReactionEntity
import com.mmxniloy.buddyscript.features.reaction.repository.PostReactionRepository
import com.mmxniloy.buddyscript.features.reaction.util.ReactionType
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PostReactionService(
    private val postReactionRepository: PostReactionRepository,
    private val profileRepository: ProfileRepository,
    private val postRepository: PostRepository
) {
    fun getMyReactionsOfAPost(postId: String, userId: String): ReactionDto? {
        val postReaction =
            postReactionRepository.findByPost_IdAndAuthor_Id(UUID.fromString(postId), UUID.fromString(userId))
                .orElseThrow()

        return postReaction.toDto()
    }

    fun reactToPost(postId: String, userId: String, reactionType: ReactionType) {
        val user = profileRepository.findById(UUID.fromString(userId)).orElseThrow()
        val post = postRepository.findById(UUID.fromString(postId)).orElseThrow()

        val postReaction =
            postReactionRepository.findByPost_IdAndAuthor_Id(UUID.fromString(postId), UUID.fromString(userId))

        if(postReaction.isPresent) {
        val reaction = postReaction.get()
            // Remove reaction
            if(reaction.type == reactionType) {

            postReactionRepository.delete(postReaction.get())
            } else {
                reaction.type = reactionType
                postReactionRepository.save(reaction)
            }
        } else {
            // Add reaction
            postReactionRepository.save(PostReactionEntity(
                type = reactionType,
                author = user,
                post = post
            ))
        }
    }
}