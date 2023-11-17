package com.example.booksapp

//import com.bumptech.glide.Glide
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private lateinit var viewModel: DescriptionViewModel

    private lateinit var v : View
    private lateinit var title : TextView
    private lateinit var description : TextView

    private lateinit var idGlobal: sharedData
    private var db = Firebase.firestore

    private lateinit var booksList: ArrayList<Books>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_description, container, false)
        title = v.findViewById(R.id.titleBook)
        description = v.findViewById(R.id.descriptionBook)
        booksList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        idGlobal = ViewModelProvider(requireActivity()).get(sharedData::class.java)

        idGlobal.sharedID.observe(viewLifecycleOwner) { data1 ->
            db.collection("Books").document(data1).get().addOnSuccessListener {
                title.text = (it.data?.get("title").toString())
                description.text = (it.data?.get("description").toString())
            }.addOnFailureListener {
                Toast.makeText(context, "No se encontraron datos", Toast.LENGTH_SHORT).show()
            }
        }
        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DescriptionViewModel::class.java)
    }

}