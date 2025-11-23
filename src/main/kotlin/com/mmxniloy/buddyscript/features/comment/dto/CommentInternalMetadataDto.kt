package com.mmxniloy.buddyscript.features.comment.dto

import com.mmxniloy.buddyscript.features.reaction.dto.ReactionMetadataDto

data class CommentInternalMetadataDto(
    val reactions: ReactionMetadataDto,
    val replies: ReplyMetadataDto,
)
