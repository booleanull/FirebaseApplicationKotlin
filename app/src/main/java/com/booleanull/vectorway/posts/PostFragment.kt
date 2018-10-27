package com.booleanull.vectorway.posts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.vectorway.R
import kotlinx.android.synthetic.main.fragment_post.*


class NewsFragment : Fragment() {

    class ViewPostList(val items : MutableList<ViewInPost>) : MutableList<ViewInPost> by items
    private val viewPostList : ViewPostList = ViewPostList(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_post, container, false)

        val postAdapter = PostAdapter(layoutInflater, viewPostList)
        PostFirebaseManager(postAdapter, viewPostList)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = postAdapter
        return v
    }
}
