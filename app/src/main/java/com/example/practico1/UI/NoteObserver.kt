package com.example.practico1.UI

import com.example.practico1.model.Note

interface NoteObserver {
    fun onNoteUpdated(note: Note, position: Int)
    fun onNoteDeleted(position: Int)
    fun onNoteColorChanged(note: Note, position: Int)
}
