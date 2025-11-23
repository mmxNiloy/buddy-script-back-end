package com.mmxniloy.buddyscript.features.comment.dto

data class ReplyMetadataDto(
    val total: Int,
    val recentReplies: List<CommentDto>
)
