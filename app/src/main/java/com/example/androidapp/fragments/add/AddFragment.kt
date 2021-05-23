package com.example.androidapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidapp.R
import com.example.androidapp.data.Post
import com.example.androidapp.data.PostViewModel
import com.example.androidapp.data.User
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import okhttp3.*
import java.io.IOException


class AddFragment : Fragment() {

    lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        view.addUser_btn.setOnClickListener {
            insertUserToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase(){
        val userId = addUserID_et.text
        val title = addTitle_et.text.toString()
        val body = addBody_et.text.toString()

        if(inputCheck(userId, title, body)) {
            val post = Post(
                0,
                Integer.parseInt(userId.toString()),
                title,
                body
            )

            mPostViewModel.addPost(post)
            Toast.makeText(requireContext(), "Added!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Fill out data fields!", Toast.LENGTH_LONG).show()
        }

    }

    private fun insertUserToDatabase(){
        val userName = addUserName_et.text.toString()

        if (inputCheckUser(userName)){
            val user = User(
                    0,
                    userName
            )
            mPostViewModel.addUser(user)
            Toast.makeText(requireContext(), "Added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Fill out data fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(userId: Editable, title: String, body: String): Boolean{
        return !(userId.isEmpty() && TextUtils.isEmpty(title) && TextUtils.isEmpty(body))
    }

    private fun inputCheckUser(userName: String):Boolean{
        return !(TextUtils.isEmpty(userName))
    }

}