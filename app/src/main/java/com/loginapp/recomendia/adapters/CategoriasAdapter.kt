package com.loginapp.recomendia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.fragments.home.SugerenciasFragmentDirections
import com.loginapp.recomendia.utils.Utils
import java.util.Locale

class CategoriasAdapter(var categorias: MutableList<Categoria>, var onClick: (Int) -> Unit): RecyclerView.Adapter<CategoriasAdapter.CategoriaHolder>() {
    private val utils = Utils()

    inner class CategoriaHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init { this.view = v }

        fun setCategoriaName(name: String) {
            var txtName : TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }

        fun setCategoriaImage(imageName: String) {
            var imgName : ImageView = view.findViewById(R.id.imgName)
            imgName.setImageResource(view.context.resources.getIdentifier(imageName, "drawable", view.context.packageName))
        }

        fun setRecyclerView(recomendaciones: MutableList<Recomendacion>) {
            var childRecyclerView: RecyclerView = view.findViewById(R.id.child_recyclerview)
            val layoutManager = LinearLayoutManager(childRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager.initialPrefetchItemCount = recomendaciones.size
            val childItemAdapter = RecomendacionesAdapter(recomendaciones){ position ->
                var action = SugerenciasFragmentDirections.actionSugerenciasFragment2ToRecomendacionFragment(
                    recomendaciones[position].categoriaId,
                    recomendaciones[position].subcategoriaId,
                    recomendaciones[position].message,
                    null
                )
                //findNavController().navigate(action)
            }
            childRecyclerView.layoutManager = layoutManager
            childRecyclerView.adapter = childItemAdapter
            val viewPool = RecycledViewPool()
            childRecyclerView.setRecycledViewPool(viewPool)
        }

         fun getCard(): CardView {
             return view.findViewById(R.id.cardCategoria)
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.categorias_item,parent,false)
        return (CategoriaHolder(view))
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        if (categorias[position].name !== null) {
            holder.setCategoriaName((categorias[position].name))
            holder.setCategoriaImage(utils.quitarTildes((categorias[position].name).lowercase(Locale.ROOT)))
        }
        if (categorias[position].recomendaciones !== null){
            holder.setRecyclerView(categorias[position].recomendaciones!!)
        }
        holder.getCard().setOnClickListener{
            onClick(position)
        }
    }
}