package com.example.embeddedsystem.model

import java.io.Serializable

data class Device(
    val id: Int,
    val name: String,
    var status: Boolean,
    var house : House,
    var auto : Boolean
) : Serializable
