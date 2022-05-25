package com.marva.notes.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// anotasi entity untuk menandakan bahwa sebuah data classdijadikan sebuah table database
@Entity(tableName = "notes_table")
@Parcelize
data class Notes (
    // untuk id didalam table supaya tidak duplikat
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String,
    var date: String
) //: Parcelab
    : Parcelable

// column itu kebawah tabelnya
// row itu kepinggir tabelnya