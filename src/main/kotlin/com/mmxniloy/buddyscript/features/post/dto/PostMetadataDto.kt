package com.mmxniloy.buddyscript.features.post.dto

import com.mmxniloy.buddyscript.features.comment.dto.CommentMetadataDto
import com.mmxniloy.buddyscript.features.profile.dto.ProfileDto
import com.mmxniloy.buddyscript.features.reaction.dto.ReactionMetadataDto

data class PostMetadataDto(
    val user: ProfileDto?,
    val reactions: ReactionMetadataDto,
    val comments: CommentMetadataDto,
    val shares: Int,
)
