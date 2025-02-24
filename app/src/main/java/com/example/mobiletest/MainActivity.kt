package com.example.mobiletest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletest.databinding.ActivityMainBinding
import com.example.mobiletest.ui.BookingListAdapter
import com.example.mobiletest.viewmodel.BookingViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@SuppressLint("RestrictedApi")
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<BookingViewModel>()

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val adapter = BookingListAdapter()
        viewBinding.rv.layoutManager = LinearLayoutManager(this)
        viewBinding.rv.adapter = adapter
        viewModel.bookingListLD.observe(this) {
            viewBinding.rv.visibility = View.VISIBLE
            viewBinding.errorText.visibility = View.GONE
            adapter.submitList(it.segments)
            val gson = GsonBuilder().setPrettyPrinting().create()
            Log.d("BOOKING_DATA", gson.toJson(it))
        }
        viewModel.errorLD.observe(this) {
            viewBinding.rv.visibility = View.GONE
            viewBinding.errorText.visibility = View.VISIBLE
        }
        viewBinding.refresh.setOnClickListener {
            viewModel.getBookingList()
        }
        viewBinding.error.setOnClickListener {
            viewModel.getError()
        }
        viewModel.getBookingList()
    }
}