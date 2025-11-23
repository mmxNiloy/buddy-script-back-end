package com.mmxniloy.buddyscript.features.comment.dto

data class CommentMetadataDto(
    val total: Int,
    val recentComments: List<CommentDto>,
)