package com.mmxniloy.buddyscript.features.post

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PostRepository: JpaRepository<PostEntity, UUID> {
    fun findAllByPrivacy(privacy: PostPrivacy, pageable: Pageable): Page<PostEntity>
    fun findAllByAuthor_Id(authorId: UUID, pageable: Pageable): Page<PostEntity>
}