package com.loginapp.recomendia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.fragments.home.SugerenciasFragmentDirections
import com.loginapp.recomendia.repository.CategoriasRepository
import com.loginapp.recomendia.utils.Utils
import java.util.Locale

class MasBuscadoRecomendacionesAdapter(var recomendaciones: MutableList<Recomendacion>, var onClick: (Int) -> Unit): RecyclerView.Adapter<MasBuscadoRecomendacionesAdapter.MasBuscadoRecomendacionesHolder>() {
    private val utils = Utils()
    inner class MasBuscadoRecomendacionesHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init { this.view = v }

        fun setRecomendacionSubcategoriaName(name: String) {
            var txtName : TextView = view.findViewById(R.id.textSubcategoriaMasBuscado)
            txtName.text = name
        }

        fun setRecomendacionCategoriaImage(imageName: String) {
            var imgName : ImageView = view.findViewById(R.id.imgSubcategoriaMasBuscado)
            imgName.setImageResource(view.context.resources.getIdentifier(imageName, "drawable", view.context.packageName))
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardRecomendacionMasBuscado)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasBuscadoRecomendacionesHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.mas_buscado_recomendaciones_item,parent,false)
        return (MasBuscadoRecomendacionesHolder(view))
    }

    override fun getItemCount(): Int {
        return recomendaciones.size
    }

    override fun onBindViewHolder(holder: MasBuscadoRecomendacionesHolder, position: Int) {
        var categoriaName = CategoriasRepository.obtenerNombreCategoria(recomendaciones[position].categoriaId)
        var subCategoriaName = CategoriasRepository.obtenerNombreSubcategoria(recomendaciones[position].categoriaId, recomendaciones[position].subcategoriaId)
        holder.setRecomendacionSubcategoriaName(subCategoriaName)
        holder.setRecomendacionCategoriaImage(utils.quitarTildes((categoriaName).lowercase(Locale.ROOT)))
        holder.getCard().setOnClickListener{
            onClick(position)
        }
    }
}