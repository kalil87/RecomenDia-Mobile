package com.loginapp.recomendia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.fragments.home.MasBuscadoFragmentDirections
import com.loginapp.recomendia.fragments.home.SugerenciasFragmentDirections
import com.loginapp.recomendia.repository.CategoriasRepository
import java.util.Locale

class MasBuscadoAdapter(var categorias: MutableList<Categoria>, var onClick: (Int) -> Unit): RecyclerView.Adapter<MasBuscadoAdapter.MasBuscadoHolder>() {

    inner class MasBuscadoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init { this.view = v }

        fun setCategoriaName(name: String, cantidad: Int) {
            var txtName : TextView = view.findViewById(R.id.texrMasBuscadoCategoria)
            txtName.text = name + " (" + cantidad + ")"
        }

        fun setRecyclerView(recomendaciones: MutableList<Recomendacion>) {
            var childRecyclerView: RecyclerView = view.findViewById(R.id.child_recyclerview)
            val layoutManager = LinearLayoutManager(childRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager.initialPrefetchItemCount = recomendaciones.size
            val childItemAdapter = MasBuscadoRecomendacionesAdapter(recomendaciones){ position ->
                var action = MasBuscadoFragmentDirections.actionMasBuscadoFragment2ToRecomendacionFragment(
                    recomendaciones[position].categoriaId,
                    recomendaciones[position].subcategoriaId,
                    recomendaciones[position].message,
                    null
                )
                view.findNavController().navigate(action)
            }
            childRecyclerView.layoutManager = layoutManager
            childRecyclerView.adapter = childItemAdapter
            val viewPool = RecyclerView.RecycledViewPool()
            childRecyclerView.setRecycledViewPool(viewPool)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasBuscadoHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.mas_buscado_item,parent,false)
        return (MasBuscadoHolder(view))
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onBindViewHolder(holder: MasBuscadoHolder, position: Int) {
        if (categorias[position].recomendaciones !== null && categorias[position].categoriaId !== null){
            var categoriaName = CategoriasRepository.obtenerNombreCategoria(categorias[position].categoriaId!!)
            holder.setCategoriaName(categoriaName, categorias[position].recomendaciones!!.size)
            holder.setRecyclerView(categorias[position].recomendaciones!!)
        }
    }
}