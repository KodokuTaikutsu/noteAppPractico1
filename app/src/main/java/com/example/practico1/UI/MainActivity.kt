package com.example.practico1.UI

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico1.adapters.NoteAdapter
import com.example.practico1.databinding.ActivityMainBinding
import com.example.practico1.model.Note

class MainActivity : AppCompatActivity(), NoteObserver {

    private lateinit var binding: ActivityMainBinding
    private val noteList: ArrayList<Note> = ArrayList()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(noteList, this, this)
        binding.recyclerView.adapter = noteAdapter

        // Save button action
        binding.saveButton.setOnClickListener {
            val text = binding.noteInput.text.toString()
            if (text.isNotEmpty()) {
                val newNote = Note(text, Color.WHITE)
                noteList.add(newNote)

                // Notify only the inserted item
                noteAdapter.notifyItemInserted(noteList.size - 1)

                binding.noteInput.text.clear() // Clear input after saving
            }
        }
    }

    // Helper function to show a dialog for editing a note
    private fun showEditNoteDialog(note: Note, position: Int) {
        val editText = EditText(this)
        editText.setText(note.noteText)

        AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                note.noteText = editText.text.toString()
                noteAdapter.notifyItemChanged(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Helper function to show a dialog for changing note color
    private fun showColorPickerDialog(note: Note, position: Int) {
        val colors = arrayOf("Red", "Blue", "Green", "Yellow", "Cyan", "Magenta", "Gray", "Orange", "Purple", "Pink")
        val colorValues = intArrayOf(
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.GRAY,
            Color.parseColor("#FFA500"), // Orange
            Color.parseColor("#800080"), // Purple
            Color.parseColor("#FFC0CB")  // Pink
        )

        AlertDialog.Builder(this)
            .setTitle("Choose a color")
            .setItems(colors) { _, which ->
                note.noteColor = colorValues[which]
                noteAdapter.notifyItemChanged(position)
            }
            .show()
    }

    // Observer methods implementation
    override fun onNoteUpdated(note: Note, position: Int) {
        showEditNoteDialog(note, position)
    }

    override fun onNoteDeleted(position: Int) {
        noteList.removeAt(position)
        noteAdapter.notifyItemRemoved(position)
    }

    override fun onNoteColorChanged(note: Note, position: Int) {
        showColorPickerDialog(note, position)
    }
}
