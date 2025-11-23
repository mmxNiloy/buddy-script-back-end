package com.mmxniloy.buddyscript.features.reaction.dto

data class ReactionMetadataDto(
    val total: Int,
    val recentReactions: List<ReactionDto>,
    val myReaction: String? = null
)
