package com.example.postapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var postList: MutableList<Post>
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.postRecyclerView)
        val postEditText = findViewById<EditText>(R.id.postEditText)
        val addPostButton = findViewById<Button>(R.id.addPostButton)

        postList = mutableListOf()


        adapter = PostAdapter(postList) { post ->
            deletePost(post)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addPostButton.setOnClickListener {
            val text = postEditText.text.toString()
            if (text.isNotEmpty()) {
                val post = hashMapOf("text" to text)
                db.collection("posts").add(post)
                    .addOnSuccessListener {
                        postEditText.text.clear()
                        fetchPosts()
                    }
            }
        }

        fetchPosts()
    }

    private fun fetchPosts() {
        db.collection("posts").get().addOnSuccessListener { result ->
            postList.clear()
            for (document in result) {
                val post = Post(
                    text = document.getString("text") ?: "",
                    id = document.id
                )
                postList.add(post)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun deletePost(post: Post) {
        db.collection("posts").document(post.id).delete()
            .addOnSuccessListener {
                fetchPosts()
            }
    }
}
