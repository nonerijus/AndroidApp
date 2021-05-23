package com.example.androidapp.fragments.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.androidapp.R
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment : Fragment() {


    private val args by navArgs<PostFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        view.title_tv.setText(args.currentPost.title)
        view.body_tv.setText(args.currentPost.body)

        return view
    }
}