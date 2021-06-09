package com.cursosant.android.stores.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.jauregui.R
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.databinding.RutasViewListBinding


class RutasAdapter(private var rutas: MutableList<RutaEntity>,
                   private var listener: OnClickListener) :

    RecyclerView.Adapter<RutasAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.rutas_view_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ruta = rutas.get(position)

        with(holder){
            setListener(ruta)

            binding.rDescp.text = ruta.vDescription
            binding.rUser.text = ruta.vUserID
            binding.rDate.text = ruta.vFechaEntre.toString()
        }
    }

    override fun getItemCount(): Int = rutas.size

    fun setStores(rutas: List<RutaEntity>) {
        this.rutas = rutas as MutableList<RutaEntity>
        notifyDataSetChanged()
    }

    fun add(rutaEntity: RutaEntity) {
        if (rutaEntity.idLocation != 0L) {
            if (!rutas.contains(rutaEntity)) {
                rutas.add(rutaEntity)
                notifyItemInserted(rutas.size-1)
            } else {
                update(rutaEntity)
            }
        }
    }

    private fun update(rutaEntity: RutaEntity) {
        val index = rutas.indexOf(rutaEntity)
        if (index != -1){
            rutas.set(index, rutaEntity)
            notifyItemChanged(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = RutasViewListBinding.bind(view)

        fun setListener(rutaEntity: RutaEntity){
            with(binding.root) {
                setOnClickListener { listener.onClick(rutaEntity) }
                setOnLongClickListener {
                    listener.onDeleteRuta(rutaEntity)
                    true
                }
            }
        }
    }
}