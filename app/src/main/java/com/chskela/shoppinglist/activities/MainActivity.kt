package com.chskela.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chskela.shoppinglist.R
import com.chskela.shoppinglist.databinding.ActivityMainBinding
import com.chskela.shoppinglist.fragments.FragmentManager
import com.chskela.shoppinglist.fragments.NoteFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

private fun setBottomNavListener() {
    binding.bNav.setOnItemSelectedListener {
        when(it.itemId) {
            R.id.settings -> {

            }
            R.id.notes -> {
                FragmentManager.setFragment(NoteFragment.newInstance(), this)
            }
            R.id.shop_list -> {

            }
            R.id.new_item -> {
                FragmentManager.currentFragment?.onClickNew()
            }
        }
        true
    }
}
}