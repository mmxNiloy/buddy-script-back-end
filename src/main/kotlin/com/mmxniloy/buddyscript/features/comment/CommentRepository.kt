package com.mmxniloy.buddyscript.features.comment

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CommentRepository: JpaRepository<CommentEntity, UUID> {
    fun findAllByPost_IdAndParentCommentIsNull(postId: UUID, pageable: Pageable): Page<CommentEntity>
    fun findAllByParentComment_Id(commentId: UUID, pageable: Pageable): Page<CommentEntity>
}