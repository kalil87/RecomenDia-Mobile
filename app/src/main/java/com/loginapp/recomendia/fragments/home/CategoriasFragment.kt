package com.loginapp.recomendia.fragments.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.switchMap
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginapp.recomendia.R
import com.loginapp.recomendia.adapters.CategoriasAdapter
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.repository.CategoriasRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriasFragment : Fragment() {
    companion object { fun newInstance() = CategoriasFragment() }
    private lateinit var viewModel: CategoriasViewModel
    private lateinit var recyclerCategorias: RecyclerView
    private lateinit var adapter: CategoriasAdapter
    private lateinit var v : View
    private val categoriasRepository: CategoriasRepository = CategoriasRepository;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_categorias, container, false)
        recyclerCategorias = v.findViewById(R.id.recCategorias)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriasViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { categorias ->
            adapter = CategoriasAdapter(categorias) { position ->
                var action = CategoriasFragmentDirections.actionCategoriasFragment2ToSubcategoriasFragment(categorias[position]._id)
                findNavController().navigate(action)
            }
            recyclerCategorias.layoutManager = GridLayoutManager(context, 2)
            recyclerCategorias.adapter = adapter
        })
        viewModel.getCategorias()
    }
}