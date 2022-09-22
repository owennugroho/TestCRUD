package com.owen.testcrud

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.owen.testcrud.BookmarkRoom.BookmarkCRUD
import com.owen.testcrud.BookmarkRoom.BookmarkCRUDDB
import com.owen.testcrud.BookmarkRoom.Constant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { BookmarkCRUDDB(this) }
    lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        bookmarkAdapter = BookmarkAdapter(arrayListOf(), object :
            BookmarkAdapter.OnAdapterListener{
            override fun onClick(bookmark: BookmarkCRUD) {
                intentEdit(bookmark.id, Constant.TYPE_READ)
            }

            override fun onUpdate(bookmark: BookmarkCRUD) {
                intentEdit(bookmark.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(bookmark: BookmarkCRUD) {
                deleteDialog(bookmark)
            }
        })
        list_hotel.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bookmarkAdapter
        }
    }

    private fun deleteDialog(bookmark: BookmarkCRUD){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are You Sure to delete this data From ${bookmark.namaHotel}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.bookmarkCRUDDao().deleteBookmark(bookmark)
                    loadData()
                }
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.bookmarkCRUDDao().getBookmark()
            Log.d("MainActivity","dbResponse: $notes")
            withContext(Dispatchers.Main){
                bookmarkAdapter.setData( notes )
            }
        }
    }

    fun setupListener(){
        button_create.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(bookmarkId : Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditBookmark::class.java)
                .putExtra("intent_id", bookmarkId)
                .putExtra("intent_type", intentType)
        )
    }
}