package com.budianto.moviejetpackpro.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.budianto.moviejetpackpro.R
import com.budianto.moviejetpackpro.core.data.Resource
import com.budianto.moviejetpackpro.core.ui.MovieAdapter
import com.budianto.moviejetpackpro.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {


    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null){
//            val factory = ViewModelFactory.getInstance(requireActivity())
//            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData)
                startActivity(intent)
            }

            movieViewModel.movie.observe(viewLifecycleOwner, { movies ->
                if (movies != null){
                    when(movies){
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success ->{
                            progress_bar.visibility = View.GONE
                            movieAdapter.setMovies(movies.data)
                            movieAdapter.notifyDataSetChanged()
                        }
                        is Resource.Error ->{
                            progress_bar.visibility = View.GONE
                            view_error.visibility = View.GONE
                            tv_error.text = movies.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            })

            with(rv_movie){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}