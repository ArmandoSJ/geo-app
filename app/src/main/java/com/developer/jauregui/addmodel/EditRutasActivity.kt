package com.developer.jauregui.addmodel

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cursosant.android.stores.editModule.viewModel.EditRutaViewModel
import com.developer.jauregui.MainActivity
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.databinding.FragmentEditRutasActivityBinding

class EditRutasActivity : Fragment() {

    private lateinit var rutasBinding: FragmentEditRutasActivityBinding
    //MVVM
    private lateinit var editRutaViewModel: EditRutaViewModel

    private var mapActivity: MainActivity? = null
    private var isEditMode: Boolean = false
    private lateinit var rutaEntity: RutaEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        editRutaViewModel = ViewModelProvider(requireActivity()).get(EditRutaViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {
        rutasBinding = FragmentEditRutasActivityBinding.inflate(inflater, container, false)

        return rutasBinding.root
    }
}