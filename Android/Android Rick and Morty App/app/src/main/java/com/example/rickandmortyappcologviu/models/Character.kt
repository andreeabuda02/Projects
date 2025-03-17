package com.example.rickandmortyappcologviu.models

import java.io.Serializable

class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val image: String,
    val location: Location,
    var hasCustomBackground: Boolean = false

) : Serializable{
    var detailsCount: Int = 0
}
