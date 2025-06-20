package com.example.postapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// This class connects your list of posts to the RecyclerView
class PostAdapter(
    private val postList: List<Post>,              // List of posts
    private val onDeleteClick: (Post) -> Unit      // What to do when delete is clicked
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    // This class holds the views for each item in the list
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postText: TextView = view.findViewById(R.id.postText)       // Text of post
        val deleteButton: Button = view.findViewById(R.id.deleteButton) // Delete button
    }

    // function creates a new row for the list using post_item.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)  // Inflate XML layout
        return PostViewHolder(view)
    }

    // This function sets the content of each row
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]              // Get current post
        holder.postText.text = post.text           // Show text in TextView

        holder.deleteButton.setOnClickListener {
            onDeleteClick(post)                    // Call delete when button is clicked
        }
    }

    // How many items in total to show
    override fun getItemCount(): Int {
        return postList.size
    }
}
