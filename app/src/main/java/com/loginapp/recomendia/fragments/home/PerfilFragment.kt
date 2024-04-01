package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginapp.recomendia.LoadingDialog
import com.loginapp.recomendia.R
import com.loginapp.recomendia.adapters.PerfilAdapter
import com.loginapp.recomendia.adapters.RecomendacionesAdapter
import com.loginapp.recomendia.entities.Preferences

class PerfilFragment : Fragment() {
    companion object { fun newInstance() = PerfilFragment() }
    private val PREF_NAME = "myPreferences"
    private lateinit var viewModel: PerfilViewModel
    lateinit var vPerfil: View
    lateinit var btnEdit: Button
    lateinit var btnCerrarSesion: Button
    lateinit var email : TextView
    lateinit var name : TextView
    private lateinit var recyclerPerfil: RecyclerView
    private lateinit var adapter: PerfilAdapter
    private lateinit var loading: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vPerfil = inflater.inflate(R.layout.fragment_perfil, container, false)
        loading = LoadingDialog(requireActivity())
        btnEdit = vPerfil.findViewById(R.id.goToEditButton)
        btnCerrarSesion = vPerfil.findViewById(R.id.btnCerrarSesion)
        email = vPerfil.findViewById(R.id.mailUser)
        name = vPerfil.findViewById(R.id.nameUser)
        recyclerPerfil = vPerfil.findViewById(R.id.recPerfil)
        viewModel = ViewModelProvider(requireActivity()).get(PerfilViewModel::class.java)
        return vPerfil
    }

    override fun onStart() {
        super.onStart()
        val preferences = Preferences(requireContext())
        val usuario = preferences.obtenerUsuario()
        name.text = usuario.name
        email.text = usuario.email
        btnEdit.setOnClickListener{
            findNavController().navigate(R.id.action_perfilFragment2_to_editPerfil)
        }
        btnCerrarSesion.setOnClickListener {
            val preferences = Preferences(requireContext())
            preferences.borrarUsuario()
            findNavController().navigate(R.id.action_perfilFragment2_to_mainActivity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preferences = Preferences(requireContext())
        val usuario = preferences.obtenerUsuario()
        super.onViewCreated(view, savedInstanceState)
        val noRecomendacionesText = vPerfil.findViewById<TextView>(R.id.sinRecomendaciones)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { recomendaciones ->
            if(recomendaciones.isEmpty()){
                noRecomendacionesText.visibility = View.VISIBLE
                loading.stopLoading()
            } else{
                adapter = PerfilAdapter(recomendaciones) { position ->
                    var action = PerfilFragmentDirections.actionPerfilFragment2ToRecomendacionFragment2(
                        recomendaciones[position].categoriaId,
                        recomendaciones[position].subcategoriaId,
                        recomendaciones[position].message,
                        null
                    )
                    findNavController().navigate(action)
                }
                loading.stopLoading()
                recyclerPerfil.layoutManager = LinearLayoutManager(context)
                recyclerPerfil.adapter = adapter
            }
        })
        loading.startLoading("Obteniendo recomendaciones...")
        viewModel.getMisRecomendaciones(usuario.id.toString())
    }

    override fun onPause() {
        super.onPause()
        viewModel.killViewModel()
    }
}