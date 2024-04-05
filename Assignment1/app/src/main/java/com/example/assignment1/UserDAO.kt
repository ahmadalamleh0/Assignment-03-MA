package com.example.assignment1

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.assignment1.model.UserData

class UserDAO(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDB"
        private const val TABLE_USERS = "users"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
    }

    // Method called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_USERS ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_NAME TEXT, $KEY_EMAIL TEXT)"
        db?.execSQL(createTableQuery)
        Log.d(TAG, "onCreate: Table created")
    }

    // Method called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
        Log.d(TAG, "onUpgrade: Database upgraded")
    }

    // Method to add a new user to the database
    fun addUser(user: UserData): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, user.userName)
        values.put(KEY_EMAIL, user.userMB)
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        Log.d(TAG, "addUser: User added with ID $id")
        return id
    }
    // Method to add a new user to the database
    fun getAllUsers(): ArrayList<UserData> {
        val usersList = ArrayList<UserData>()
        val selectQuery = "SELECT * FROM $TABLE_USERS"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            // If an exception occurs while executing the query, return an empty list
            Log.e(TAG, "getAllUsers: Error executing query", e)
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        val userNameIndex = cursor.getColumnIndex(KEY_NAME)
                        val userEmailIndex = cursor.getColumnIndex(KEY_EMAIL)

                        // Check if column indexes are valid
                        if (userNameIndex >= 0 && userEmailIndex >= 0) {
                            val userName = cursor.getString(userNameIndex)
                            val userEmail = cursor.getString(userEmailIndex)
                            val user = UserData(userName = userName, userMB = userEmail)
                            usersList.add(user)
                        } else {
                            // Log a warning if column indexes are invalid
                            Log.w("UserDAO", "Invalid column index")
                        }
                    } while (cursor.moveToNext())
                }
            } finally {
                cursor.close()
            }
        }
        db.close()
        Log.d(TAG, "getAllUsers: Retrieved ${usersList.size} users")
        return usersList
    }

    // Method to update an existing user in the database
    fun updateUser(user: UserData): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, user.userName)
        values.put(KEY_EMAIL, user.userMB)
        val rowsAffected = db.update(TABLE_USERS, values, "$KEY_ID = ?", arrayOf(user.id.toString()))
        db.close()
        Log.d(TAG, "updateUser: Updated $rowsAffected rows")
        return rowsAffected
    }

    // Method to delete a user from the database
    fun deleteUser(user: UserData): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_USERS, "$KEY_ID = ?", arrayOf(user.id.toString()))
        db.close()
        Log.d(TAG, "deleteUser: Deleted $rowsAffected rows")
        return rowsAffected
    }
}