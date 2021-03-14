package com.mathieugouverneur.homeview.models

data class Movie(val edited: String,
                 val director: String,
                 val created: String,
                 val vehicles: List<String>?,
                 val opening_crawl: String,
                 val title: String,
                 val url: String,
                 val characters: List<String>?,
                 val episode_id: Int,
                 val planets: List<String>?,
                 val release_date: String,
                 val starships: List<String>?,
                 val species: List<String>?,
                 val producer: String)