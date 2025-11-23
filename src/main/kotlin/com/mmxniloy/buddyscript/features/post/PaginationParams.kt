package com.mmxniloy.buddyscript.features.post

import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Min

data class PaginationParams(
    @field:PositiveOrZero
    val page: Int? = 0,
    @field:Min(2)
    val pageSize: Int? = 10,
)
