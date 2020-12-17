package com.budianto.moviejetpackpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView = this.findNavController(R.id.nav_host_fragment_container)
        NavigationUI.setupActionBarWithNavController(this, navView)

        to_favorite.setOnClickListener {
            moveToFavorite()
        }
    }

    private fun moveToFavorite(){
        try {
            val intent = Class.forName("com.budianto.moviejatpackpro.favorite.FavoriteActivity")
            startActivity(Intent(this, intent))
        } catch (e: Exception){
            Toast.makeText(this, "Tidak dapat membuka module", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navView = findNavController(R.id.nav_host_fragment_container)
        return navView.navigateUp()
    }
}