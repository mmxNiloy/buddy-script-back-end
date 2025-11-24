package com.mmxniloy.buddyscript.features.reaction.service

import com.mmxniloy.buddyscript.features.comment.CommentRepository
import com.mmxniloy.buddyscript.features.post.PostRepository
import com.mmxniloy.buddyscript.features.profile.ProfileRepository
import com.mmxniloy.buddyscript.features.reaction.dto.ReactionDto
import com.mmxniloy.buddyscript.features.reaction.entity.CommentReactionEntity
import com.mmxniloy.buddyscript.features.reaction.entity.PostReactionEntity
import com.mmxniloy.buddyscript.features.reaction.repository.CommentReactionRepository
import com.mmxniloy.buddyscript.features.reaction.repository.PostReactionRepository
import com.mmxniloy.buddyscript.features.reaction.util.ReactionType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CommentReactionService(
    private val commentReactionRepository: CommentReactionRepository,
    private val profileRepository: ProfileRepository,
    private val commentRepository: CommentRepository
) {
    fun getReactionsOfAComment(commentId: String, page: Int?, pageSize: Int?): Page<ReactionDto> {
        val pageable = PageRequest.of(page ?: 0, pageSize ?: 20, Sort.by("createdAt").descending())

        val commentReactions =
            commentReactionRepository.findAllByComment_Id(UUID.fromString(commentId), pageable)

        val reactions: Page<ReactionDto> = commentReactions.map { it.toDto() }

        return reactions
    }

    fun getMyReactionsOfAComment(commentId: String, userId: String): ReactionDto? {
        val commentReaction =
            commentReactionRepository.findByComment_IdAndAuthor_Id(
                UUID.fromString(commentId),
                UUID.fromString(userId)
            ).orElseThrow()

        return commentReaction.toDto()
    }

    fun reactToComment(commentId: String, userId: String, reactionType: ReactionType) {
        val user = profileRepository.findById(UUID.fromString(userId)).orElseThrow()
        val comment = commentRepository.findById(UUID.fromString(commentId)).orElseThrow()

        val commentReaction =
            commentReactionRepository.findByComment_IdAndAuthor_Id(UUID.fromString(commentId),
                UUID.fromString(userId))

        if (commentReaction.isPresent) {
            val reaction = commentReaction.get()
            // Remove reaction
            if (reaction.type == reactionType) {

                commentReactionRepository.delete(commentReaction.get())
            } else {
                reaction.type = reactionType
                commentReactionRepository.save(reaction)
            }
        } else {
            // Add reaction
            commentReactionRepository.save(
                CommentReactionEntity(
                    type = reactionType,
                    author = user,
                    comment = comment
                )
            )
        }
    }
}