package com.example.assignment1.model

// Data class representing user data
data class UserData(
    var id: Long = -1,     // Unique identifier for the user (default value: -1)
    var userName: String,  // User's name
    var userMB: String     // User's email or mobile number
)
