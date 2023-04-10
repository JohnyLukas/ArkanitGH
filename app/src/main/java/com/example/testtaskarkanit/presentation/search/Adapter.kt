package com.example.testtaskarkanit.presentation.search

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.databinding.ListItemRepoBinding
import com.example.testtaskarkanit.databinding.ListItemUserBinding
import com.example.testtaskarkanit.presentation.model.ItemRepoUI
import com.example.testtaskarkanit.presentation.model.ItemUserUI
import com.example.testtaskarkanit.presentation.model.ItemsUI

class Adapter : ListAdapter<ItemsUI, ViewHolder>(ItemsDiffUtils) {
    //private val itemList: MutableList<ItemsUI> = ArrayList()

    enum class VTYPE {
        USER, REPO
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemUserUI -> VTYPE.USER.ordinal
            is ItemRepoUI -> VTYPE.REPO.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        /*val binding = ListItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)*/
        return when (viewType) {
            VTYPE.USER.ordinal -> UserViewHolder(
                ListItemUserBinding.bind(
                    inflater.inflate(
                        R.layout.list_item_user, parent, false
                    )
                )
            )
            VTYPE.REPO.ordinal -> RepoViewHolder(
                ListItemRepoBinding.bind(
                    inflater.inflate(
                        R.layout.list_item_repo, parent, false
                    )
                )
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val items = itemList[position]
        holder.bind(items)*/
        when (holder) {
            is UserViewHolder -> holder.bind(model = (getItem(position) as ItemUserUI))
            is RepoViewHolder -> holder.bind(model = (getItem(position) as ItemRepoUI))
        }
    }

    inner class UserViewHolder(
        private val binding: ListItemUserBinding
    ) : ViewHolder(binding.root) {
        private val customTabsIntent = CustomTabsIntent.Builder().build()

        fun bind(model: ItemUserUI) = with(binding) {
            Glide.with(root)
                .load(model.avatarUrl)
                .centerCrop()
                .into(avatarView)

            userNameText.text = model.login
            scoreCountText.text = model.score.toString()
            root.setOnClickListener {
                customTabsIntent.launchUrl(
                    root.context,
                    Uri.parse(model.htmlUrl)
                )
            }
        }
    }

    inner class RepoViewHolder(
        private val binding: ListItemRepoBinding
    ) : ViewHolder(binding.root) {
        fun bind(model: ItemRepoUI) = with(binding) {
            nameRepo.text = model.name
            descriptionText.text = model.description
            forksCount.text = model.forksCount.toString()
            root.setOnClickListener {
                TODO()
            }
        }
    }
}

object ItemsDiffUtils : DiffUtil.ItemCallback<ItemsUI>() {

    override fun areItemsTheSame(oldItem: ItemsUI, newItem: ItemsUI) =
        oldItem.getItemId() == newItem.getItemId()

    override fun areContentsTheSame(oldItem: ItemsUI, newItem: ItemsUI) =
        oldItem == newItem
}