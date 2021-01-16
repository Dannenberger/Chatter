package com.example.chatter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatter.database.ContactDatabase
import com.example.chatter.databinding.FragmentContactListBinding

/**
 * A fragment representing a list of Items.
 */
class ContactListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentContactListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_list, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactsDao
        val viewModelFactory = ContactListViewModelFactory(dataSource, application)
        val contactListViewModel = ViewModelProvider(this, viewModelFactory).get(ContactListViewModel::class.java)

        val adapter = ContactListAdapter()
        binding.contactList.adapter = adapter
//        contactListViewModel.contacts.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.data = it
//            }
//        })
        binding.lifecycleOwner = this
        return binding.root
    }

}