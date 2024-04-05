
import android.app.AlertDialog
import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.R
import com.example.assignment1.model.UserData





class UserAdapter (val c:Context,val userList:ArrayList<UserData>) :RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    // Inner class representing the ViewHolder for each user item
    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        var Email: TextView
        var mMenus: ImageView

        // Initialize views in the ViewHolder
        init {
            name = v.findViewById<TextView>(R.id.mTitle)
            Email = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
        }
        // Bind data to the views
        fun bind(position: Int) {
            val currentUser = userList[position]
            name.text = currentUser.userName
            Email.text = currentUser.userMB

            // Set click listener for popup menus
            mMenus.setOnClickListener { popupMenus(it, position) }
        }

        // Function to handle popup menu actions
        private fun popupMenus(v: View, position: Int) {
            // Log the position of the clicked item
            Log.d("UserAdapter", "popupMenus called for position $position")
            val currentUser = userList[position]
            val popupMenus = PopupMenu(c, v)
            popupMenus.inflate(R.menu.show_menu)

            // Set click listener for popup menu items
                popupMenus.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.editText -> {
                            // Show dialog for editing user information
                            val v = LayoutInflater.from(c).inflate(R.layout.add_item, null)
                            val name = v.findViewById<EditText>(R.id.userName)
                            val Email = v.findViewById<EditText>(R.id.userEmail)
                            AlertDialog.Builder(c)
                                .setView(v)
                                .setPositiveButton("Ok") { dialog, _ ->
                                    // Update user information
                                    currentUser.userName = name.text.toString()
                                    currentUser.userMB = Email.text.toString()
                                    notifyDataSetChanged()
                                    Log.d("UserAdapter", "DataSetChanged called")
                                    Toast.makeText(c, "User Information is Edited", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                                .setNegativeButton("Cancel") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()

                            true
                        }
                    // Show dialog for confirming deletion
                    R.id.delete -> {
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes") { dialog, _ ->
                                // Remove user from the lis
                                userList.removeAt(position)
                                notifyDataSetChanged()
                                Toast.makeText(c, "Deleted this Information", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()


                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }

                    else -> true
                }

            }
            popupMenus.show()
            // Show icons in popup menu
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }
    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return UserViewHolder(v)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Log.d("UserAdapter", "onBindViewHolder called for position $position")
        holder.bind(position)
    }

    // Return the size of the user list
    override fun getItemCount(): Int {
        return userList.size
    }
}
