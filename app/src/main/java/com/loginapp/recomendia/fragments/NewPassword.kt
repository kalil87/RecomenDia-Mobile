package com.loginapp.recomendia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.loginapp.recomendia.R

class NewPassword : Fragment() {
    companion object { fun newInstance() = NewPassword() }
    private lateinit var viewModel: NewPasswordViewModel
    lateinit var vNewPassword: View
    lateinit var btnPassword: Button
    lateinit var newPassword : EditText
    lateinit var checkPassword : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vNewPassword =  inflater.inflate(R.layout.fragment_new_password, container, false)
        btnPassword = vNewPassword.findViewById(R.id.btnPassword)
        newPassword = vNewPassword.findViewById(R.id.newPassword)
        checkPassword = vNewPassword.findViewById(R.id.checkPassword)
        return vNewPassword
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewPasswordViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnPassword.setOnClickListener{
            if(viewModel.validarFormulario(newPassword.text, checkPassword.text)){
                Snackbar.make(vNewPassword, "Rellenar todos los campos.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(viewModel.validarPassword(newPassword.text, checkPassword.text)){
                Snackbar.make(vNewPassword, "Se realizo el cambio de contraseña correctamente", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_newPassword_to_loginFragment)
            } else{
                Snackbar.make(vNewPassword, "No hay coincidencias en las contraseñas", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}