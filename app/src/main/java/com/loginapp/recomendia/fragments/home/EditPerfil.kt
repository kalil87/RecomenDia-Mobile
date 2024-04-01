package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.Preferences
import com.loginapp.recomendia.entities.RecuperarParams

class EditPerfil : Fragment() {
    companion object { fun newInstance() = EditPerfil() }
    private lateinit var viewModel: EditPerfilViewModel
    lateinit var vEdit: View
    lateinit var nameUser: TextView
    lateinit var emailUser: EditText
    lateinit var passUser: EditText
    lateinit var passConfUser: EditText
    lateinit var btnSave: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vEdit = inflater.inflate(R.layout.fragment_edit_perfil, container, false)
        nameUser = vEdit.findViewById(R.id.nameEdit)
        emailUser = vEdit.findViewById(R.id.emailEdit)
        passUser = vEdit.findViewById(R.id.passwordEdit)
        passConfUser = vEdit.findViewById(R.id.confirmPassEdit)
        btnSave = vEdit.findViewById(R.id.goToSaveButton)
        return vEdit
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditPerfilViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val preferences = Preferences(requireContext())
        val usuario = preferences.obtenerUsuario()
        nameUser.text = usuario.name
        val emailViejo = usuario.email
        emailUser.setText(usuario.email)
        btnSave.setOnClickListener{
            if(viewModel.validarFormulario(emailUser.text, passUser.text, passConfUser.text)) {
                Snackbar.make(vEdit, "Rellenar todos los campos.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener // es como un break, aca te cortaria btnSave.setOnClickListener{}
            }
            if(!viewModel.validarEmail(emailUser.text)){
                Snackbar.make(vEdit, "Correo electrónico inválido.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!viewModel.validarContraseña(passUser.text)){
                Snackbar.make(vEdit, "La contraseña debe tener entre 6 a 12 caracteres, una mayúscula, un numero y un caracter especial", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!viewModel.validarPassword(passUser.text, passConfUser.text)){
                Snackbar.make(vEdit, "Las contraseñas no coincíden.", Snackbar.LENGTH_SHORT).show()
            } else {
                val params = RecuperarParams(usuario.token!!, passUser.text.toString())
                viewModel.editarUsuario(params)
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner, Observer { success ->
            if(success !== null) {
                preferences.guardarUsuario(success)
                Snackbar.make(vEdit, "Se guardaron los cambios.", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editPerfil_to_perfilFragment2)
            }
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            if(error !== null) {
                Snackbar.make(vEdit, error, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}