package com.mmxniloy.buddyscript.features.comment

import com.mmxniloy.buddyscript.features.comment.dto.CommentDto
import com.mmxniloy.buddyscript.features.comment.dto.CommentInternalMetadataDto
import com.mmxniloy.buddyscript.features.comment.dto.ReplyMetadataDto
import com.mmxniloy.buddyscript.features.post.PostEntity
import com.mmxniloy.buddyscript.features.profile.ProfileEntity
import com.mmxniloy.buddyscript.features.reaction.entity.CommentReactionEntity
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
@Table(name="comments")
@EntityListeners(AuditingEntityListener::class)
data class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val content: String,

    @Column(updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: ProfileEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: PostEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    val parentComment: CommentEntity? = null,

    @OneToMany(mappedBy="comment", fetch = FetchType.LAZY)
    val reactions: List<CommentReactionEntity> = emptyList(),

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    val replies: List<CommentEntity> = emptyList()
) {
    fun toDto(): CommentDto {
        return CommentDto(
            id = id?.toString() ?: "",
            content,
            createdAt,
            user = author.toDto(),
            metadata = CommentInternalMetadataDto(
                reactions = ReactionMetadataDto(
                    total = reactions.size,
                    recentReactions = emptyList(),
                ),
                replies = ReplyMetadataDto(
                    total = replies.size,
                    recentReplies = replies.map { it -> it.toDto() }
                )
            )
        )
    }
}