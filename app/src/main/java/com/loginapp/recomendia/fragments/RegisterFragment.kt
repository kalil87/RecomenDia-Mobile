package com.loginapp.recomendia.fragments

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
import com.loginapp.recomendia.entities.Usuario

class RegisterFragment : Fragment() {
    companion object { fun newInstance() = RegisterFragment() }
    private lateinit var viewModel: RegisterViewModel
    lateinit var vRegister: View
    lateinit var btnGoLogin: Button
    lateinit var nameUser: EditText
    lateinit var mailUser: EditText
    lateinit var passUser: EditText
    lateinit var passConfUser: EditText
    lateinit var nationality: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vRegister = inflater.inflate(R.layout.fragment_register, container, false)
        btnGoLogin = vRegister.findViewById(R.id.goToSaveButton)
        nameUser = vRegister.findViewById(R.id.nameEdit)
        mailUser = vRegister.findViewById(R.id.emailEdit)
        passUser = vRegister.findViewById(R.id.passwordEdit)
        passConfUser = vRegister.findViewById(R.id.confirmPassEdit)
        nationality = vRegister.findViewById(R.id.nationalityRegister)
        return vRegister
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnGoLogin.setOnClickListener{
            if(viewModel.validarFormulario(nameUser.text, mailUser.text, passUser.text, passConfUser.text, nationality.text)){
                Snackbar.make(vRegister, "Rellenar todos los campos.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!viewModel.validarEmail(mailUser.text)){
                Snackbar.make(vRegister, "Correo electrónico inválido.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!viewModel.validarPassword(passUser.text, passConfUser.text)){
                Snackbar.make(vRegister, "Las contraseñas no coincíden.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!viewModel.validarContraseña(passUser.text)){
                Snackbar.make(vRegister, "La contraseña debe tener entre 6 a 12 caracteres, una mayúscula, un numero y un caracter especial", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user: Usuario = Usuario(
                null,
                nameUser.text.toString(),
                mailUser.text.toString(),
                passUser.text.toString(),
                nationality.text.toString(),
                null
            )
            viewModel.registrarUsuario(user)
            viewModel.liveData.observe(viewLifecycleOwner, Observer { success ->
                if(success !== null && success === "") {
                    Snackbar.make(vRegister, "Se registro usuario correctamente.", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else if (success !== null && success !== "") {
                    Snackbar.make(vRegister, success, Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }
}