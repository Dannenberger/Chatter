package com.example.chatter

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.chatter.contactlist.ContactListViewModel
import com.example.chatter.contactlist.ContactListViewModelFactory
import com.example.chatter.database.ContactDatabase
import com.example.chatter.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        binding.buttonManage.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_contactListFragment)
        )
        binding.buttonImport.setOnClickListener {
            val application = requireNotNull(this.activity).application
            val dataSource = ContactDatabase.getInstance(application).contactsDao
            val viewModelFactory = ContactListViewModelFactory(dataSource, application)
            val contactListViewModel = ViewModelProvider(this, viewModelFactory).get(
                ContactListViewModel::class.java)
            contactListViewModel.onStartImport()
            Toast.makeText(context,"Contacts imported :)", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    /**
     * Handles the creation of the action bar
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    /**
     * Handles actions taken when action bar items are selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

//    private fun importContacts() {
//        val context: Context? = context
//        val db = context?.let { ContactDatabase.getInstance(it) }
//        val contentResolver = activity?.contentResolver
//        val cursor: Cursor? = contentResolver?.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        )
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)) > 0) {
//                    val phoneNumber: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
////                    if (!checkForValidNumber(phoneNumber)) continue
//                    val id: Long = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
//                    val firstName: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                    val lastName: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                    val contact = Contact(
//                        id = id,
//                        firstName = firstName,
//                        lastName = lastName,
//                        phoneNumber = "1234567890",
//                    )
//                    db?.contactsDao?.insertContact(contact)
//                }
//            }
//        }
//    }
//
//    /**
//     * Checks to make sure a number received should be imported to the list of contacts in the app.
//     */
//    private fun checkForValidNumber(phoneNumber: String): Boolean {
//        if (phoneNumber.length < 10 || phoneNumber.startsWith("1800")) {
//            return false
//        }
//        return true
//    }

}