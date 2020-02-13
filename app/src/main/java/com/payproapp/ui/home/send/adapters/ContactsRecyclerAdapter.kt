package com.payproapp.ui.home.send.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.payproapp.R
import com.payproapp.databinding.ItemContactBinding
import com.payproapp.databinding.ItemEmptyContactBinding
import com.payproapp.model.Contact
import com.payproapp.ui.home.send.interfaces.OnContactClickListener

class ContactsRecyclerAdapter(
        private val contactClickListener: OnContactClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var contactList: List<Contact> = ArrayList()

    fun replaceData(contactList: List<Contact>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CONTACT) {
            val bindingContact = DataBindingUtil.inflate<ItemContactBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_contact,
                    parent,
                    false)

            return ContactViewHolder(bindingContact)

        } else {
            val bindingEmptyContact = DataBindingUtil.inflate<ItemEmptyContactBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_empty_contact,
                    parent,
                    false)

            return EmptyContactViewHolder(bindingEmptyContact)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContactViewHolder) {
            val contactItem = contactList[position - 1]
            (holder).bind(contactItem, contactClickListener)
        } else if (holder is EmptyContactViewHolder) {
            holder.bind(contactClickListener)
        }
    }

    override fun getItemCount(): Int {
        return if (contactList.isNotEmpty()) {
            contactList.size + 1 // contacts +1 added manually
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_EMPTY_CONTACT
        } else {
            VIEW_TYPE_CONTACT
        }
    }

    companion object {

        private const val VIEW_TYPE_EMPTY_CONTACT = 0
        private const val VIEW_TYPE_CONTACT = 1

        class ContactViewHolder(private var binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(contact: Contact, clickListener: OnContactClickListener) {
                binding.listener = clickListener
                binding.contact = contact
            }
        }

        class EmptyContactViewHolder(private var binding: ItemEmptyContactBinding) : RecyclerView.ViewHolder(
                binding.root) {
            fun bind(clickListener: OnContactClickListener) {
                binding.listener = clickListener
            }
        }
    }
}