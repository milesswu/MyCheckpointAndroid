package tech.mycheckpoint.ui.three

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThreeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is three Fragment"
    }
    val text: LiveData<String> = _text
}