package io.github.uditkarode.ididit.utils

class Constants {
    companion object {
        const val BASE_URL = "https://ididit-api.herokuapp.com/"

        const val SIGN_UP_ENDPOINT = "user/register"
        const val LOGIN_ENDPOINT = "user/login"

        const val ADD_RESOLUTION_ENDPOINT = "habit/add"
        const val GET_RESOLUTIONS_ENDPOINT = "habit/get"
        const val SET_RESOLUTION_STATUS_ENDPOINT = "habit/set_status"
        const val DELETE_RESOLUTION_ENDPOINT = "habit/delete"

        const val GET_HISTORY_ENDPOINT = "history/get"

        const val LOGIN_GRADIENT_COLOR1 = "#70E1f5"
        const val LOGIN_GRADIENT_COLOR2 = "#FFD194"

        const val HOME_GRADIENT_COLOR1 = "#ff512f"
        const val HOME_GRADIENT_COLOR2 = "#f09819"

        const val HABIT_GRADIENT_COLOR1 = "#DDD6F3"
        const val HABIT_GRADIENT_COLOR2 = "#70E1F5"

        const val HABIT_COMPLETED_GRADIENT_COLOR1 = "#A8FF78"
        const val HABIT_COMPLETED_GRADIENT_COLOR2 = "#78FFd6"

        const val HABIT_FAILED_GRADIENT_COLOR1 = "#ED213A"
        const val HABIT_FAILED_GRADIENT_COLOR2 = "#93291E"

        const val POSITIVE_BUTTON_COLOR = "#43A047"
        const val NEGATIVE_BUTTON_COLOR = "#B71C1C"

        const val COLOR_DISABLED = "#424242"
    }
}