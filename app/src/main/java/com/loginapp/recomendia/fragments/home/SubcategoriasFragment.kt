package com.loginapp.recomendia.fragments.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.loginapp.recomendia.R
import com.loginapp.recomendia.adapters.SubCategoriasAdapter
import com.loginapp.recomendia.entities.Categoria

class SubcategoriasFragment : Fragment() {
    companion object { fun newInstance() = SubcategoriasFragment() }
    private lateinit var viewModel: SubcategoriasViewModel
    private lateinit var recyclerSubCategorias: RecyclerView
    private lateinit var v : View
    private lateinit var adapter: SubCategoriasAdapter
    val args: SubcategoriasFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_subcategorias, container, false)
        recyclerSubCategorias = v.findViewById(R.id.recSubCategorias)
        viewModel = ViewModelProvider(requireActivity()).get(SubcategoriasViewModel::class.java)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { categoria ->
            adapter = SubCategoriasAdapter(categoria.subCategories) { position ->
                showObservationDialog(categoria, position)
                /*var action = SubcategoriasFragmentDirections.actionSubcategoriasFragmentToRecomendacionFragment(
                    categoria._id,
                    categoria.subCategories[position]._id,
                    null
                )
                findNavController().navigate(action)*/
            }
            recyclerSubCategorias.layoutManager = LinearLayoutManager(context)
            recyclerSubCategorias.adapter = adapter
        })
        viewModel.cargarSubCategorias(args.id)
    }

    @SuppressLint("MissingInflatedId")
    private fun showObservationDialog(categoria: Categoria, position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.especificaciones_dialog, null)
        val especificacionesEditText = dialogView.findViewById<EditText>(R.id.especificacionesEditText)
        val especificacionesCheckBox = dialogView.findViewById<CheckBox>(R.id.especificacionesCheckBox)
        // Habilitar o deshabilitar el EditText según el estado del CheckBox
        especificacionesCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            especificacionesEditText.isEnabled = !isChecked
        }
        val dialog = AlertDialog.Builder(this.requireContext())
            .setView(dialogView)
            .setPositiveButton("Confirmar") { dialog, which ->
                if (especificacionesCheckBox.isChecked) {
                    navigate(categoria, position, null)
                } else {
                    val especificaciones = especificacionesEditText.text.toString()
                    navigate(categoria, position, especificaciones)
                }

            }
            .setNegativeButton("Cancelar") { dialog, which ->
            }
            .create()
        dialog.show()
    }

    private fun navigate(categoria: Categoria, position: Int, especificaciones: String?){
        var action = SubcategoriasFragmentDirections.actionSubcategoriasFragmentToRecomendacionFragment(categoria._id, categoria.subCategories[position]._id, null, especificaciones)
        findNavController().navigate(action)
    }
}