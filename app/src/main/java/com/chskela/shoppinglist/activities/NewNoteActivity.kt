package com.chskela.shoppinglist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.chskela.shoppinglist.R
import com.chskela.shoppinglist.databinding.ActivityNewNoteBinding
import com.chskela.shoppinglist.entities.NoteItem
import com.chskela.shoppinglist.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewNoteBinding

    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_save) {
            setMainResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getNote() {
        note = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as? NoteItem
        fillNote()
    }

    private fun fillNote() = with(binding) {
        if (note != null) {
            edTitle.setText(note?.title)
            edDescription.setText(note?.content)
        }
    }

    private fun setMainResult() {
        val intent = Intent().apply {
            if (note != null) {
                putExtra(
                    NoteFragment.EDIT_NOTE_KEY,
                    note?.copy(
                        title = binding.edTitle.text.toString(),
                        content = binding.edDescription.text.toString()
                    )
                )
            } else {
                putExtra(NoteFragment.NEW_NOTE_KEY, createNewNote())
            }

        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime(),
            ""
        )
    }

    private fun getCurrentTime(): String = SimpleDateFormat(
        "hh:mm:ss - yyyy/MM/dd",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)


    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}