package com.budianto.moviejatpackpro.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.budianto.moviejetpackpro.core.ui.MovieAdapter
import com.budianto.moviejetpackpro.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        loadKoinModules(favoriteMovieModule)

        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_favorite_close_24)
        getFavoriteData()
    }

    private fun getFavoriteData(){
        val movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        favoriteMovieViewModel.favoriteMovie.observe(this, { favorites ->
            movieAdapter.setMovies(favorites)
            view_empty.visibility = if (favorites.isNotEmpty()) View.GONE else View.VISIBLE
        })

        with(rv_favorite_movie){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}