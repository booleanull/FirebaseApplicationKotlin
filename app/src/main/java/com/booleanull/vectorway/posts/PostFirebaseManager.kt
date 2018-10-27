package com.booleanull.vectorway.posts

import android.view.View
import android.widget.ProgressBar
import com.google.firebase.database.*

class PostFirebaseManager (val postAdapter: PostAdapter, val items : MutableList<ViewInPost>, val progressBar : ProgressBar){

    var event: Event = Event("", "")
    val posts: MutableList<Post> = mutableListOf<Post>()
    var count : Int = 0

    init {
        val database = FirebaseDatabase.getInstance()
        val eventReference = database.getReference("event")
        val postReference = database.getReference("posts")

        eventReference.addValueEventListener(getEventValueEventListener())
        postReference.addChildEventListener(getPostChildEventListener())
    }

    private fun notifyItemsArray() {
        items.clear()
        if(!event.title.equals(""))
            items.add(0, event)
        for (p in posts) {
            items.add(p as ViewInPost)
        }
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
                items[index+1] = post
                postAdapter.notifyItemChanged(index+1)
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                posts.add(dataSnapshot.getValue(Post::class.java)!!)
                count++
                if(count >= dataSnapshot.childrenCount) {
                    progressBar.visibility = View.GONE
                    notifyItemsArray()
                    postAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(Post::class.java)!!
                val index = getItemIndex(post)
                items.removeAt(index+1)
                postAdapter.notifyItemChanged(index+1)
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