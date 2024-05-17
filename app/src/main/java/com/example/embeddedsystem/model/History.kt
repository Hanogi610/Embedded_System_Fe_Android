package com.example.embeddedsystem.model

import java.io.Serializable

data class History (
    var id: Int = 0,
    var deviceName: String = "",
    var status: Boolean = false,
    var time: Long = 0,
    var house: House
) : Serializable