package com.enveramil.imagerecorder.model

data class ImageResponse(
    val hits : List<ImageResult>,
    val total : Int,
    val totalHits : Int
)
