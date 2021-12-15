package com.notes.data

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY modifiedAt DESC")
    fun getAll(): List<NoteDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: NoteDbo)

    @Delete
    fun deleteNote(vararg note: NoteDbo)

}