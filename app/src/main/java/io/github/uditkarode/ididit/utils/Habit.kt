package io.github.uditkarode.ididit.utils

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

enum class HabitStatus {COMPLETED, FAILED, NOT_MARKED}

class Habit(name: String, buttonType: List<Buttons>): ExpandableGroup<Buttons>(name, buttonType)