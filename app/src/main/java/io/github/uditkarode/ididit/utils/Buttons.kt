package io.github.uditkarode.ididit.utils

import android.os.Parcel
import android.os.Parcelable

class Buttons() : Parcelable {
    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Buttons> {
        override fun createFromParcel(parcel: Parcel): Buttons {
            return Buttons(parcel)
        }

        override fun newArray(size: Int): Array<Buttons?> {
            return arrayOfNulls(size)
        }
    }

}