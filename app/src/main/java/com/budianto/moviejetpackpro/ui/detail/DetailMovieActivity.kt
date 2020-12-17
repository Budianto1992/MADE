package com.budianto.moviejetpackpro.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.budianto.moviejetpackpro.R
import com.budianto.moviejetpackpro.core.data.Resource
import com.budianto.moviejetpackpro.core.domain.model.Movie
import com.budianto.moviejetpackpro.core.util.Constant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_movie.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private val detailViewModel: DetailMovieViewModel by viewModel()

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

//        val factory = ViewModelFactory.getInstance(this)
//        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        val detailMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (detailMovie != null){
            toolbar.title = detailMovie.title
            getDetailMovie(detailMovie.movieId)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
    }

    private fun getDetailMovie(movieId: Int){
        detailViewModel.detailMovie(movieId).observe(this, { detail ->
            if (detail != null){
                when(detail){
                    is Resource.Loading ->{
                        progress_bar.visibility = View.VISIBLE
                        container_detail_content.visibility = View.GONE
                    }
                    is Resource.Success ->{
                        showDetailMovie(detail.data)
                        progress_bar.visibility = View.GONE
                        container_detail_content.visibility = View.VISIBLE
                    }
                    is Resource.Error ->{
                        progress_bar.visibility = View.GONE
                        tv_error.text = detail.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailMovie(movie: Movie?){
        movie?.let {
            tv_original_name.text = movie.title
            tv_rating.text = "${movie.vote_average} Star (${movie.vote_count} people)"
            tv_release_date.text = movie.release_date
            tv_detail_description.text = movie.overview

            Glide.with(this@DetailMovieActivity)
                    .load(Constant.IMAGE_PATH + movie.poster_path)
                    .into(text_detail_image)

            var stateFavorite = movie.isMovieFavorite
            setStateFavorite(stateFavorite)
            fab.visibility = View.VISIBLE
            fab.setOnClickListener {
                stateFavorite = !stateFavorite
                detailViewModel.setFavoriteMovie(movie, stateFavorite)
                setStateFavorite(stateFavorite)
                val toastMessage = if (stateFavorite) "${movie.title} added to Favorite" else "${movie.title} deleted from Favorite"
                showToast(toastMessage)
            }
        }
    }

    private fun setStateFavorite(stateFavorite: Boolean){
        if (stateFavorite){
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else{
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
        }
    }

    private fun showToast(text: String = "", duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}