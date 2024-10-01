package com.wguproject.videogamebacklog.data

data class APIGameItem(
    val id: Int,
    val aggregated_rating: Double?,
    val category: Int,
    val coverUrl: String? = null,
    val first_release_date: Long?,
    val genres: List<Int>?,
    val name: String,
    val similar_games: List<Int>?,
    val summary: String? = null
)
