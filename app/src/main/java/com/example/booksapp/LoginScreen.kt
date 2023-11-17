package com.example.booksapp

import android.annotation.SuppressLint
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreen : Fragment() {

    companion object {
        fun newInstance() = LoginScreen()
    }

    private lateinit var viewModel: LoginScreenViewModel
    private lateinit var v : View
    private lateinit var userWritten: EditText
    private lateinit var passWritten: EditText
    private lateinit var confirmButton: Button
    private lateinit var signInButton: Button
    private lateinit var auth: FirebaseAuth
    lateinit var userText: String
    lateinit var passText: String

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login_screen, container, false)
        userWritten = v.findViewById(R.id.editUser)
        passWritten = v.findViewById(R.id.editPass)
        confirmButton = v.findViewById(R.id.loginButton)
        signInButton = v.findViewById(R.id.createUserButton)
        return v
    }


    //   override fun onActivityCreated(savedInstanceState: Bundle?) {
    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(requireActivity()).get(LoginScreenViewModel::class.java)

        confirmButton.setOnClickListener {
            userText = userWritten.text.toString()
            passText = passWritten.text.toString()
            auth = Firebase.auth
            auth.signInWithEmailAndPassword(userText, passText).addOnCompleteListener { }
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithUser:success")
                    val userWritten = auth.currentUser
                    findNavController().navigate(R.id.action_loginScreen_to_bookListFragment)
                } else {
                    Log.w(ContentValues.TAG, "signInWithUser:failure", task.exception)
                    Toast.makeText(context, "Username and password are incorrect", Toast.LENGTH_SHORT,).show()
                    }
                }
            }
        signInButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginScreen_to_createUserFragment)
        }
    }
}