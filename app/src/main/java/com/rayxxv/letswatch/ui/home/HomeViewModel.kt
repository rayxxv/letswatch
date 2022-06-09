package com.rayxxv.letswatch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxxv.letswatch.data.Repository
import com.rayxxv.letswatch.data.Resource
import com.rayxxv.letswatch.data.local.User
import com.rayxxv.letswatch.data.pojo.PopularMoviesResponse
import com.rayxxv.letswatch.data.pojo.Tv
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {
    private val _movies = MutableLiveData<Resource<PopularMoviesResponse>>()
    val movies: LiveData<Resource<PopularMoviesResponse>>
    get() = _movies
    private val _series = MutableLiveData<Resource<Tv>>()
    val series: LiveData<Resource<Tv>>
    get() = _series
    private val  _getDataUser : MutableLiveData<User> = MutableLiveData()
    val getDataUser : LiveData<User> get() = _getDataUser

    fun getAllPopularMovies(){
        viewModelScope.launch {
            _movies.postValue(Resource.loading())
            try {
                _movies.postValue(Resource.success(repository.getPopularMovies()))
            }catch (exception: Exception){
                _movies.postValue(Resource.error(exception.message ?: "Error Occurred"))
            }
        }
    }

    fun getAllPopularSeries(){
        viewModelScope.launch {
            _series.postValue(Resource.loading())
            try {
                _series.postValue(Resource.success(repository.getAllSeries()))
            }catch (exception: Exception){
                _series.postValue(Resource.error(exception.message ?: "Error Occurred"))
            }
        }
    }
    fun getUser(username: String) {
        viewModelScope.launch {
            _getDataUser.value = repository.getUserr(username)
        }
    }
}