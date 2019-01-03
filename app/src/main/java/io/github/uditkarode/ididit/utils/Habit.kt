package io.github.uditkarode.ididit.utils

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Habit(name: String, buttonType: List<Buttons>): ExpandableGroup<Buttons>(name, buttonType)