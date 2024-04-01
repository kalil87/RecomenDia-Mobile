package com.loginapp.recomendia.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.LoginParams
import com.loginapp.recomendia.entities.Preferences
import com.loginapp.recomendia.entities.Usuario

class LoginFragment : Fragment() {
    companion object { fun newInstance() = LoginFragment() }
    private lateinit var viewModel: LoginViewModel
    private val PREF_NAME = "myPreferences"
    lateinit var vLogin: View
    lateinit var btnGoRegister: Button
    lateinit var btnGoPassword: Button
    lateinit var btnLogin: Button
    lateinit var emailSesion : EditText
    lateinit var passwordSesion : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vLogin = inflater.inflate(R.layout.fragment_login, container, false)
        btnGoRegister = vLogin.findViewById(R.id.goToRegisterButton)
        btnGoPassword = vLogin.findViewById(R.id.goToPassword)
        btnLogin = vLogin.findViewById(R.id.loginButton)
        emailSesion = vLogin.findViewById(R.id.emailSesion)
        passwordSesion = vLogin.findViewById(R.id.passwordSesion)
        return vLogin;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val preferences = Preferences(requireContext())
        val usuario = preferences.obtenerUsuario()
        println(usuario)
        if (!usuario.id.isNullOrEmpty()){
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity2)
        }
        btnGoRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        btnGoPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_recoverPassword)
        }
        btnLogin.setOnClickListener{
            if(viewModel.validarFormulario(emailSesion.text, passwordSesion.text)){
                Snackbar.make(vLogin, "Rellenar todos los campos.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val loginParams = LoginParams(emailSesion.text.toString(), passwordSesion.text.toString())
            viewModel.login(loginParams)
            viewModel.liveData.observe(viewLifecycleOwner, Observer { success ->
                if(success !== null) {
                    preferences.guardarUsuario(success)
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity2)
                }
            })
            viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
                if(error !== null) {
                    Snackbar.make(vLogin, error, Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }
}