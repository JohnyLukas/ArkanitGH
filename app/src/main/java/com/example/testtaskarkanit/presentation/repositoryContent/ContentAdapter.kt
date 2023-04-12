package com.example.testtaskarkanit.presentation.repositoryContent

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.databinding.ListItemDirBinding
import com.example.testtaskarkanit.presentation.model.repoContent.RepoContentItemType
import com.example.testtaskarkanit.presentation.model.repoContent.RepoContentItemUI

class ContentAdapter(
    private val onFileClicked: (RepoContentItemUI) -> Unit
) : ListAdapter<RepoContentItemUI, ContentAdapter.ContentViewHolder>(ContentDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContentViewHolder(
            ListItemDirBinding.bind(
                inflater.inflate(R.layout.list_item_dir, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentViewHolder(
        private val binding: ListItemDirBinding
    ) : ViewHolder(binding.root) {
        private val customTabsIntent = CustomTabsIntent.Builder().build()

        fun bind(item: RepoContentItemUI) = with(binding) {
            if (item.type == RepoContentItemType.DIRECTORY) {
                fileImage.setImageResource(R.drawable.baseline_folder_24)
            } else {
                fileImage.setImageResource(R.drawable.baseline_insert_drive_file_24)
            }
            fileName.text = item.name
            root.setOnClickListener {
                if (item.type == RepoContentItemType.FILE) customTabsIntent.launchUrl(
                    root.context, Uri.parse(item.htmlUrl)
                ) else this@ContentAdapter.onFileClicked(item)
            }
        }
    }
}

object ContentDiffUtils : DiffUtil.ItemCallback<RepoContentItemUI>() {
    override fun areItemsTheSame(oldItem: RepoContentItemUI, newItem: RepoContentItemUI) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: RepoContentItemUI, newItem: RepoContentItemUI) =
        oldItem == newItem
}