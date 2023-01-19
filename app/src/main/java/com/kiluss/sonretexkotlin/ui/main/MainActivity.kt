package com.kiluss.sonretexkotlin.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiluss.sonretexkotlin.data.model.Movie
import com.kiluss.sonretexkotlin.constant.EXTRA_MOVIE_DETAIL
import com.kiluss.sonretexkotlin.databinding.ActivityMainBinding
import com.kiluss.sonretexkotlin.ui.detail.DetailActivity
import com.kiluss.sonretexkotlin.utils.Utils

class MainActivity : AppCompatActivity(), MovieAdapter.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var movieAdapter: MovieAdapter? = null

    // view model ktx
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerViewListView()
        observeViewModel()
    }

    private fun setUpRecyclerViewListView() {
        movieAdapter = MovieAdapter(mutableListOf(), this)
        with(binding.rvMovie) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            // trigger when user double back to finish
            exitApp.observe(this@MainActivity) {
                finish()
            }
            showToast.observe(this@MainActivity) {
                Utils.showShortToast(this@MainActivity, it)
            }
            progressBarStatus.observe(this@MainActivity) {
                if (it) {
                    showProgressbar()
                } else {
                    hideProgressbar()
                }
            }
            movieList.observe(this@MainActivity) {
                movieAdapter?.updateData(it)
            }
        }
    }

    override fun onOpen(movie: Movie) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_DETAIL, movie)
        })
    }

    private fun showProgressbar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.pbLoading.visibility = View.GONE
    }

    override fun onBackPressed() {
        viewModel.onBackPressToExit()
    }
}
