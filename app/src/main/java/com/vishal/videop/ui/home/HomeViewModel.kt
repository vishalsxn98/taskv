package com.vishal.videop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.videop.commons.utils.ResultWrapper
import com.vishal.videop.ui.home.data.repository.HomeRepository
import com.vishal.videop.ui.home.domain.model.VideoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val videoListMutableLiveData = MutableLiveData<ResultWrapper<MutableList<VideoModel>>>()

    fun getVideoList() {
        viewModelScope.launch(Dispatchers.IO) {
            videoListMutableLiveData.postValue(ResultWrapper.Loading("loading"))
            try {
                val data = repository.getVideoList()
                videoListMutableLiveData.postValue(ResultWrapper.Success(data))

            } catch (e: Exception) {
                videoListMutableLiveData.postValue(
                    ResultWrapper.Failed(
                        e.localizedMessage ?: "error getting list!"
                    )
                )
                e.printStackTrace()
            }
        }
    }


    val getVideoListLiveData
        get() = videoListMutableLiveData as LiveData<ResultWrapper<MutableList<VideoModel>>>
}
