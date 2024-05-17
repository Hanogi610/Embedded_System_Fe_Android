package com.example.embeddedsystem.model

import java.io.Serializable

data class House(
    val id: Int = 0,
    var username: String = "",
    var password: String = "",
    var devices: List<Device> = listOf(),
    var histories: List<History> = listOf()
) : Serializable