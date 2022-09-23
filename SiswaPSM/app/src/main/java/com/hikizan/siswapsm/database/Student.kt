package com.hikizan.siswapsm.database

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String? = null,

    @ColumnInfo(name = "isMale")
    var isMale: Boolean? = false,

    @ColumnInfo(name = "image")
    var image: String? = null

) : Parcelable