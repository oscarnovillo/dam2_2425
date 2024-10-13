package com.example.viewmodel.ui.pantalllamain

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.viewmodel.R

import com.example.viewmodel.databinding.ActivityMainBinding
import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.ui.pantalladetalle.DetalleActivity

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        events()
    }

    private fun events() {

        binding.button2.setOnClickListener{
            val intent =  Intent(this, DetalleActivity::class.java)
            intent.putExtra("id",0)

            intent.putExtra(getString(R.string.persona), Persona("nombre","apellidos"))

            startActivity(intent)

        }
    }
}