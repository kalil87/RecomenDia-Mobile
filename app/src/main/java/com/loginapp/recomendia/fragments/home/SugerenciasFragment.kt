package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.loginapp.recomendia.LoadingDialog
import com.loginapp.recomendia.R
import com.loginapp.recomendia.adapters.CategoriasAdapter
import com.loginapp.recomendia.adapters.RecomendacionesAdapter
import com.loginapp.recomendia.repository.CategoriasRepository

class SugerenciasFragment : Fragment() {
    companion object { fun newInstance() = SugerenciasFragment() }
    private lateinit var viewModel: SugerenciasViewModel
    private lateinit var recyclerSugerencias: RecyclerView
    private lateinit var adapter: RecomendacionesAdapter
    private lateinit var v : View
    private lateinit var loading: LoadingDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_sugerencias, container, false)
        loading = LoadingDialog(requireActivity())
        recyclerSugerencias = v.findViewById(R.id.recSugerencias)
        viewModel = ViewModelProvider(requireActivity()).get(SugerenciasViewModel::class.java)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { recomendaciones ->
            adapter = RecomendacionesAdapter(recomendaciones) { position ->
                var action = SugerenciasFragmentDirections.actionSugerenciasFragment2ToRecomendacionFragment(
                    recomendaciones[position].categoriaId,
                    recomendaciones[position].subcategoriaId,
                    recomendaciones[position].message,
                    null
                )
                findNavController().navigate(action)
            }
            recyclerSugerencias.layoutManager = LinearLayoutManager(context)
            recyclerSugerencias.adapter = adapter
            loading.stopLoading()
        })
        loading.startLoading("Obteniendo sugerencias...")
        viewModel.getRecomendaciones()
    }

    override fun onPause() {
        super.onPause()
        viewModel.killViewModel()
    }
}