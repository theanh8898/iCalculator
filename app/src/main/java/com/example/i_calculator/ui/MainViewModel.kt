package com.example.i_calculator.ui

import com.example.i_calculator.base.BaseViewModel
import com.example.i_calculator.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var networkHelper: NetworkHelper
}