package com.kiluss.sonretexkotlin.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kiluss.data.model.Movie
import com.kiluss.sonretexkotlin.databinding.ItemMoviePreviewBinding
import com.kiluss.sonretexkotlin.utils.MovieDiff
import com.kiluss.sonretexkotlin.utils.Utils

class MovieAdapter(
    private var movies: MutableList<Movie>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onOpen(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMoviePreviewBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(private val binding: ItemMoviePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            itemView.setOnClickListener {
                listener.onOpen(movies[adapterPosition])
            }
            with(item) {
                with(binding) {
                    tvTitle.text = title
                    tvAuthor.text = author
                    Utils.getDate(datetime)?.let {
                        tvDatetime.text = it
                    }
                }
            }
        }
    }

    internal fun updateData(items: MutableList<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiff(items, this.movies))
        diffResult.dispatchUpdatesTo(this)
        this.movies.clear()
        this.movies.addAll(items)
    }
}
