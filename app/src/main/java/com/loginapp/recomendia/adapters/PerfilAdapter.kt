package com.loginapp.recomendia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.Categoria
import com.loginapp.recomendia.entities.Recomendacion
import com.loginapp.recomendia.utils.Utils
import java.util.Locale

class PerfilAdapter(var recomendaciones: MutableList<Recomendacion>, var onClick: (Int) -> Unit): RecyclerView.Adapter<PerfilAdapter.RecomendacionHolder>() {
    private val utils = Utils()
    inner class RecomendacionHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init { this.view = v }

        fun setRecomendacionCategoriaName(name: String) {
            var txtName : TextView = view.findViewById(R.id.idRecomendacionCategoriaName)
            txtName.text = name
        }

        fun setRecomendacionSubcategoriaName(name: String) {
            var txtName : TextView = view.findViewById(R.id.idRecomendacionSubcategoriaName)
            txtName.text = name
        }

        fun setRecomendacionCategoriaImage(imageName: String) {
            var imgName : ImageView = view.findViewById(R.id.idRecomendacionImgName)
            imgName.setImageResource(view.context.resources.getIdentifier(imageName, "drawable", view.context.packageName))
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardRecomendacion)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendacionHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recomendaciones_item,parent,false)
        return (RecomendacionHolder(view))
    }

    override fun getItemCount(): Int {
        return recomendaciones.size
    }

    override fun onBindViewHolder(holder: RecomendacionHolder, position: Int) {
        holder.setRecomendacionCategoriaName((recomendaciones[position].categoriaName))
        holder.setRecomendacionSubcategoriaName((recomendaciones[position].subCategoriaName))
        holder.setRecomendacionCategoriaImage(utils.quitarTildes((recomendaciones[position].categoriaName).lowercase(Locale.ROOT)))
        holder.getCard().setOnClickListener{
            onClick(position)
        }
    }
}