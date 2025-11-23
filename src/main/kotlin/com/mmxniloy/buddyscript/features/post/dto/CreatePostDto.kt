package com.mmxniloy.buddyscript.features.post.dto

import com.mmxniloy.buddyscript.features.post.PostPrivacy
import jakarta.validation.constraints.NotEmpty

data class CreatePostDto(
    @field:NotEmpty
    val content: String,
    val privacy: PostPrivacy,
    val imageUrl: String?
)
