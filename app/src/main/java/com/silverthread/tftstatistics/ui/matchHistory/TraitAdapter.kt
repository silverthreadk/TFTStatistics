package com.silverthread.tftstatistics.ui.matchHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.model.response.TraitDTO
import kotlinx.android.synthetic.main.list_item_item.view.*
import kotlinx.android.synthetic.main.list_item_trait.view.*

class TraitAdapter(private val traits: MutableList<TraitDTO>): RecyclerView.Adapter<TraitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var trait: TraitDTO

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(trait: TraitDTO) {
            this.trait = trait
            val traitName = trait.name?.let{
                if (it.startsWith("Set4_")){
                    it.substring(5).toLowerCase()
                } else {
                    it.toLowerCase()
                }
            }
            val context = itemView.context
            itemView.traitImage.setImageResource(
                    context.resources.getIdentifier("trait_" + traitName, "drawable", context.packageName))
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_trait, parent, false))
    }

    override fun getItemCount() = traits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(traits[position])
    }

    fun updateTraits(traits: List<TraitDTO>) {
        this.traits.clear()
        this.traits.addAll(traits)
        notifyDataSetChanged()
    }
}
