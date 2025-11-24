package com.mmxniloy.buddyscript.features.comment

import com.mmxniloy.buddyscript.features.comment.dto.CommentDto
import com.mmxniloy.buddyscript.features.comment.dto.CreateCommentDto
import com.mmxniloy.buddyscript.features.post.PostRepository
import com.mmxniloy.buddyscript.features.profile.ProfileRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val profileRepository: ProfileRepository
) {
    fun getCommentsOfAPost(postId: String, page: Int?, pageSize: Int?): Page<CommentDto> {
        val comments =
            commentRepository.findAllByPost_IdAndParentCommentIsNull(
                UUID.fromString(postId),
                PageRequest.of(page ?: 0, pageSize ?: 20, Sort.by("createdAt").descending())
            )

        return comments.map { it.toDto() }
    }

    fun getRepliesOfAComment(commentId: String, page: Int?, pageSize: Int?): Page<CommentDto> {
        val replies =
            commentRepository.findAllByParentComment_Id(
                UUID.fromString(commentId),
                PageRequest.of(page ?: 0, pageSize ?: 20, Sort.by("createdAt").descending())
            )

        return replies.map { it.toDto() }
    }

    fun createCommentForAPost(postId: String, userId: String, createCommentDto: CreateCommentDto): CommentDto {
        val post = postRepository.findById(UUID.fromString(postId)).orElseThrow()
        val user = profileRepository.findById(UUID.fromString(userId)).orElseThrow()

        val comment = CommentEntity(
            post = post,
            author = user,
            content = createCommentDto.content
        )

        commentRepository.save(comment)

        return comment.toDto()
    }

    fun createReplyForAComment(commentId: String, userId: String, createCommentDto: CreateCommentDto): CommentDto {
        val comment = commentRepository.findById(UUID.fromString(commentId)).orElseThrow()
        val user = profileRepository.findById(UUID.fromString(userId)).orElseThrow()

        val reply = CommentEntity(
            post = comment.post,
            author = user,
            content = createCommentDto.content,
            parentComment = comment
        )

        commentRepository.save(reply)

        return reply.toDto()
    }
}