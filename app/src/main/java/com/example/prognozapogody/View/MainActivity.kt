package com.example.prognozapogody.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prognozapogody.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        Inicjalizacja elementów
        val fragmentManager = supportFragmentManager
        val simpleFragment = SimpleFragment()
        val detailedFragment = DetailedFragment()
        val changeButton : FloatingActionButton = findViewById(R.id.fab)
        var buttonStateChanged = false

//        Zmiana fragmentów
        fragmentManager.beginTransaction().add(R.id.fragment_container, simpleFragment).commit()
        changeButton.setOnClickListener {
            if (buttonStateChanged){
                fragmentManager.beginTransaction().replace(R.id.fragment_container, simpleFragment).commit()
                buttonStateChanged = false
            } else {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, detailedFragment).commit()
                buttonStateChanged = true
            }
        }

    }
}