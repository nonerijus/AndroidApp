package com.example.androidapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidapp.R
import com.example.androidapp.data.Post
import com.example.androidapp.data.PostViewModel
import com.example.androidapp.data.User
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_list.view.*
import okhttp3.*
import java.io.IOException

class ListFragment : Fragment() {


    private lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        // Recycler View
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //PostViewModel
        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mPostViewModel.readAllData.observe(viewLifecycleOwner, Observer {post ->
            adapter.setData(post)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        view.swipeContainer.setOnRefreshListener {
            Toast.makeText(requireContext(), "Refreshing", Toast.LENGTH_LONG).show()
            fetchJson()
            fetchJsonUser()
            view.swipeContainer.isRefreshing = false
        }
        return view
    }

    fun insertDataToDatabaseFromApi(id: Int, userId: Int, title: String, body: String){
        val id = id
        val userId = userId
        val title = title
        val body = body

        val post = Post(id, userId, title, body)
        mPostViewModel.addPost(post)
    }

    fun insertUserDataToDatabaseFromApi(id: Int, name: String){
        val id = id
        val name = name

        val user = User(id, name)
        mPostViewModel.addUser(user)
    }

    private fun fetchJson(){
        val url = "https://jsonplaceholder.typicode.com/posts"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response){
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, Array<Post>::class.java).toList()
                val post = arrayOfNulls<String>(homeFeed.size)

                mPostViewModel.deleteAllPosts()

                for (post in homeFeed){
                    insertDataToDatabaseFromApi(homeFeed[post.id-1].id, homeFeed[post.id-1].userId, homeFeed[post.id-1].title, homeFeed[post.id-1].body)
                }
                //runOnUiThread{
                // recyclerview.adapter = MainAdapter(homeFeed)
                //}
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed")
            }
        })
    }

    private fun fetchJsonUser(){
        val url = "https://jsonplaceholder.typicode.com/users"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response){
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val usersFeed = gson.fromJson(body, Array<User>::class.java).toList()
                val user = arrayOfNulls<String>(usersFeed.size)

                for (user in usersFeed){
                    insertUserDataToDatabaseFromApi(usersFeed[user.id-1].id, usersFeed[user.id-1].name)
                }
                //runOnUiThread{
                // recyclerview.adapter = ListAdapter(homeFeed)
                //}
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed")
            }
        })
    }

    private fun refreshPosts(){

    }
}