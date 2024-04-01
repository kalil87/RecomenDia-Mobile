package com.loginapp.recomendia.fragments

import android.text.Editable
import androidx.lifecycle.ViewModel

class NewPasswordViewModel : ViewModel() {

    fun validarFormulario(pass: Editable, confPass: Editable): Boolean {
        return pass.isEmpty() || confPass.isEmpty()
    }
    fun validarPassword(pass: Editable, confPass: Editable): Boolean{
        return pass.toString() == confPass.toString()
    }
}