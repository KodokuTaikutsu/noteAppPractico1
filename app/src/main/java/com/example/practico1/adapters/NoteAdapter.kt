package com.example.practico1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practico1.R
import com.example.practico1.model.Note
import com.example.practico1.UI.NoteObserver
import android.widget.TextView

class NoteAdapter(
    private val noteList: ArrayList<Note>,
    private val context: Context,
    private val noteObserver: NoteObserver
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTextView.text = note.noteText
        holder.itemView.setBackgroundColor(note.noteColor)

        // Click to edit note
        holder.itemView.setOnClickListener {
            noteObserver.onNoteUpdated(note, position)
        }

        // Long press to show options (Delete or Change Color)
        holder.itemView.setOnLongClickListener {
            showOptionsDialog(note, position)
            true
        }
    }

    private fun showOptionsDialog(note: Note, position: Int) {
        val options = arrayOf("Change Color", "Delete")
        android.app.AlertDialog.Builder(context)
            .setTitle("Choose Action")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> noteObserver.onNoteColorChanged(note, position) // Change color
                    1 -> noteObserver.onNoteDeleted(position)             // Delete note
                }
            }
            .show()
    }

    override fun getItemCount(): Int = noteList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
    }
}
