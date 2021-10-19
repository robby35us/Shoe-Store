package com.udacity.shoestore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentListBinding
import com.udacity.shoestore.databinding.ListItemAccentBinding
import com.udacity.shoestore.databinding.ListItemNoneBinding
import com.udacity.shoestore.databinding.ListItemPrimaryBinding
import com.udacity.shoestore.models.Shoe

class ListFragment : Fragment() {


    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel : ShoeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        binding.floatingActionButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }
        viewModel = ViewModelProvider(requireActivity()).get(ShoeViewModel::class.java)

        var usePrimaryBinding = true

        viewModel.shoeList.observe(viewLifecycleOwner) { list ->
            binding.shoeList.removeAllViewsInLayout()
            if(list.isEmpty()) {
                binding.shoeList.addView(
                    DataBindingUtil.inflate<ListItemNoneBinding>(
                        inflater, R.layout.list_item_none, container, false
                    ).root
                )
            } else {
                for (shoe: Shoe in list) {
                    if (usePrimaryBinding) {
                        val listItemBinding = DataBindingUtil.inflate<ListItemPrimaryBinding>(
                            inflater, R.layout.list_item_primary, container, false
                        )
                        listItemBinding.shoeData = shoe
                        binding.shoeList.addView(listItemBinding.root)
                        usePrimaryBinding = false
                    } else {
                        val listItemBinding = DataBindingUtil.inflate<ListItemAccentBinding>(
                            inflater, R.layout.list_item_accent, container, false
                        )
                        listItemBinding.shoeData = shoe
                        binding.shoeList.addView(listItemBinding.root)
                        usePrimaryBinding = true
                    }
                }
            }
        }
        return binding.root
    }
}