package com.owen.testcrud

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.owen.testcrud.BookmarkRoom.BookmarkCRUD
import kotlinx.android.synthetic.main.activity_bookmark_adapter.view.*


class BookmarkAdapter (private val bookmarks: ArrayList<BookmarkCRUD>, private val
listener: OnAdapterListener): RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_bookmark_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.view.text_title.text = bookmark.namaHotel
        holder.view.text_title.setOnClickListener{
            listener.onClick(bookmark)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(bookmark)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(bookmark)
        }
    }

    override fun getItemCount() = bookmarks.size
    inner class BookmarkViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BookmarkCRUD>){
        bookmarks.clear()
        bookmarks.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(bookmark: BookmarkCRUD)
        fun onUpdate(bookmark: BookmarkCRUD)
        fun onDelete(bookmark: BookmarkCRUD)
    }
}