package com.example.winterwonderlandapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genshintest.RecyclerAdapter
import com.example.winterwonderlandapp.databinding.ProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetails: Fragment() {
    private var _binding: ProductDetailsBinding? = null
    private val shoppingViewModel: ShoppingViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ProductDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!shoppingViewModel.getItemStatus(shoppingViewModel.getSelectedProduct())) {
            binding.wishListButton.text = "Add To WishList"
        }
        else {
            binding.wishListButton.text = "Remove From Wishlist"
        }
        val category = shoppingViewModel.getSelectedProduct()?.category
        val rating = shoppingViewModel.getSelectedProduct()?.rating?.rate
        val description = shoppingViewModel.getSelectedProduct()?.description
        val price = shoppingViewModel.getSelectedProduct()?.price.toString()
        val imageUrl = shoppingViewModel.getSelectedProduct()?.image

        binding.productTitle.text = shoppingViewModel.getSelectedProduct()?.title
        binding.category.text = "Category: $category"
        binding.rating.text = "Rating: $rating"
        binding.price.text = "Price: $$price"
        binding.description.text = "Description: $description"

        Picasso.get().load(imageUrl).into(binding.imageUrl)

        binding.wishListButton.setOnClickListener {
            if (!shoppingViewModel.getItemStatus(shoppingViewModel.getSelectedProduct())) {
                binding.wishListButton.text = "Remove From Wishlist"
                shoppingViewModel.addToWishList(shoppingViewModel.getSelectedProduct())
            }
            else {
                binding.wishListButton.text = "Add To Wishlist"
                shoppingViewModel.removeFromWishList(shoppingViewModel.getSelectedProduct())
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}