package com.hedaia.beltexamtwoprep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hedaia.beltexamtwoprep.models.DigimonModel
import com.hedaia.beltexamtwoprep.R
import com.hedaia.beltexamtwoprep.databinding.DigimonDbRowLayoutBinding

class DigimonDBAdapter(var digimonList:List<DigimonModel>, val deleteFavoritesClickListener: DeleteFavoritesClickListener):RecyclerView.Adapter<DigimonDBAdapter.ViewHolder>() {
    class ViewHolder(val binding:DigimonDbRowLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DigimonDbRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val digimon = digimonList[position]

        holder.binding.apply {

            digimonNameTv.text = "Name: ${digimon.name}"
            digimonLevelTv.text = "Level: ${digimon.level}"
            val img = digimon.img
            Glide.with(root.context).load(img).placeholder(R.drawable.ic_launcher_foreground).into(digimonImageIv)

            deleteFavoriteBtn.setOnClickListener{

                deleteFavoritesClickListener.onClick(digimon)

            }

        }

    }

    override fun getItemCount(): Int {
        return digimonList.size
    }

    fun updateAdapterList(newDigimonList:List<DigimonModel>){
        digimonList = newDigimonList
        notifyDataSetChanged()

    }

    interface DeleteFavoritesClickListener{
        fun onClick(digimonsItem: DigimonModel)
    }
}