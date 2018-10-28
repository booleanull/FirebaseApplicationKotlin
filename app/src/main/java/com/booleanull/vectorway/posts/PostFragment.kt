package com.booleanull.vectorway.posts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.booleanull.vectorway.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import android.support.v4.view.MenuItemCompat
import android.R.menu
import android.support.v7.widget.SearchView
import android.view.*


class NewsFragment : Fragment() {

    private val viewPostList : MutableList<ViewInPost> = mutableListOf()
    lateinit var postFirebaseManager : PostFirebaseManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_post, container, false)
        val recyclerView = v.recyclerView
        val progressBar = v.progressBar

        activity!!.title = inflater.context.resources.getString(R.string.post)
        setHasOptionsMenu(true);

        val postAdapter = PostAdapter(layoutInflater, viewPostList)
        postFirebaseManager = PostFirebaseManager(postAdapter, viewPostList, progressBar)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = postAdapter
        return v
    }

    override fun onCreateOptionsMenu(menu : Menu, inflater : MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post_search, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(search) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                postFirebaseManager.filter.filter(newText)
                return true
            }
        })
    }
}
