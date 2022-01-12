package com.matheusvictor.habiticateammanager.service.model

import com.google.gson.annotations.SerializedName

class LoginDataModel {

    @SerializedName("success")
    var success: Boolean = false

    @SerializedName("data")
    lateinit var data: UserHeaderModel

}
