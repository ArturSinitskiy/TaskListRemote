package com.example.tasklist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val messageForFragmentTask: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val messageForCompletedTasks: MutableLiveData<ArrayList<Task>> by lazy{
        MutableLiveData<ArrayList<Task>>()
    }


}