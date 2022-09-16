package com.hedaia.beltexamtwoprep.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.beltexamtwoprep.db.DataBaseHelper
import com.hedaia.beltexamtwoprep.adapters.DigimonDBAdapter
import com.hedaia.beltexamtwoprep.models.DigimonModel
import com.hedaia.beltexamtwoprep.databinding.ActivityDatabaseBinding
import kotlinx.coroutines.*

class DatabaseActivity : AppCompatActivity(), DigimonDBAdapter.DeleteFavoritesClickListener {
    lateinit var binding:ActivityDatabaseBinding
    lateinit var adapter: DigimonDBAdapter
    private val dataBaseHelper by lazy { DataBaseHelper(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DigimonDBAdapter(arrayListOf<DigimonModel>(),this)
        binding.apply {
            digimonDbRv.adapter = adapter
        }

        getData()


    }

    private fun getData()
    {
        CoroutineScope(Dispatchers.IO).launch {

            val digimonList = async { getDatabaseDigimonList()}.await()
            populateRVData(digimonList)

        }
    }

    private fun getDatabaseDigimonList():List<DigimonModel>{

        return dataBaseHelper.readData()
    }

    private suspend fun populateRVData(digimonList: List<DigimonModel>){
        withContext(Dispatchers.Main){
            adapter.updateAdapterList(digimonList)
        }
    }

    override fun onClick(digimonsItem: DigimonModel) {
        CoroutineScope(Dispatchers.IO).launch {
            async { dataBaseHelper.deleteData(digimonsItem)}.await()
            getData()
        }
    }
}