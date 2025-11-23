package com.mmxniloy.buddyscript.features.profile

import com.mmxniloy.buddyscript.features.profile.dto.ProfileDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name="profiles")
data class ProfileEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    val displayName: String?,
    val avatarUrl: String?
) {
    fun toDto(): ProfileDto {
        return ProfileDto(
            id = id.toString(),
            displayName,
            avatarUrl
        )
    }
}