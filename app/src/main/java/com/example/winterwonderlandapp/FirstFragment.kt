package com.example.winterwonderlandapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.winterwonderlandapp.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val baseURL: String = "https://fakestoreapi.com"
    private val dropDownOptions: ArrayList<String> = arrayListOf("Electronics", "Jewelery", "Men's clothing","Women's clothing", "View Wishlist")
    private val avenueArray: ArrayList<String> = arrayListOf("electronics","jewelery","men's clothing","women's clothing", "wishlist")
    private var avenue: String? = ""
    private val shoppingViewModel: ShoppingViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setImageResource(R.drawable.img)

        val arrayAdapter = context?.let{
            ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, dropDownOptions)
        }


        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                avenue = avenueArray[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.buttonFirst.setOnClickListener {
            if (avenue.equals("wishlist")) {
                shoppingViewModel.getWishList()?.let { it1 -> shoppingViewModel.setShoppingList(it1) }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            }
            else {
                val statusString: String = "Retrieving $avenue..."
                binding.status.text = statusString
                val retrofit: Retrofit =
                    Retrofit.Builder().baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                val shoppingAPI = retrofit.create(ShoppingAPI::class.java)
                val call: Call<ArrayList<PresentObject>> = shoppingAPI.getGiftIdeas(avenue)
                call.enqueue(object : Callback<ArrayList<PresentObject>> {
                    override fun onResponse(
                        p0: Call<ArrayList<PresentObject>>,
                        p1: Response<ArrayList<PresentObject>>
                    ) {

                        val retrievedList: ArrayList<PresentObject>? = p1.body()
                        if (retrievedList != null) {
                            shoppingViewModel.setShoppingList(retrievedList)
                        }
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

                    }

                    override fun onFailure(p0: Call<ArrayList<PresentObject>>, p1: Throwable) {
                        p1.message?.let { Log.e("TAGGED", it) }
                    }

                })

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}