package com.example.domain.model.response

import com.squareup.moshi.Json

data class Article (
    @Json(name = "id")
    var id: String? = null,

    @Json(name = "title")
    var title: String? = null,

    @Json(name = "pubDate")
    var pubDate: String? = null,

    @Json(name = "dc:creator")
    var creator: String? = null,

    @Json(name = "category")
    var categories: List<String>? = null,

    @Json(name = "description")
    var description: String? = null,

    @Json(name = "image")
    var image: String? = null,

    @Json(name = "detail")
    var detail: String? = null
)

