package com.rayxxv.letswatch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxxv.letswatch.data.Repository
import com.rayxxv.letswatch.data.Resource
import com.rayxxv.letswatch.data.local.Favorite
import com.rayxxv.letswatch.data.pojo.Movie
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: Repository) : ViewModel() {
    private val _detailMovie = MutableLiveData<Resource<Movie>>()
    private val _dataFav = MutableLiveData<Favorite>()
    val detailMovie: LiveData<Resource<Movie>>get() = _detailMovie
    val dataFav: LiveData<Favorite>get() = _dataFav
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun insertFavorite(favEntity: Favorite){
        viewModelScope.launch {
            repository.insert(favEntity)
        }
    }

    fun deleteFav(entity: Favorite){
        viewModelScope.launch {
            repository.delete(entity)
        }
    }
    fun checkFavorite(movieid: Int){
        viewModelScope.launch {
            _dataFav.postValue(repository.getFavorite(movieid))
        }
    }


    fun getAllDetailMovies(movieid: Int){
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getMovieDetail(movieid)))
            }catch (exception: Exception){
                _detailMovie.postValue(Resource.error(exception.message ?: "Error Occured"))
            }
        }
    }
}


