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
import com.chskela.shoppinglist.activities.MainApp
import com.chskela.shoppinglist.activities.NewNoteActivity
import com.chskela.shoppinglist.databinding.FragmentNoteBinding
import com.chskela.shoppinglist.viewmodels.MainViewModel


class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding

    private lateinit var editLauncher: ActivityResultLauncher<Intent>

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
//        mainViewModel.allNotes.observe(this, {
//            it
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {

            }
        }
    }

    companion object {
        const val TITLE_KEY = "title_key"
        const val DESC_KEY = "desc_key"
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}