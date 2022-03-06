package com.matheusvictor.habiticateammanager.service.constants

class AppConstants {

    object API {
        private const val API_VERSION = "v3"
        const val BASE_URL = "https://habitica.com/api/${API_VERSION}/"
    }

    object HTTP {
        const val SUCCESS = 200
    }

    object HEADER {
        const val USERNAME = "username"
        const val API_TOKEN = "apiToken"
    }

    // Shared Preferences
    object SHARED {
        const val USERNAME = "username"
        const val API_TOKEN = "apiToken"
    }

}
