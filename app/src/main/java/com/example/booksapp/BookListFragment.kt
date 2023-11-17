package com.example.booksapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast

//import com.google.android.play.core.integrity.v


@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class BookListFragment : Fragment() {
    private lateinit var v : View
    private lateinit var recBooks : RecyclerView

    private lateinit var booksList: ArrayList<Books>

    var db = Firebase.firestore
    private lateinit var buttonAdd: Button
    private lateinit var idBookActual: String
    private lateinit var idGlobal: sharedData

    private lateinit var adapter : BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.book_list_fragment, container, false)
        recBooks = v.findViewById(R.id.recBooks)
        db = FirebaseFirestore.getInstance()
        recBooks.layoutManager = LinearLayoutManager(context)
        booksList = arrayListOf()
        buttonAdd = v.findViewById(R.id.buttonAdd)

        initRecyclerView()

        buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_bookListFragment_to_postBookFragment)
        }
        return v
    }

    private fun initRecyclerView() {
        db.collection("Books").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val book:Books? = data.toObject<Books>(Books::class.java)
                    booksList.add(book!!)
                }

                adapter = BookAdapter(booksList,
                    delete = {position -> deleteBook(position) },
                    edit = {position -> editBook(position) },
                    item = {position -> seeBookData(position)} )

                recBooks.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seeBookData(position:Int) {
        idBookActual = booksList[position].idBook.toString()
        idGlobal = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idGlobal.sharedID.value = idBookActual

        findNavController().navigate(R.id.action_bookListFragment_to_descriptionFragment)

    }
    fun editBook(position: Int) {
        idBookActual = booksList[position].idBook.toString()
        idGlobal = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idGlobal.sharedID.value = idBookActual

        findNavController().navigate(R.id.action_bookListFragment_to_editFragment)
    }

    fun deleteBook (position : Int){
        db.collection("Books").document(booksList[position].idBook.toString()).delete()
            .addOnSuccessListener {
                adapter.notifyItemRemoved(position)
                booksList.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"Error in deleting book", Toast.LENGTH_SHORT).show() }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }
}

