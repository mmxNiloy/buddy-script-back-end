package com.mmxniloy.buddyscript.features.profile

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProfileRepository: JpaRepository<ProfileEntity, UUID> {
}