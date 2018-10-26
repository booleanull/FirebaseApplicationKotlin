package com.booleanull.vectorway.posts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.vectorway.R
import com.google.firebase.database.*


class NewsFragment : Fragment() {

    val posts: MutableList<Post> = mutableListOf<Post>()
    lateinit var postAdapter : PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_post, container, false)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("posts")

        postAdapter = PostAdapter(layoutInflater, posts)
        val recycler: RecyclerView = v.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = postAdapter

        myRef.addChildEventListener(getChildEventListener())
        return v
    }

    fun getChildEventListener() : ChildEventListener {
        val c = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                val post = dataSnapshot.getValue(Post::class.java)!!
                val index = getItemIndex(post)
                posts[index] = post
                postAdapter.notifyItemChanged(index)
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                posts.add(dataSnapshot.getValue(Post::class.java)!!)
                postAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(Post::class.java)
                val index = getItemIndex(post!!)
                posts.removeAt(index)
                postAdapter.notifyItemChanged(index)
            }

        }
        return c
    }

    private fun getItemIndex(post: Post): Int {
        var index = -1

        for ((id) in posts) {
            if (id == post.id) {
                index = id
                break
            }
        }
        return index
    }
}
