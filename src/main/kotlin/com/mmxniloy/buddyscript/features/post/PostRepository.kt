package com.mmxniloy.buddyscript.features.post

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<PostEntity, String> {
}