package com.chskela.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chskela.shoppinglist.R
import com.chskela.shoppinglist.databinding.NoteListItemBinding
import com.chskela.shoppinglist.entities.NoteItem


class NoteAdapter : ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val noteItem = getItem(position)
        holder.setData(noteItem)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = NoteListItemBinding.bind(view)

        fun setData(noteItem: NoteItem) = with(binding) {
            tvTitle.text = noteItem.title
            tvDescription.text = noteItem.content
            tvTime.text = noteItem.time
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.note_list_item, parent, false)
                )
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<NoteItem>(){
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }
}