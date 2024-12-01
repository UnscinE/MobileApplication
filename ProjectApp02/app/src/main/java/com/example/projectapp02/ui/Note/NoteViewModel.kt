package com.example.projectapp02.ui.Note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Note Fragment"
    }
    val text: LiveData<String> = _text
}