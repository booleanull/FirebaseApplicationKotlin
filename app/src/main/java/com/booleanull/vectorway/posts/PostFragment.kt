package com.booleanull.vectorway.posts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.booleanull.vectorway.R
import com.google.firebase.database.*


class NewsFragment : Fragment() {

    var event: Event = Event("", "")
    val posts: MutableList<Post> = mutableListOf<Post>()
    var items: MutableList<ViewInPost> = mutableListOf<ViewInPost>()
    lateinit var postAdapter : PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_post, container, false)

        val database = FirebaseDatabase.getInstance()
        val eventReference = database.getReference("event")
        val postReference = database.getReference("posts")

        postAdapter = PostAdapter(layoutInflater, items)
        val recycler: RecyclerView = v.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = postAdapter

        eventReference.addValueEventListener(getEventValueEventListener())
        postReference.addChildEventListener(getPostChildEventListener())
        return v
    }

    private fun notifyItemsArray() {
        items.clear()
        for (p in posts) {
            items.add(p as ViewInPost)
        }
        if(!event.title.equals(""))
            items.add(0, event)
    }

    private fun getEventValueEventListener() : ValueEventListener {
        return object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                event = dataSnapshot.getValue(Event::class.java)!!
                notifyItemsArray()
                postAdapter.notifyItemChanged(0)
            }

        }
    }

    private fun getPostChildEventListener(): ChildEventListener {
        return object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                val post = dataSnapshot.getValue(Post::class.java)!!
                val index = getItemIndex(post)
                posts[index] = post
                notifyItemsArray()
                postAdapter.notifyItemChanged(index)
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                posts.add(dataSnapshot.getValue(Post::class.java)!!)
                notifyItemsArray()
                postAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(Post::class.java)!!
                val index = getItemIndex(post)
                posts.removeAt(index)
                notifyItemsArray()
                postAdapter.notifyItemChanged(index)
            }

        }
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
