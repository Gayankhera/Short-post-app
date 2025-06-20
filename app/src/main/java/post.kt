package com.example.postapp

data class Post(
    val text: String = "",
    val id: String = "" // This one is Firestore document ID
                         // which is set to be deafult in firebase firestore database
)
