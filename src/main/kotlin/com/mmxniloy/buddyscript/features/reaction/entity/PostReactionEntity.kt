package com.mmxniloy.buddyscript.features.reaction.entity

import com.mmxniloy.buddyscript.features.post.PostEntity
import com.mmxniloy.buddyscript.features.profile.ProfileEntity
import com.mmxniloy.buddyscript.features.reaction.util.ReactionType
import com.mmxniloy.buddyscript.features.reaction.dto.ReactionDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name="post_reactions", uniqueConstraints = [
    UniqueConstraint(columnNames = ["author_id", "post_id"])
])
@EntityListeners(AuditingEntityListener::class)
data class PostReactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    var type: ReactionType,

    @Column(updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: ProfileEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: PostEntity,
) {
    fun toDto(): ReactionDto {
        return ReactionDto(
            id = id.toString(),
            type = type.name,
            createdAt,
            author = author.toDto()
        )
    }
}
