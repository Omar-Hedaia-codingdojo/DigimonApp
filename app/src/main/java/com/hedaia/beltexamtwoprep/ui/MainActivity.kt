package com.hedaia.beltexamtwoprep.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hedaia.beltexamtwoprep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            apiActivityBtn.setOnClickListener {

                val intent = Intent(this@MainActivity, ApiActivity::class.java)
                startActivity(intent)

            }

            browseDbActivityBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, DatabaseActivity::class.java)
                startActivity(intent)

            }

        }



    }
}