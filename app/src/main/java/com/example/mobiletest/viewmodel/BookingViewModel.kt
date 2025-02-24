package com.example.mobiletest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest.common.NonStickyMutableLiveData
import com.example.mobiletest.entity.BookingEntity
import com.example.mobiletest.entity.BookingInfoEntity
import com.example.mobiletest.model.BookingDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class BookingViewModel(
    private val bookingDataRP: BookingDataRepository = BookingDataRepository()
) : ViewModel() {

    private val _bookingListLD = MutableLiveData<BookingInfoEntity>()

    private val _errorLD = NonStickyMutableLiveData<Throwable>()

    val bookingListLD: LiveData<BookingInfoEntity> = _bookingListLD

    val errorLD: LiveData<Throwable> = _errorLD

    fun getBookingList() = viewModelScope.launch {
        bookingDataRP.getBookingList()
            .flowOn(Dispatchers.IO)
            .catch { _errorLD.postValue(it) }
            .collectLatest {
                _bookingListLD.value = it
            }
    }

    fun getError() = viewModelScope.launch {
        bookingDataRP.getError()
            .flowOn(Dispatchers.IO)
            .catch {
                _errorLD.postValue(it)
            }
            .collectLatest {  }
    }
}