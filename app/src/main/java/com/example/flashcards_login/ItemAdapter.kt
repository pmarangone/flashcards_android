package com.example.flashcards_login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items.view.*

class ItemAdapter(
  var items: List<Deck>,
  val listener: OnItemClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    init {
      itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      val position = adapterPosition
      if (position != RecyclerView.NO_POSITION) {
        listener.onItemClick(position)
      }
    }
  }

  interface OnItemClickListener {
    fun onItemClick(position: Int)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    // todo: add info
    val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
    return ItemViewHolder(view)
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.itemView.apply {
      tvName.text = items[position].name
      tvDescription.text = items[position].description
    }
  }

  override fun getItemCount(): Int {
    return items.size
  }


}