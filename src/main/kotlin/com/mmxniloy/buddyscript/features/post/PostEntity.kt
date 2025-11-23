package com.mmxniloy.buddyscript.features.post

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name="posts")
@EntityListeners(AuditingEntityListener::class)
data class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    val content: String,
    val imageUrl: String? = null,

    @Column(updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    val privacy: PostPrivacy? = PostPrivacy.PUBLIC,

)
