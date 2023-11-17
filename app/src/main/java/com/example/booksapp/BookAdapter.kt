package com.example.booksapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
//import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookAdapter(
    booksList: ArrayList<Books>,
    private val item: (Int) -> Unit,
    val delete : (Int)->Unit,
    private val edit : (Int) -> Unit

) : RecyclerView.Adapter<BookAdapter.BooksHolder>() {
    private var booksList: ArrayList<Books>
    init {
        this.booksList = booksList
    }
    class BooksHolder (v: View) : RecyclerView.ViewHolder(v) {
        val title= v.findViewById<TextView>(R.id.txtTitleBooks)
        val buttonEdit = v.findViewById<Button>(R.id.buttonEdit)
        val buttonDelete = v.findViewById<Button>(R.id.buttonDelete)

        fun render(thisBook: Books){
            title.text = thisBook.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.itembooks,parent,false)
        return (BooksHolder(v))
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        val item = booksList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            item(position)
        }
        holder.buttonDelete.setOnClickListener {
            delete(position)
        }
        holder.buttonEdit.setOnClickListener {
            edit(position)
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

}


