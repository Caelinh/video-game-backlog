package com.wguproject.videogamebacklog.utils

private const val IGDB_IMAGE_URL = "https://images.igdb.com/igdb/image/upload/"

fun imageBuilder(imageID: String, size: ImageSize, imageType: ImageType = ImageType.PNG): String {
    return "$IGDB_IMAGE_URL${size.tSize}/$imageID.${imageType.type}"
}