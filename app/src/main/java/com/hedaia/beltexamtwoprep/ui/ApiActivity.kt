package com.hedaia.beltexamtwoprep.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hedaia.beltexamtwoprep.db.DataBaseHelper
import com.hedaia.beltexamtwoprep.adapters.DigimonApiAdapter
import com.hedaia.beltexamtwoprep.models.DigimonsItem
import com.hedaia.beltexamtwoprep.databinding.ActivityApiBinding
import kotlinx.coroutines.*
import org.json.JSONArray
import java.lang.Exception
import java.net.URL

class ApiActivity : AppCompatActivity(), DigimonApiAdapter.FavoritesClickListener {
    private val TAG = "ApiActivity"
    lateinit var binding:ActivityApiBinding
    lateinit var adapter: DigimonApiAdapter
    private val dataBaseHelper by lazy { DataBaseHelper(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DigimonApiAdapter(arrayListOf<DigimonsItem>(),this)
        binding.apply {

            apiRv.adapter = adapter

        }
        requestAPI()


    }

    fun requestAPI()
    {
        CoroutineScope(Dispatchers.IO).launch {
             val data:String = async { fetchData() }.await()

            if(data.isNotEmpty())
            {
                populateRv(data)
            }else{
                Log.d(TAG, "requestAPI: Unable to retrieve data from the server")
            }

        }
    }

    private fun fetchData():String {

        var response = ""
        try {
            response = URL("https://digimon-api.vercel.app/api/digimon").readText()
        }catch (e:Exception){
            Log.d(TAG, "fetchData: issue $e")
        }

        return response

    }

    private suspend fun populateRv(data: String) {

        withContext(Dispatchers.Main){

            val digimonList = arrayListOf<DigimonsItem>()
            val jsonArray = JSONArray(data)
            for (i in 0 until jsonArray.length())
            {
                val name = jsonArray.getJSONObject(i).getString("name")
                val img = jsonArray.getJSONObject(i).getString("img")
                val level = jsonArray.getJSONObject(i).getString("level")
                digimonList.add(DigimonsItem(img,level,name))
            }
            adapter.updateAdapterList(digimonList)




        }
    }

    override fun onClick(digimonsItem: DigimonsItem) {

        dataBaseHelper.saveData(digimonsItem.name,digimonsItem.img,digimonsItem.level)
    }

}