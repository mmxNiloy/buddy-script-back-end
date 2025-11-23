package com.mmxniloy.buddyscript.features.reaction.repository

import com.mmxniloy.buddyscript.features.reaction.entity.PostReactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface PostReactionRepository: JpaRepository<PostReactionEntity, UUID> {
    fun findByAuthor_Id(authorId: UUID): Optional<PostReactionEntity>
    fun findByPost_Id(postId: UUID): Optional<PostReactionEntity>

    fun findByPost_IdAndAuthor_Id(postId: UUID, authorId: UUID): Optional<PostReactionEntity>
}