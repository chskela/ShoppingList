package com.chskela.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chskela.shoppinglist.activities.MainApp
import com.chskela.shoppinglist.activities.NewNoteActivity
import com.chskela.shoppinglist.adapters.NoteAdapter
import com.chskela.shoppinglist.databinding.FragmentNoteBinding
import com.chskela.shoppinglist.entities.NoteItem
import com.chskela.shoppinglist.viewmodels.MainViewModel


class NoteFragment : BaseFragment(), NoteAdapter.Listener {

    private lateinit var binding: FragmentNoteBinding

    private lateinit var editLauncher: ActivityResultLauncher<Intent>

    private lateinit var adapter: NoteAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun observer() {
        mainViewModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun initRcView() = with(binding) {
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter(this@NoteFragment)
        rcViewNote.adapter = adapter
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.getSerializableExtra(NEW_NOTE_KEY) != null) {
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                } else {
                    mainViewModel.updateNote(it.data?.getSerializableExtra(EDIT_NOTE_KEY) as NoteItem)
                }
            }
        }
    }

    override fun deleteItem(noteItem: NoteItem) {
        mainViewModel.deleteNote(noteItem)
    }

    override fun onClickItem(noteItem: NoteItem) {
        val intent = Intent(Intent(activity, NewNoteActivity::class.java))
        intent.putExtra(NEW_NOTE_KEY, noteItem)
        editLauncher.launch(intent)
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_NOTE_KEY = "edit_note_key"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}