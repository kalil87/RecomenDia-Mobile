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
import androidx.navigation.fragment.navArgs
import com.loginapp.recomendia.LoadingDialog
import com.loginapp.recomendia.R
import com.loginapp.recomendia.entities.Preferences
import com.loginapp.recomendia.repository.CategoriasRepository
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService

class RecomendacionFragment : Fragment() {
    companion object { fun newInstance() = RecomendacionFragment() }
    private lateinit var viewModel: RecomendacionViewModel
    private lateinit var v : View
    lateinit var recText: TextView
    lateinit var catText: TextView
    lateinit var subText: TextView
    lateinit var copyButton: Button
    val args: RecomendacionFragmentArgs by navArgs()
    private lateinit var loading: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_recomendacion, container, false)
        copyButton = v.findViewById(R.id.buttonCopy)
        loading = LoadingDialog(requireActivity())
        recText = v.findViewById(R.id.recMessage)
        catText = v.findViewById(R.id.textCatRec)
        subText = v.findViewById(R.id.subTextRec)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RecomendacionViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { message ->
            recText.text = message
            loading.stopLoading()
        })
        catText.text = CategoriasRepository.obtenerNombreCategoria(args.id)
        subText.text = CategoriasRepository.obtenerNombreSubcategoria(args.id, args.idSub)
        if(args.message != null) {
            println("entrando por sugerencias")
            println(args.especificaciones)
            recText.text = args.message
        } else {
            println(args)
            val preferences = Preferences(requireContext())
            val id = preferences.obtenerUsuario().id
            if(id !== null){
                loading.startLoading("Obteniendo recomendación...")
                viewModel.getRecomendacion(args.id, args.idSub, id, args.especificaciones)
            }
            // TODO: ACA HAY QUE MOSTRAR UN MENSAJE DE QUE OCURRIO UN ERROR Y POSIBLEMENTE SACARLO DE LA APP
        }
    }

    override fun onStart() {
        super.onStart()
        copyButton.setOnClickListener{
            // Obtiene el gestor del portapapeles del sistema
            val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // Crea un ClipData con el texto a copiar
            val clip = ClipData.newPlainText("Etiqueta", recText.text)
            // Copia el texto al portapapeles
            clipboardManager.setPrimaryClip(clip)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.killViewModel()
    }
}