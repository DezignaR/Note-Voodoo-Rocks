package com.notes.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
@Parcelize
@Entity(tableName = "notes")
data class
NoteDbo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "createdAt")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "modifiedAt")
    val modifiedAt: LocalDateTime,
) : Parcelable