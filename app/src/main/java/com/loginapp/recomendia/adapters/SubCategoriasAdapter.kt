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
import com.loginapp.recomendia.utils.Utils
import java.util.Locale

class SubCategoriasAdapter(var subCategorias: MutableList<Categoria>, var onClick: (Int) -> Unit): RecyclerView.Adapter<SubCategoriasAdapter.SubCategoriaHolder>() {
    private val utils = Utils()
    inner class SubCategoriaHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init { this.view = v }

        fun setSubCategoriaName(name: String) {
            var txtName : TextView = view.findViewById(R.id.txtSubCategoriaName)
            txtName.text = name
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardSubCategoria)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoriaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.subcategorias_item,parent,false)
        return (SubCategoriaHolder(view))
    }

    override fun getItemCount(): Int {
        return subCategorias.size
    }

    override fun onBindViewHolder(holder: SubCategoriaHolder, position: Int) {
        holder.setSubCategoriaName((subCategorias[position].name))
        holder.getCard().setOnClickListener{
            onClick(position)
        }
    }
}