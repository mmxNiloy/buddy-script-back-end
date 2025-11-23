package com.mmxniloy.buddyscript.features.post

import com.mmxniloy.buddyscript.features.comment.CommentEntity
import com.mmxniloy.buddyscript.features.comment.dto.CommentDto
import com.mmxniloy.buddyscript.features.comment.dto.CommentMetadataDto
import com.mmxniloy.buddyscript.features.post.dto.PostDto
import com.mmxniloy.buddyscript.features.post.dto.PostMetadataDto
import com.mmxniloy.buddyscript.features.profile.ProfileEntity
import com.mmxniloy.buddyscript.features.reaction.entity.PostReactionEntity
import com.mmxniloy.buddyscript.features.reaction.dto.ReactionDto
import com.mmxniloy.buddyscript.features.reaction.dto.ReactionMetadataDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener::class)
data class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val content: String,
    val imageUrl: String? = null,

    @Column(updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    val privacy: PostPrivacy = PostPrivacy.PUBLIC,
    val shares: Int = 0,

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: ProfileEntity,

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    val comments: List<CommentEntity> = emptyList(),

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    val reactions: List<PostReactionEntity> = emptyList()
) {

    fun toDto(myReaction: String? = null): PostDto {
        return PostDto(
            id = id?.toString() ?: "",
            content = content,
            imageUrl = imageUrl,
            createdAt = createdAt,
            privacy = privacy.name,
            metadata = buildMetadata(myReaction)
        )
    }

    fun buildMetadata(myReaction: String? = null): PostMetadataDto {
        var recentReactions: List<ReactionDto> = emptyList()
        if (reactions.isNotEmpty()) {
            recentReactions = reactions
                .map { it -> it.toDto() }
            recentReactions = recentReactions.subList(0, 20.coerceAtMost(recentReactions.size))
        }

        var recentComments: List<CommentDto> = emptyList()
        if (comments.isNotEmpty()) {
            recentComments = comments
                .map { it -> it.toDto() }
            recentComments = recentComments.subList(0, 20.coerceAtMost(recentComments.size))
        }

        return PostMetadataDto(
            user = author.toDto(),
            reactions = ReactionMetadataDto(
                total = reactions.size,
                recentReactions,
                myReaction = myReaction
            ),
            comments = CommentMetadataDto(
                total = comments.size,
                recentComments,
            ),
            shares = shares,
        )
    }
}
