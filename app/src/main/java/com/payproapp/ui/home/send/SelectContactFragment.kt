package com.payproapp.ui.home.send


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.databinding.FragmentSelectContactBinding
import com.payproapp.model.Contact
import com.payproapp.ui.base.BaseFragment
import com.payproapp.R
import com.payproapp.ui.home.HomeViewModel
import com.payproapp.ui.home.send.adapters.ContactsRecyclerAdapter
import com.payproapp.ui.home.send.interfaces.OnContactClickListener
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


/**
 * A simple [BaseFragment] subclass.
 * Use the [SelectContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SelectContactFragment : BaseFragment(),
        EasyPermissions.PermissionCallbacks,
        OnContactClickListener {

    private lateinit var binding: FragmentSelectContactBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var contactsAdapter: ContactsRecyclerAdapter

    //Permission
    private val rcContacts = 1

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment SelectContactFragment.
         */
        @JvmStatic
        fun newInstance() = SelectContactFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_select_contact, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            activity.title = getString(R.string.title_select_contact)
            configureContactList()

            viewModel.contactList.observe(this, Observer { contactList ->
                if (contactList.size > 0) {
                    viewModel.retrieveContacts(getPhoneFromContact(contactList))
                }
            })

            viewModel.finalContactList.observe(this, Observer { contactList ->
                if (contactList.size > 0) {
                    contactsAdapter.replaceData(contactList)
                }
            })

            if (EasyPermissions.hasPermissions(activity, Manifest.permission.READ_CONTACTS)) {
                viewModel.retrievePhoneContacts(activity.contentResolver)
            } else {
                requestReadContactsPermission()
            }
        }
    }

    private fun getPhoneFromContact(contacts: MutableList<Contact>): MutableList<String> {
        val phones = mutableListOf<String>()

        for (contact in contacts) {
            contact.phone.let {
                phones.add(formatPhone(it))
            }
        }

        return phones
    }

    private fun formatPhone(phone: String): String {
        var formatedPhone = ""

        if (!phone.contains("+") && !phone.contains("00")) {
            formatedPhone = "+34" + phone.replace("[^\\d.]", "")
        } else if (phone.contains("00")) {
            formatedPhone = "+34" + phone.replace("00", "").replace("[^\\d.]", "")
        } else {
            formatedPhone = phone.replace("[^\\d.]", "")
        }

        return formatedPhone.trim().replace(" ", "").replace("  ", "")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        showError(R.string.error_contacts_permission, binding.root)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        activity?.let {
            viewModel.retrievePhoneContacts(it.contentResolver)
        }
    }

    override fun onContactClicked(contact: Contact) {
        viewModel.sendBody.name = contact.name
        viewModel.sendBody.phone = contact.phone
        if (contact.phone.length > 9) {

            viewModel.sendBody.prefix = contact.phone.substring(0, 2)
        }

        viewModel.sendBody.destinationKey = contact.account


        viewModel.sendAmountState()
    }

    override fun onEmptyContactClicked() {
        viewModel.sendManuallyState()
    }

    private fun requestReadContactsPermission() {
        val permissionRequest = PermissionRequest.Builder(
                this,
                rcContacts,
                Manifest.permission.READ_CONTACTS).build()

        EasyPermissions.requestPermissions(permissionRequest)
    }

    private fun configureContactList() {
        contactsAdapter = ContactsRecyclerAdapter(this)
        binding.recycleContacts.adapter = contactsAdapter
    }
}
