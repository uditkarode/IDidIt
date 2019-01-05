package io.github.uditkarode.ididit.utils

class Constants {
    companion object {
        const val BASE_URL = "https://vast-everglades-91661.herokuapp.com/api/"
        const val SIGN_UP_ENDPOINT = "user/sign-up/"
        const val CHECK_USERNAME_ENDPOINT = "user/username/" //Append username to check for after this
        const val LOGIN_ENDPOINT = "user/login/"
        const val ADD_RESOLUTION_ENDPOINT = "resolution/save/"
        const val GET_RESOLUTIONS_ENDPOINT = "resolution/"
        const val DELETE_RESOLUTION_ENDPOINT = "resolution/delete/"
        const val UPDATE_STATUS_ENDPOINT = "/"

        const val LOGIN_GRADIENT_COLOR1 = "#70E1f5"
        const val LOGIN_GRADIENT_COLOR2 = "#FFD194"

        const val HOME_GRADIENT_COLOR1 = "#DDD6f3"
        const val HOME_GRADIENT_COLOR2 = "#FAACA8"

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