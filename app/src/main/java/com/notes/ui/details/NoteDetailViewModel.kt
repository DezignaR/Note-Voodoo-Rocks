package com.notes.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.data.NoteDatabase
import com.notes.data.NoteDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(
    private val noteDatabase: NoteDatabase
) : ViewModel() {

    private val _navigateToNoteList = MutableLiveData<Unit?>()
    val navigateToNoteCreation: LiveData<Unit?> = _navigateToNoteList

    private val _setNoteEdit = MutableLiveData<NoteDbo?>()
    val setNoteEdit: LiveData<NoteDbo?> = _setNoteEdit

    private var editNote: NoteDbo? = null

    fun createNote(
        id: Long = 0,
        title: String,
        content: String,
        createdAt: LocalDateTime = LocalDateTime.now()
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDatabase.noteDao().insertAll(
                if (editNote == null)
                    NoteDbo(
                        id,
                        title,
                        content,
                        createdAt,
                        LocalDateTime.now()
                    ) else NoteDbo(
                    editNote!!.id,
                    title,
                    content,
                    editNote!!.createdAt,
                    LocalDateTime.now()
                )
            )
        }
    }

    fun onCreateNoteClick() {
        _navigateToNoteList.postValue(Unit)
    }

    fun setNoteEdit(note: NoteDbo?) {
        if (note != null)
            editNote = note
        _setNoteEdit.postValue(note)
    }

}