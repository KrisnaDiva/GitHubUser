package com.krisna.diva.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.diva.githubuser.data.remote.response.UsersItem
import com.krisna.diva.githubuser.databinding.ItemUserBinding
import com.krisna.diva.githubuser.ui.detail.DetailActivity

class UserAdapter : ListAdapter<UsersItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersItem) {
            binding.tvName.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                val moveWithDataIntent = Intent(binding.root.context, DetailActivity::class.java)
                moveWithDataIntent.putExtra(DetailActivity.USERNAME, user.login)
                binding.root.context.startActivity(moveWithDataIntent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersItem>() {
            override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}