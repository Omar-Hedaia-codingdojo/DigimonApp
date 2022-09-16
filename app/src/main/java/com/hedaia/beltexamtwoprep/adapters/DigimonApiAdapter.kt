package com.hedaia.beltexamtwoprep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hedaia.beltexamtwoprep.models.DigimonsItem
import com.hedaia.beltexamtwoprep.R
import com.hedaia.beltexamtwoprep.databinding.DigimonApiRowLayoutBinding

class DigimonApiAdapter(var digimonList:List<DigimonsItem>, val favoritesClickListener: FavoritesClickListener):RecyclerView.Adapter<DigimonApiAdapter.ViewHolder>() {
    class ViewHolder(val binding:DigimonApiRowLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DigimonApiRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val digimon = digimonList[position]
        holder.binding.apply {

            digimonNameTv.text = "Name: ${digimon.name}"
            digimonLevelTv.text = "Level: ${digimon.level}"
            val img = digimon.img
            Glide.with(root.context).load(img).placeholder(R.drawable.ic_launcher_foreground).into(digimonImageIv)

            favoriteBtn.setOnClickListener{

                favoritesClickListener.onClick(digimon)

            }

        }

    }

    override fun getItemCount(): Int {
        return digimonList.size
    }

    fun updateAdapterList(newDigimonList:List<DigimonsItem>){
        digimonList = newDigimonList
        notifyDataSetChanged()

    }

    interface FavoritesClickListener{
        fun onClick(digimonsItem: DigimonsItem)
    }
}