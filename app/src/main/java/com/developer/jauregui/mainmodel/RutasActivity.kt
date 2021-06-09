package com.developer.jauregui.mainmodel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.stores.editModule.viewModel.EditRutaViewModel
import com.cursosant.android.stores.mainModule.adapter.OnClickListener
import com.cursosant.android.stores.mainModule.adapter.RutasAdapter
import com.cursosant.android.stores.mainModule.viewModel.MainViewModel
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.databinding.ActivityRutasBinding

class RutasActivity : AppCompatActivity(), OnClickListener {
    private lateinit var rutasViewBinding: ActivityRutasBinding

    private lateinit var rutaAdapter: RutasAdapter
    private lateinit var rutaGridLayout: GridLayoutManager


    private lateinit var rutaMainViewModel: MainViewModel
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
        rutaMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        rutaMainViewModel.getStores().observe(this, { stores->
            rutaAdapter.setStores(stores)
        })

        rutaEditStoreViewModel = ViewModelProvider(this).get(EditRutaViewModel::class.java)
        rutaEditStoreViewModel.getShowFab().observe(this, { isVisible ->
            if (isVisible) rutasViewBinding.fab.show() else rutasViewBinding.fab.hide()
        })
        rutaEditStoreViewModel.getStoreSelected().observe(this, { storeEntity ->
            rutaAdapter.add(storeEntity)
        })
    }

    private fun launchEditFragment(rutaEntity: RutaEntity = RutaEntity()) {
        rutaEditStoreViewModel.setShowFab(false)
        rutaEditStoreViewModel.setStoreSelected(rutaEntity)

        val fragment = EditRutaViewModel()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        //fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setupRecylcerView() {
        rutaAdapter = RutasAdapter(mutableListOf(), this)
        //rutaGridLayout = GridLayoutManager(this, resources.getInteger(R.integer.main_columns))

        rutasViewBinding.recyclerView.apply {
            setHasFixedSize(true)
            //layoutManager = mGridLayout
            adapter = rutaAdapter
        }
    }

    /*
    * OnClickListener
    * */
    override fun onClick(storeEntity: RutaEntity) {
        //launchEditFragment(storeEntity)
        print("Entro")
    }



    override fun onDeleteStore(rutaEntity: RutaEntity) {
        /*
        val items = resources.getStringArray(R.array.array_options_item)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_options_title)
            .setItems(items, { dialogInterface, i ->
                when(i){
                    0 -> confirmDelete(rutaEntity)

                    1 -> dial(rutaEntity.phone)

                    2 -> goToWebsite(rutaEntity.website)
                }
            })
            .show()
            */
    }

    private fun confirmDelete(storeEntity: RutaEntity){
        /*
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_confirm, { dialogInterface, i ->
                mMainViewModel.deleteStore(storeEntity)
            })
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()
        */
    }

    private fun dial(phone: String){
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phone")
        }

        startIntent(callIntent)
    }

    private fun goToWebsite(website: String){
        /*
        if (website.isEmpty()){
            Toast.makeText(this, R.string.main_error_no_website, Toast.LENGTH_LONG).show()
        } else {
            val websiteIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(website)
            }

            startIntent(websiteIntent)
        }

       */
    }

    private fun startIntent(intent: Intent){
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
        else
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()

    }
}