package com.example.secretcustomer.util.textWatchers

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData

class LiveDataTextWatcher(private val liveData: MutableLiveData<String>)
    : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        if (liveData.value != s.toString().trim()) {
            liveData.value = s.toString().trim()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}