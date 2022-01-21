package com.chskela.shoppinglist.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import com.chskela.shoppinglist.R
import com.chskela.shoppinglist.databinding.ActivityNewNoteBinding
import com.chskela.shoppinglist.entities.NoteItem
import com.chskela.shoppinglist.fragments.NoteFragment
import com.chskela.shoppinglist.utils.HtmlManager
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
        when (item.itemId) {
            R.id.id_save -> {
                setMainResult()
            }
            R.id.id_color -> {
                showColorPicker()
            }
            R.id.id_bold -> {
                setBoldForSelectedText()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult() {
        val intent = Intent().apply {
            if (note != null) {
                putExtra(
                    NoteFragment.EDIT_NOTE_KEY,
                    note?.copy(
                        title = binding.edTitle.text.toString(),
                        content = HtmlManager.getHtmlFromText(binding.edDescription.text)
                    )
                )
            } else {
                putExtra(NoteFragment.NEW_NOTE_KEY, createNewNote())
            }

        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showColorPicker() = with(binding) {
        if (colorPicker.isShown) {
            closeColorPicker()
        } else {
            openColorPicker()
        }
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edDescription.selectionStart
        val endPos = edDescription.selectionEnd
        val styles = edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null

        if (styles.isNotEmpty()) {
            edDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }

        edDescription.text.setSpan(
            boldStyle,
            startPos,
            endPos,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        edDescription.text.trim()
    }

    private fun getNote() {
        note = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as? NoteItem
        fillNote()
    }

    private fun fillNote() = with(binding) {
        if (note != null) {
            edTitle.setText(note?.title)
            edDescription.setText(HtmlManager.getTextFromHtml(note?.content ?: "").trim())
        }
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.getHtmlFromText(binding.edDescription.text),
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

    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnimation = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnimation)
    }

    private fun closeColorPicker() {
        val closeAnimation = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        binding.colorPicker.startAnimation(closeAnimation)
        binding.colorPicker.visibility = View.GONE
    }
}