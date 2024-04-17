package com.example.utilsuser.cutomerview.flow_optimize

import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState

class SaveState : BaseSavedState{
    var lineVerticalGravity = OptimizedFlowLayout.LINE_VERTICAL_GRAVITY_CENTER_VERTICAL
    var maxLines = Int.MAX_VALUE
    var maxCount = Int.MAX_VALUE

    constructor(parcelable: Parcelable?) : super(parcelable)
    constructor(parcel: Parcel) : super(parcel) {
        maxCount = parcel.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(maxCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SaveState> {
        override fun createFromParcel(source: Parcel): SaveState {
            return SaveState(source)
        }

        override fun newArray(size: Int): Array<SaveState?> {
            return arrayOfNulls(size)
        }
    }
}