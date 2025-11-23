package com.mmxniloy.buddyscript.features.reaction.dto

import com.mmxniloy.buddyscript.features.profile.dto.ProfileDto
import java.time.LocalDateTime

data class ReactionDto(
    val id: String?,
    val type: String?,
    val createdAt: LocalDateTime?,
    val author: ProfileDto
)