package com.wguproject.videogamebacklog.data

class SearchResult : ArrayList<SearchResultItem>()

data class SearchResultItem(
    val alternative_name: String,
    val game: Int,
    val id: Int,
    val name: String,
    val published_at: Int
)
