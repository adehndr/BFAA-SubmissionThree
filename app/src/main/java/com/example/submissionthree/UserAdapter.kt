package com.example.submissionthree

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionthree.databinding.ListRowUserBinding
import com.example.submissionthree.dataclass.UserItems

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    var mData = ArrayList<UserItems>()

    fun setData(items: ArrayList<UserItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        return holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListRowUserBinding.bind(itemView)
        fun bind(userItems: UserItems) {
            Glide.with(itemView.context)
                .load(userItems.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivAvatar)
            binding.tvUserName.text = userItems.userName
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(userItems)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItems)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}