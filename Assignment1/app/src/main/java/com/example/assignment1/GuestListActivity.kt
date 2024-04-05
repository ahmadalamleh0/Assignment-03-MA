package com.example.assignment1


import UserAdapter
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.model.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GuestListActivity : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton // Floating action button for adding guest information
    private lateinit var recv: RecyclerView // RecyclerView for displaying the list of guests
    private lateinit var userList: ArrayList<UserData> // List to store guest information
    private lateinit var userAdapter: UserAdapter // Adapter for populating RecyclerView
    private lateinit var userDAO: UserDAO // Data Access Object for database operations
    private var eventName: String = "" // Name of the event
    private var eventDate: String = "" // Date of the event
    private var eventTime: String = "" // Time of the event
    private var eventLocation: String = "" // Location of the event


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_list)
        Log.d(TAG, "onCreate: Guest List Activity created")

        userDAO = UserDAO(this) // Initialize the UserDAO for database operations
        userList = userDAO.getAllUsers() // Load existing guest information from the database

        // Retrieve event information from intent extras
        intent.extras?.let { extras ->
            eventName = extras.getString("eventName", "")
            eventDate = extras.getString("eventDate", "")
            eventTime = extras.getString("eventTime", "")
            eventLocation = extras.getString("eventLocation", "")
        }

        // Find views by their IDs
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)

        // Set up RecyclerView
        userAdapter = UserAdapter(this, userList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter

        // Set up OnClickListener for the floating action button to add guest information
        addsBtn.setOnClickListener { addInfo() }

        // Setting up the toolbar
        var toolbar = findViewById<Toolbar>(R.id.Toolbar)
        setSupportActionBar(toolbar)
        // Enable the back button in the ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Method called when the activity resumes to update the guest list
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Guest List Activity resumed")
        // Clear the existing list and reload guest information from the database
        userList.clear()
        userList.addAll(userDAO.getAllUsers())
        // Notify the RecyclerView adapter that the data set has changed
        userAdapter.notifyDataSetChanged()
    }

    // Method to handle options menu items clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            android.R.id.home -> {
                // This is the ID for the back button
                onBackPressed() // Call onBackPressed() method when back button is pressed
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Method to add guest information
    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item, null)
        ///set view
        val userName = v.findViewById<EditText>(R.id.userName)
        val useremail = v.findViewById<EditText>(R.id.userEmail)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val names = userName.text.toString()
            val email = useremail.text.toString()
            // Create a new UserData object with the entered information
            val newUser = UserData(userName = "Name: $names", userMB = "Email: $email")
            // Add the new user to the database and update the list
            val id = userDAO.addUser(newUser)
            newUser.id = id.toInt().toLong()
            userList.add(newUser)
            userAdapter.notifyDataSetChanged()
            // Display a toast message indicating success
            Toast.makeText(this, "Adding User Information Success", Toast.LENGTH_SHORT).show()

            // Send invitation email to the newly added guest
            sendInvitationEmail(names, email)
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }
    // Method to send an invitation email to the guest
    private fun sendInvitationEmail(guestName: String, guestEmail: String) {
        // Create the email subject and body
        val subject = "Invitation to $eventName"
        val body = """
    Dear $guestName,

    You are invited to the event:
    Event Name: $eventName
    Date: $eventDate
    Time: $eventTime
    Location: $eventLocation

    We hope you can join us!
""".trimIndent()

        // Create an Intent to send the email
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(guestEmail))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        // Start the activity to send the email
        try {
            startActivity(Intent.createChooser(emailIntent, "Send Invitation Email"))
        } catch (e: ActivityNotFoundException) {
            // Display a toast message if no email clients are installed on the device
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}


