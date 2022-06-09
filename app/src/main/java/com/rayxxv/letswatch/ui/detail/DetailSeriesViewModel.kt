package com.rayxxv.letswatch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxxv.letswatch.data.Repository
import com.rayxxv.letswatch.data.Resource
import com.rayxxv.letswatch.data.local.Favorite
import com.rayxxv.letswatch.data.pojo.ResultX
import kotlinx.coroutines.launch

class DetailSeriesViewModel(private val repository: Repository) : ViewModel() {
    private val _detailSeries = MutableLiveData<Resource<ResultX>>()
    private val _dataFav = MutableLiveData<Favorite>()
    val detailSeries: LiveData<Resource<ResultX>>get() = _detailSeries
    val dataFav: LiveData<Favorite>get() = _dataFav

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


    fun getAllDetailSeries(seriesid: Int){
        viewModelScope.launch {
            _detailSeries.postValue(Resource.loading())
            try {
                _detailSeries.postValue(Resource.success(repository.getSeriesDetail(seriesid)))
            }catch (exception: Exception){
                _detailSeries.postValue(Resource.error(exception.message ?: "Error Occured"))
            }
        }
    }
}