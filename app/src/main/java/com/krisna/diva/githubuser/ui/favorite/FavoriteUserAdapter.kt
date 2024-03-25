package com.krisna.diva.githubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity
import com.krisna.diva.githubuser.databinding.ItemFavoriteUserBinding
import com.krisna.diva.githubuser.ui.detail.DetailActivity
import com.krisna.diva.githubuser.utils.DateFormatter

class FavoriteUserAdapter(private val favoriteViewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val listFavoriteUsers = ArrayList<FavoriteUserEntity>()

    fun setListFavoriteUsers(listFavoriteUsers: List<FavoriteUserEntity>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUsers, listFavoriteUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavoriteUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUsers[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteUsers.size
    }

    inner class FavoriteUserViewHolder(private val binding: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUserEntity) {
            binding.tvUserName.text = favoriteUser.username
            binding.tvCreatedAt.text = DateFormatter.getRelativeTime(favoriteUser.createdAt)
            Glide.with(binding.root)
                .load(favoriteUser.avatarUrl)
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                val moveWithDataIntent = Intent(binding.root.context, DetailActivity::class.java)
                moveWithDataIntent.putExtra(DetailActivity.USERNAME, favoriteUser.username)
                binding.root.context.startActivity(moveWithDataIntent)
            }
            binding.favoriteButton.setOnClickListener {
                favoriteViewModel.delete(favoriteUser)
            }

        }
    }
}
