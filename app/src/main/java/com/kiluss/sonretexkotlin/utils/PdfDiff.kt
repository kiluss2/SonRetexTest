package com.kiluss.sonretexkotlin.utils

import androidx.recyclerview.widget.DiffUtil
import com.kiluss.data.model.Movie

class MovieDiff(
    private var newList: MutableList<Movie>, private var oldList: MutableList<Movie>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].title == newList[newItemPosition].title)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
