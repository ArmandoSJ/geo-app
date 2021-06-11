package com.developer.jauregui.mainmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.stores.editModule.viewModel.EditRutaViewModel
import com.cursosant.android.stores.mainModule.adapter.OnClickListener
import com.cursosant.android.stores.mainModule.adapter.RutasAdapter
import com.cursosant.android.stores.mainModule.viewModel.RutaModel
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.databinding.ActivityRutasBinding

class RutasActivity : AppCompatActivity(), OnClickListener {
    private lateinit var rutasViewBinding: ActivityRutasBinding

    private lateinit var rutaAdapter: RutasAdapter
    private lateinit var rutaGridLayout: GridLayoutManager


    private lateinit var rutaMainViewModel: RutaModel
    private lateinit var rutaEditStoreViewModel: EditRutaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rutasViewBinding = ActivityRutasBinding.inflate(layoutInflater)
        setContentView(rutasViewBinding.root)

        rutasViewBinding.fab.setOnClickListener { launchEditFragment() }

        setupViewModel()
        setupRecylcerView()
    }

    private fun setupViewModel() {
        rutaMainViewModel = ViewModelProvider(this).get(RutaModel::class.java)
        rutaMainViewModel.getRutas().observe(this, { rutas->
            rutaAdapter.setRutas(rutas)
        })

        rutaEditStoreViewModel = ViewModelProvider(this).get(EditRutaViewModel::class.java)
        rutaEditStoreViewModel.getShowFab().observe(this, { isVisible ->
            if (isVisible) rutasViewBinding.fab.show() else rutasViewBinding.fab.hide()
        })

        //agregamos a nuestra base de datos una ruta.
        rutaEditStoreViewModel.getRutaSelected().observe(this, { rutaEntity ->
            rutaAdapter.add(rutaEntity)
        })
    }

    private fun launchEditFragment(rutaEntity: RutaEntity = RutaEntity()) {
        rutaEditStoreViewModel.setShowFab(false)
        rutaEditStoreViewModel.setRutaSelected(rutaEntity)

        val fragment = EditRutaViewModel()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setupRecylcerView() {
        rutaAdapter = RutasAdapter(mutableListOf(), this)

        rutasViewBinding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = rutaAdapter
        }
    }


    override fun onClick(rutaEntity: RutaEntity) {
        print("Entro")
    }



    override fun onDeleteRuta(rutaEntity: RutaEntity) {
    }

    private fun confirmDelete(rutaEntity: RutaEntity){

    }




    private fun startIntent(intent: Intent){
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
        else
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()

    }
}