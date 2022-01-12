package com.matheusvictor.habiticateammanager.service.listener

interface APIListener<T> {

    fun onSuccess(result: T, statusCode: Int)
    fun onFailure(errorMessage: String)

}
