package com.mmxniloy.buddyscript.features.reaction.repository

import com.mmxniloy.buddyscript.features.reaction.entity.PostReactionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface PostReactionRepository: JpaRepository<PostReactionEntity, UUID> {
    fun findAllByAuthor_Id(authorId: UUID, pageable: Pageable): Page<PostReactionEntity>
    fun findAllByPost_Id(postId: UUID, pageable: Pageable): Page<PostReactionEntity>

    fun findByPost_IdAndAuthor_Id(postId: UUID, authorId: UUID): Optional<PostReactionEntity>
}