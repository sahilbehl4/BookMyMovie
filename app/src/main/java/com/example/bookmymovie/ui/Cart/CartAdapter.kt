package com.example.bookmymovie.ui.Cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bookmymovie.R

//class CartAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private val cartItems: ArrayList<CartModel>
//    private val context: Context
//
//    constructor(
//        context: Context,
//        cartItems: ArrayList<CartModel>
//    ) : super() {
//        this.cartItems = cartItems
//        this.context = context
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
//        return MyViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val viewHolder = holder as MyViewHolder
//        viewHolder.movieName.setText(this.cartItems[position].movieName)
//        viewHolder.countView.setText(this.cartItems[position].numberOfTickets)
//        viewHolder.priceView.setText(this.cartItems[position].totalPrice)
//    }
//
//    override fun getItemCount(): Int {
//        val size = cartItems.size
//        return size
//    }
//
//    class MyViewHolder : RecyclerView.ViewHolder {
//        val movieName: TextView
//        val countView: TextView
//        val priceView: TextView
//
//        constructor(view: View): super(view) {
//            movieName = view.findViewById<TextView>(R.id.movie_name)
//            countView = view.findViewById<TextView>(R.id.total_count)
//            priceView = view.findViewById<TextView>(R.id.total_price)
//        }
//    }
//}

class CartAdapter : BaseAdapter {
    private val cartItems: ArrayList<CartModel>
    private val context: Context

    constructor(
        context: Context,
        cartItems: ArrayList<CartModel>
    ) : super() {
        this.cartItems = cartItems
        this.context = context
    }

    override fun getCount(): Int {
        val size = cartItems.size
        return size
    }

    override fun getItem(p0: Int): Any {
        return Any()
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, null, false)
        val movieName = view.findViewById<TextView>(R.id.movie_name)
        val countView = view.findViewById<TextView>(R.id.count)
        val priceView = view.findViewById<TextView>(R.id.total_price)

        movieName.setText(this.cartItems[p0].movieName)
        countView.setText("x" + this.cartItems[p0].numberOfTickets.toString())
        priceView.setText("Total Price: " + this.cartItems[p0].totalPrice)

        return view
    }

}