package com.krisna.diva.githubuser.ui.favorite

import androidx.recyclerview.widget.DiffUtil
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity

class FavoriteUserDiffCallback(
    private val oldFavoriteUserList: List<FavoriteUserEntity>,
    private val newFavoriteUserList: List<FavoriteUserEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldFavoriteUserList.size

    override fun getNewListSize(): Int = newFavoriteUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteUserList[oldItemPosition].username == newFavoriteUserList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = oldFavoriteUserList[oldItemPosition]
        val newFavoriteUser = newFavoriteUserList[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}