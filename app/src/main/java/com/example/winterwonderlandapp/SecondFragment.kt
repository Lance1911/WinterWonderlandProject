package com.example.winterwonderlandapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genshintest.RecyclerAdapter
import com.example.winterwonderlandapp.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), RecyclerAdapter.onProductClickListener {

    private var _binding: FragmentSecondBinding? = null
    private val shoppingViewModel: ShoppingViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: ArrayList<PresentObject>? = shoppingViewModel.getShoppingList()

        binding.recyclerView.adapter = shoppingViewModel.getShoppingList()?.let {
            RecyclerAdapter(it, this)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        //binding.textviewSecond.text = shoppingViewModel.getShoppingList()?.size.toString()


        //binding.textviewSecond.text = results

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClickListener(position: Int) {
        shoppingViewModel.setSelectedProduct(shoppingViewModel.getShoppingList()?.get(position))
        findNavController().navigate(R.id.action_SecondFragment_to_productDetails)
    }
}