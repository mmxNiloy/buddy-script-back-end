package com.mmxniloy.buddyscript.config.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "supabase.jwt")
data class SupabaseJwtProps(
    var secret: String = ""
)