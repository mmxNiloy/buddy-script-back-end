package com.mmxniloy.buddyscript.features.post.dto

import com.mmxniloy.buddyscript.features.post.PostPrivacy
import java.time.LocalDateTime

data class PostDto(
    val id: String,
    val content: String,
    val imageUrl: String?,
    val createdAt: LocalDateTime?,
    val privacy: String,
    val metadata: PostMetadataDto,
)
