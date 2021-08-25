package com.spsoft.rawgames.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spsoft.rawgames.R
import com.spsoft.rawgames.databinding.ItemGameListBinding
import com.spsoft.rawgames.databinding.ItemLoadingBinding
import com.spsoft.rawgames.data.models.game.Result


class GameListAdapter : ListAdapter<Result, RecyclerView.ViewHolder>(DIFF) {
    private lateinit var binding: ItemGameListBinding
    private lateinit var bindingLoading: ItemLoadingBinding

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            binding =
                ItemGameListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderGame(binding)
        } else {
            bindingLoading =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolderGame(bindingLoading)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderGame) {
            populateItemRows(holder, position)
        } else if (holder is LoadingViewHolderGame) {
            showLoadingView(holder, position)
        }
    }


    private fun showLoadingView(viewHolder: LoadingViewHolderGame, position: Int) {
        //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ViewHolderGame, position: Int) {
        viewHolder.bind(position)
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }


    inner class ViewHolderGame(private val binding: ItemGameListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val currentGame = getItem(position)
            binding.textViewName.text = currentGame.name
            binding.textView.text = currentGame.rating.toString()

            /*binding.imageView.load(currentGame.background_image) {
                crossfade(true)
                placeholder(android.R.drawable.alert_dark_frame)
            }*/


            Glide.with(binding.root).load(currentGame.background_image)
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(binding.imageView)

        }
    }

    inner class LoadingViewHolderGame(private val bindingLoadingBinding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(bindingLoadingBinding.root) {
        fun bind(position: Int) {

        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }


}
