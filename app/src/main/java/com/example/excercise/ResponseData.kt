package com.example.excercise

data class ResponseData(
    val photos: Photos,
    val stat: String
)
data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)

data class Photo(
    val farm: Int,
    val has_comment: Int,
    val id: String,
    val is_primary: Int,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)