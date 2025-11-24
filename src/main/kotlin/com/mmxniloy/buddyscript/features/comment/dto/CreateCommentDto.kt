package com.mmxniloy.buddyscript.features.comment.dto

import jakarta.validation.constraints.NotEmpty

data class CreateCommentDto(
    @field:NotEmpty
    val content: String
)
