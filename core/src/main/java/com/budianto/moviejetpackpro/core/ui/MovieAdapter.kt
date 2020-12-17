package com.budianto.moviejetpackpro.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.budianto.moviejetpackpro.core.R
import com.budianto.moviejetpackpro.core.domain.model.Movie
import com.budianto.moviejetpackpro.core.util.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var listMovies = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setMovies(movies: List<Movie>?){
        if (movies == null) return
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
//            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//                return oldItem.movieId == newItem.movieId
//            }
//            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))


    override fun getItemCount() = listMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bindTo(movie)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindTo(movie: Movie){
            with(itemView){
                tv_item_title.text = movie.title
                tv_vote_average.text = "Rating: ${movie.vote_average}"
                Glide.with(context)
                    .load(Constant.IMAGE_PATH + movie.poster_path)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                    )
                    .into(iv_item_image)
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listMovies[adapterPosition])
            }
        }
    }
}