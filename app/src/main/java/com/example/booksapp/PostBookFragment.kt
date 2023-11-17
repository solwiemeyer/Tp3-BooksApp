package com.example.booksapp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostBookFragment : Fragment() {
    companion object {
        fun newInstance() = PostBookFragment()
    }

    //private lateinit var viewModel: PostBookViewModel
    private lateinit var textTitle: EditText
    private lateinit var textDescription: EditText

    private var db = Firebase.firestore
    private lateinit var uploadNewBook: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_post_book, container, false)
        textTitle = v.findViewById(R.id.textTitle)
        textDescription = v.findViewById(R.id.textDescription)
        uploadNewBook = v.findViewById(R.id.botonSubir)

        uploadNewBook.setOnClickListener {
            val documentId:String = db.collection("Books").document().id

            val newBook = hashMapOf(
                "title" to textTitle.text.toString(),
                "description" to textDescription.text.toString(),
                "idBook" to documentId
            )

            db.collection("Books").document(documentId).set(newBook)
                .addOnSuccessListener {}
                .addOnFailureListener { e -> Toast.makeText(context, "error", Toast.LENGTH_SHORT).show() }

            findNavController().navigate(R.id.action_postBookFragment_to_bookListFragment)
        }
        return v
    }


}