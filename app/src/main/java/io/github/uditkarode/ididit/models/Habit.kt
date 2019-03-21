package io.github.uditkarode.ididit.models

import io.github.uditkarode.ididit.utils.HabitStatus

class Habit(name: String, stat: HabitStatus) {
    val habitName: String = name
    val habitStatus: HabitStatus = stat
}