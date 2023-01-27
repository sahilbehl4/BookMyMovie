package com.example.bookmymovie.ui.Movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bookmymovie.R

class MoviesAdapter : BaseAdapter {
    private val moviesName: Array<String>
    private val moviesImageName: Array<Int>
    private val context: Context

    constructor(
        context: Context,
        moviesName: Array<String>,
        moviesImageName: Array<Int>
    ) : super() {
        this.moviesName = moviesName
        this.moviesImageName = moviesImageName
        this.context = context
    }

    override fun getCount(): Int {
        val size = moviesName.size
        return size
    }

    override fun getItem(p0: Int): Any {
        return Any()
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_grid_item, null, false)
        val imageView = view?.findViewById<ImageView>(R.id.movie_item_image)
        val textView = view?.findViewById<TextView>(R.id.movie_item_name)

        imageView?.setImageResource(this.moviesImageName[p0])
        textView?.setText(this.moviesName[p0])

        return view
    }
}