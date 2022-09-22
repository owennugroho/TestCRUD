package com.owen.testcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.owen.testcrud.BookmarkRoom.BookmarkCRUD
import com.owen.testcrud.BookmarkRoom.BookmarkCRUDDB
import com.owen.testcrud.BookmarkRoom.Constant
import kotlinx.android.synthetic.main.activity_edit_bookmark.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditBookmark : AppCompatActivity() {

    val db by lazy { BookmarkCRUDDB(this)}
    private var bookmarkcrudId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bookmark)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getBookmark()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getBookmark()
            }
        }
    }

    private fun setupListener(){
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.bookmarkCRUDDao().updateBookmark(
                    BookmarkCRUD(bookmarkcrudId, edit_nama_hotel.text.toString(),
                        edit_wilayah_hotel.text.toString(), edit_alamat_hotel.text.toString())
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.bookmarkCRUDDao().updateBookmark(
                    BookmarkCRUD(bookmarkcrudId, edit_nama_hotel.text.toString(),
                        edit_wilayah_hotel.text.toString(), edit_alamat_hotel.text.toString())
                )
                finish()
            }
        }
    }

    fun getBookmark(){
        bookmarkcrudId = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarks = db.bookmarkCRUDDao().getBookmark(bookmarkcrudId)[0]
            edit_nama_hotel.setText(bookmarks.namaHotel)
            edit_wilayah_hotel.setText(bookmarks.wilayahHotel)
            edit_alamat_hotel.setText(bookmarks.alamatHotel)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}