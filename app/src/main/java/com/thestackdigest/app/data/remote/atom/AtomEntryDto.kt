package com.thestackdigest.app.data.remote.atom

data class AtomEntryDto(
    val id: String,
    val title: String,
    val articleUrl: String,
    val authorName: String?,
    val published: String,
    val updated: String?,
    val contentHtml: String?,
    val imageUrl: String?,
    val categories: List<String>
)