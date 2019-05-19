package com.example.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.my_friends_item.*


class MyFriendAdapter(private val context: Context,
                      private val item: List<FriendList>,private  val listener:(FriendList)->Unit) :
        RecyclerView.Adapter<MyFriendAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(context, LayoutInflater.from(context) .
            inflate(R.layout.my_friends_item, parent, false))

    override fun onBindViewHolder(holder:MyFriendAdapter.ViewHolder, position: Int) {
        holder.bindItem(item.get(position), listener)
    }

    override fun getItemCount(): Int = item.size

    class ViewHolder(val context: Context,
                     override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        fun bindItem(item: FriendList, Listener: (FriendList)->Unit){
            txtFriendName.text = item.nama
            txtFriendEmail.text = item.email
            txtFriendTelp.text = item.telp
            Glide.with(context).load(item.image).into(imgProfile)
           //Glide.with(context).load(item,image).into(imgProfile)
            itemView.setOnClickListener { Listener(item) }
        }
    }


    interface MyFriendClickListener {
        fun onLongClick(friend: FriendList, position: Int)
    }
}
