package com.kiluss.sonretexkotlin.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kiluss.sonretexkotlin.R
import com.kiluss.sonretexkotlin.constant.DATA
import com.kiluss.sonretexkotlin.constant.EXIT_TIME_DELAY_BACK_PRESS
import com.kiluss.sonretexkotlin.constant.MOVIE_API_URL
import com.kiluss.sonretexkotlin.extention.toList
import com.kiluss.sonretexkotlin.network.api.ApiService
import com.kiluss.sonretexkotlin.network.api.RetrofitClient
import com.kiluss.sonretexkotlin.ui.base.BaseViewModel
import com.kiluss.sonretexkotlin.data.model.Movie
import com.kiluss.sonretexkotlin.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : BaseViewModel(application) {
    private var isDoubleBack = false
    private lateinit var movieService: ApiService
    private var allMovies = mutableListOf<Movie>()

    private val _exitApp: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    internal val exitApp: LiveData<Boolean> = _exitApp
    private val _showToast: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    internal val showToast: LiveData<String> = _showToast
    private val _progressBarStatus: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    internal val progressBarStatus: LiveData<Boolean> = _progressBarStatus
    private val _movieList: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    internal val movieList: LiveData<MutableList<Movie>> = _movieList

    init {
        initApiService(getApplication())
        getMovies(getApplication())
    }

    private fun initApiService(context: Context) {
        movieService =
            RetrofitClient.getInstance(context).getClientUnAuthorize(MOVIE_API_URL).create(ApiService::class.java)
    }

    private fun getMovies(context: Context) {
        movieService.getMovie().enqueue(object : Callback<JsonObject?> {
            override fun onResponse(
                call: Call<JsonObject?>, response: Response<JsonObject?>
            ) {
                when {
                    response.isSuccessful -> {
                        response.body()?.let {
                            viewModelScope.launch(Dispatchers.Default) {
                                val jsonArray = JSONObject(response.body().toString()).getJSONArray(DATA)
                                jsonArray.toList().forEach {
                                    allMovies.add(Gson().fromJson(it.toString(), Movie::class.java))
                                }
                                _movieList.postValue(allMovies)
                                _progressBarStatus.postValue(false)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                t.message?.let { Utils.showLongToast(context, it) }
                _progressBarStatus.value = false
                t.printStackTrace()
            }
        })
    }

    internal fun onBackPressToExit() {
        if (isDoubleBack) {
            _exitApp.value = isDoubleBack
        } else {
            _showToast.value = getApplication<Application>().getString(R.string.double_back_to_exit)
            isDoubleBack = true
            viewModelScope.launch {
                delay(EXIT_TIME_DELAY_BACK_PRESS)
                isDoubleBack = false
            }
        }
    }
}
