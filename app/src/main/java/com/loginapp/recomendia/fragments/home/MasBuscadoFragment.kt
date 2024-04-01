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
import com.loginapp.recomendia.LoadingDialog
import com.loginapp.recomendia.R
import com.loginapp.recomendia.adapters.CategoriasAdapter
import com.loginapp.recomendia.adapters.MasBuscadoAdapter

class
MasBuscadoFragment : Fragment() {
    companion object { fun newInstance() = MasBuscadoFragment() }
    private lateinit var viewModel: MasBuscadoViewModel
    private lateinit var adapter: MasBuscadoAdapter
    private lateinit var recyclerMasBuscado: RecyclerView
    private lateinit var v : View
    private lateinit var loading: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_mas_buscado, container, false)
        recyclerMasBuscado = v.findViewById(R.id.recMasBuscado)
        loading = LoadingDialog(requireActivity())
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MasBuscadoViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { categorias ->
            adapter = MasBuscadoAdapter(categorias) { position ->
                var action = CategoriasFragmentDirections.actionCategoriasFragment2ToSubcategoriasFragment(categorias[position]._id)
                findNavController().navigate(action)
            }
            recyclerMasBuscado.layoutManager = LinearLayoutManager(context)
            recyclerMasBuscado.adapter = adapter
            loading.stopLoading()
        })
        loading.startLoading("Obteniendo lo más buscado...")
        viewModel.getMasBuscado()
    }

    override fun onPause() {
        super.onPause()
        viewModel.killViewModel()
    }
}