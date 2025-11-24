package com.mmxniloy.buddyscript.features.reaction.repository

import com.mmxniloy.buddyscript.features.reaction.entity.CommentReactionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface CommentReactionRepository: JpaRepository<CommentReactionEntity, UUID> {
    fun findAllByComment_Id(postId: UUID, pageable: Pageable): Page<CommentReactionEntity>

    fun findByComment_IdAndAuthor_Id(postId: UUID, authorId: UUID): Optional<CommentReactionEntity>
}