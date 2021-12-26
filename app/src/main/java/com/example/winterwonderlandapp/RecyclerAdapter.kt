package com.example.genshintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.winterwonderlandapp.R
import com.example.winterwonderlandapp.PresentObject
import com.example.winterwonderlandapp.SecondFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.shopping_item.view.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private val recyclerList: ArrayList<PresentObject>, private val listener: SecondFragment):
    RecyclerView.Adapter<RecyclerAdapter.recyclerViewHolder>() {

    private var filterList: ArrayList<PresentObject> = ArrayList(recyclerList)

    interface onProductClickListener {
        fun onProductClickListener(position: Int) {

        }
    }
    inner class recyclerViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val productName = view.productName
        val imageLink = view.productImage
        val prodPrice = view.productPrice
        val rating = view.productRating

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onProductClickListener(position)
            }
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerAdapter.recyclerViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.shopping_item, p0, false)
        return recyclerViewHolder(view)
    }

    override fun onBindViewHolder(p0: RecyclerAdapter.recyclerViewHolder, p1: Int) {
        val productItem = recyclerList[p1]
        p0.productName.text = productItem.title.capitalize()
        val priceString = productItem.price.toString()
        val rateString = productItem.rating.rate.toString()
        p0.prodPrice.text = "$$priceString"
        p0.rating.text = "Rating: $rateString"


        Picasso.get().load(productItem.image).into(p0.imageLink)


    }

    override fun getItemCount(): Int {
        return recyclerList.size
    }




}