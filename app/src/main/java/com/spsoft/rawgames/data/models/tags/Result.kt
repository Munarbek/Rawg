package com.spsoft.rawgames.data.models.tags
data class Result(
    val games: List<Game>,
    val games_count: Int,
    val id: Int,
    val image_background: String,
    val language: String,
    val name: String,
    val slug: String,
    var selected:Boolean=false
)