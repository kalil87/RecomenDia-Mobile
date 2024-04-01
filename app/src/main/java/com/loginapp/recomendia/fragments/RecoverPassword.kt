package com.loginapp.recomendia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.loginapp.recomendia.R

class RecoverPassword : Fragment() {
    companion object { fun newInstance() = RecoverPassword() }
    private lateinit var viewModel: RecoverPasswordViewModel
    lateinit var vRecover: View
    lateinit var btnRecover: Button
    lateinit var mailRecover : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vRecover = inflater.inflate(R.layout.fragment_recover_password, container, false)
        btnRecover = vRecover.findViewById(R.id.buttonRecover)
        mailRecover = vRecover.findViewById(R.id.emailRecover)
        return vRecover
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecoverPasswordViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnRecover.setOnClickListener{
            if(!viewModel.validarEmail(mailRecover.text)){
                Snackbar.make(vRecover, "Correo electrónico inválido.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val usuario = viewModel.enviarRecuperoContraseña(mailRecover.text)
            if(usuario != null) {
                Snackbar.make(vRecover, "Correo enviado, verifica la casilla para recuperar la contraseña", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(vRecover, "Ocurrio un error al recuperar la contraseña", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}