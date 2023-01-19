package com.kiluss.sonretexkotlin.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiluss.sonretexkotlin.data.model.Movie
import com.kiluss.sonretexkotlin.constant.EXTRA_MOVIE_DETAIL
import com.kiluss.sonretexkotlin.databinding.ActivityDetailBinding
import com.kiluss.sonretexkotlin.utils.Utils

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        getMovieData()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getMovieData() {
        intent?.getParcelableExtra<Movie>(EXTRA_MOVIE_DETAIL)?.let { movie ->
            with(binding) {
                tvTitle.text = movie.title
                tvAuthor.text = movie.author
                Utils.getDate(movie.datetime)?.let {
                    tvDatetime.text = it
                }
                tvDescription.text = movie.description
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}