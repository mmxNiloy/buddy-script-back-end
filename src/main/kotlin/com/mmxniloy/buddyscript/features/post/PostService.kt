package com.mmxniloy.buddyscript.features.post

import com.mmxniloy.buddyscript.features.post.dto.CreatePostDto
import com.mmxniloy.buddyscript.features.post.dto.PostDto
import com.mmxniloy.buddyscript.features.profile.ProfileRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val profileRepository: ProfileRepository
) {
    fun getPaginatedPosts(
        paginationParams: PaginationParams,
        privacy: PostPrivacy = PostPrivacy.PUBLIC,
        userId: String?
    ): Page<PostDto> {
        val pageable: Pageable = PageRequest.of(
            paginationParams.page ?: 0,
            paginationParams.pageSize ?: 10,
            Sort.by("createdAt").descending()
        )
        val posts = postRepository.findAllByPrivacy(privacy, pageable)

        val postsPage: Page<PostDto> = posts.map { it -> it.toDto() }

        return postsPage
    }

    fun getPaginatedPostsOfAUser(paginationParams: PaginationParams,
                                 userId: String): Page<PostDto> {
        val pageable: Pageable = PageRequest.of(
            paginationParams.page ?: 0,
            paginationParams.pageSize ?: 10,
            Sort.by("createdAt").descending()
        )
        val posts = postRepository.findAllByAuthor_Id(UUID.fromString(userId), pageable)

        val postsPage: Page<PostDto> = posts.map { it -> it.toDto() }

        return postsPage
    }

    fun getPostById(postId: String): PostDto {
        val post = postRepository.findById(UUID.fromString(postId)).orElseThrow()
        return post.toDto()
    }

    fun createPost(createPostDto: CreatePostDto, userId: String): PostDto {
        val profile = profileRepository.findById(UUID.fromString(userId)).orElseThrow()

        val post = PostEntity(
            content = createPostDto.content,
            privacy = createPostDto.privacy,
            imageUrl = createPostDto.imageUrl,
            author = profile
        )

        postRepository.save(post)

        return post.toDto()
    }
}