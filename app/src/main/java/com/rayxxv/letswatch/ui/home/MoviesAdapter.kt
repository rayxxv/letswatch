package com.rayxxv.letswatch.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rayxxv.letswatch.data.pojo.Movie
import com.rayxxv.letswatch.databinding.ItemMovieBinding


class MoviesAdapter (private val onItemClick: OnClickListener): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean = oldItem.hashCode() == newItem.hashCode()

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Movie>?) = differ.submitList(value)

    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                Glide.with(binding.root).load(IMAGE_BASE + data.posterPath).fitCenter().into(ivMovie)
                tvMovieTitle.text = data.originalTitle
                tvMovieRating.text = data.voteAverage.toString()
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: Movie)
    }

}