package com.matheusvictor.habiticateammanager.service.model

data class UserHeaderModel(
    val apiToken: String,
    val id: String,
    val newUser: Boolean,
    val username: String
)