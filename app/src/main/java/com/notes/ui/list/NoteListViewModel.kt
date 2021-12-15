package com.notes.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.data.NoteDatabase
import com.notes.data.NoteDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val noteDatabase: NoteDatabase
) : ViewModel() {

    private val _notes = MutableLiveData<List<NoteDbo>?>()
    val notes: LiveData<List<NoteDbo>?> = _notes

    private val _navigateToNoteCreation = MutableLiveData<NoteDbo?>()
    val navigateToNoteCreation: LiveData<NoteDbo?> = _navigateToNoteCreation

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _notes.postValue(
                getNotesList()
            )
        }
    }

    fun onDeleteNoteClick(note: NoteDbo) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDatabase.noteDao().deleteNote(note)
            _notes.postValue(
                getNotesList()
            )
        }
    }

    private fun getNotesList(): List<NoteDbo> =
        noteDatabase.noteDao().getAll().map {
            NoteDbo(
                it.id,
                it.title,
                it.content,

                it.createdAt,
                it.modifiedAt
            )
        }

    fun onCreateNoteClick() {
        _navigateToNoteCreation.postValue(null)
    }

    fun onEditNoteClick(note: NoteDbo) {
        _navigateToNoteCreation.postValue(note)
    }
}
