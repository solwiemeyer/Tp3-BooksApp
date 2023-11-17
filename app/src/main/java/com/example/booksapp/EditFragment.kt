package com.example.booksapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditFragment : Fragment() {

    private lateinit var viewModel: EditViewModel
    private lateinit var idGlobal: sharedData
    private var db = Firebase.firestore

    private lateinit var newTitle: EditText
    private lateinit var newDescription: EditText

    private lateinit var bookID: String

    private lateinit var uploadBook: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_edit, container, false)
        newTitle = v.findViewById(R.id.tituloNuevo)
        newDescription = v.findViewById(R.id.descripcionNueva)
        uploadBook = v.findViewById(R.id.botonSubir)

        db = FirebaseFirestore.getInstance()

        idGlobal = ViewModelProvider(requireActivity()).get(sharedData::class.java)

        idGlobal.sharedID.observe(viewLifecycleOwner) { data1 ->
            db.collection("Books").document(data1).get().addOnSuccessListener {
                newTitle.setText(it.data?.get("title").toString())
                newDescription.setText(it.data?.get("description").toString())
                bookID = it.data?.get("idBook").toString()
            }.addOnFailureListener {
                Toast.makeText(context, "cant find information", Toast.LENGTH_SHORT).show()
            }

            uploadBook.setOnClickListener {
                val newBook = hashMapOf(
                    "title" to newTitle.text.toString(),
                    "description" to newDescription.text.toString(),
                    "idBook" to bookID
                )

                db.collection("Books").document(data1).set(newBook)
                    .addOnSuccessListener {
                        Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show()
                    }
                findNavController().navigate(R.id.action_editFragment_to_bookListFragment)
            }
        }
        return v
    }
}