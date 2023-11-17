package com.example.booksapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateUserFragment : Fragment() {

    companion object {
        fun newInstance() = CreateUserFragment()
    }

    private lateinit var viewModelSignIn: CreateUserViewModel
    private lateinit var viewModelLogin : LoginScreenViewModel
    private lateinit var v : View
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var createButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_create_user, container, false)
        username = v.findViewById(R.id.createUsername)
        password = v.findViewById(R.id.createPassword)
        createButton = v.findViewById(R.id.createButton)
        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelSignIn = ViewModelProvider(requireActivity()).get(CreateUserViewModel::class.java)
        viewModelLogin = ViewModelProvider(requireActivity()).get(LoginScreenViewModel::class.java)

        createButton.setOnClickListener {
            val newUser: String = username.text.toString()
            val newPass: String = password.text.toString()

            print(newUser)

            auth = Firebase.auth
            auth.createUserWithEmailAndPassword(newUser, newPass).addOnCompleteListener {  }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_createUserFragment_to_loginScreen)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "That mail already has an account or doesnt exist", Toast.LENGTH_SHORT,).show()
                    }
                }
        }

    }
}


