package com.booleanull.vectorway.posts

import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.ProgressBar
import com.google.firebase.database.*

class PostFirebaseManager (val postAdapter: PostAdapter, val items : MutableList<ViewInPost>, val progressBar : ProgressBar) : Filterable{

    var event: Event = Event()

    val posts: MutableList<Post> = mutableListOf()
    var sortPosts : MutableList<Post> = mutableListOf()

    var search : String = ""
    var count : Int = 0

    init {
        val database = FirebaseDatabase.getInstance()
        val eventReference = database.getReference("event")
        val postReference = database.getReference("posts")

        eventReference.addValueEventListener(getEventValueEventListener())
        postReference.addChildEventListener(getPostChildEventListener())
    }

    private fun getEventValueEventListener() : ValueEventListener {
        return object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                event = dataSnapshot.getValue(Event::class.java)!!
                notifyItemsArray()
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

                val index = getItemIndex(post, sortPosts)
                if(index != -1)
                    items[index] = post

                val indexForArray = getItemIndex(post, posts)
                if(indexForArray != -1)
                    posts[indexForArray] = post

                notifyItemsArray()
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                posts.add(dataSnapshot.getValue(Post::class.java)!!)

                count++
                if(count >= dataSnapshot.childrenCount) {
                    progressBar.visibility = View.GONE
                    sortPosts = posts
                }

                notifyItemsArray()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(Post::class.java)!!

                val index = getItemIndex(post, sortPosts)
                if(index != -1)
                    items.removeAt(index)

                val indexForArray = getItemIndex(post, posts)
                if(indexForArray != -1)
                    posts.removeAt(indexForArray)

                notifyItemsArray()
            }
        }
    }

    private fun notifyItemsArray() {
        items.clear()

        if(!event.title.equals("") && posts.size == sortPosts.size)
            items.add(0, event as ViewInPost)

        for (p in sortPosts) {
            items.add(p as ViewInPost)
        }

        postAdapter.notifyDataSetChanged()
    }

    private fun getItemIndex(post: Post, parray : MutableList<Post>): Int {
        var index = -1
        for ((id) in parray) {
            if (id == post.id) {
                index = id
                break
            }
        }
        return index
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                search = charSequence.toString()
                var filteredPosts = mutableListOf<Post>()
                if (search.isEmpty() || search == "") {
                    filteredPosts = posts
                } else {
                    for (post in posts) {
                        val type = getTypeString(post)
                        if (post.text.toLowerCase().contains(search) || post.title.toLowerCase().contains(search)
                            || post.date.toLowerCase().contains(search) || type.contains(search))
                            filteredPosts.add(post)
                    }
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = filteredPosts
                sortPosts = filteredPosts
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                notifyItemsArray()
            }
        }
    }

    fun getTypeString(post : Post) : String {
        when(post.way) {
            1 -> return "ai"
            2 -> return "desktop"
            3 -> return "gamedev"
            4 -> return "android"
            5 -> return "ios"
            6 -> return "frontend"
            7 -> return "backend"
            8 -> return "lowlevel"
            else -> return "free"
        }
    }
}