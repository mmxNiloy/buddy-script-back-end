package com.mmxniloy.buddyscript.features.comment.dto

import com.mmxniloy.buddyscript.features.profile.dto.ProfileDto
import java.time.LocalDateTime

data class CommentDto(
    val id: String,
    val content: String,
    val createdAt: LocalDateTime?,
    val user: ProfileDto,
    val metadata: CommentInternalMetadataDto? = null
)
