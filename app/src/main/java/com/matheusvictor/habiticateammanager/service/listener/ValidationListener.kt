package com.matheusvictor.habiticateammanager.service.listener

class ValidationListener(errorMessage: String = "") {

    private var mStatus: Boolean = true
    private var mValidationMessage: String = ""

    init {
        if (errorMessage != "") {
            mStatus = false
            mValidationMessage = errorMessage
        }
    }

    fun validationSuccess() = mStatus
    fun validationFailure() = mValidationMessage

}
