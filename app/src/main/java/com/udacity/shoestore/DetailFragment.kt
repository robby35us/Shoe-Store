package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel : ShoeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.saveButton.isClickable = false

        viewModel = ViewModelProvider(requireActivity()).get(ShoeViewModel::class.java)
        viewModel.invalidShoeSize.observe(viewLifecycleOwner) { isInvalid ->
            if(isInvalid) {
                Toast.makeText(activity, "Please enter a valid shoe size", Toast.LENGTH_SHORT).show()
                viewModel.onInvalidSizeCompleted()
            }
        }

        viewModel.shoeDataComplete.observe(viewLifecycleOwner) { dataComplete ->
            binding.saveButton.isClickable = dataComplete
        }

        binding.cancelButton.setOnClickListener{
            viewModel.clearShoeData()
            binding.saveButton.isClickable = false
            it.findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener{
            viewModel.addShoeToList()
            Toast.makeText(activity, R.string.stash_upgraded, Toast.LENGTH_SHORT).show()
            viewModel.clearShoeData()
            it.findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shoeViewModel = viewModel
        binding.lifecycleOwner = this

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onResume() {
        super.onResume()
        binding.saveButton.isClickable = viewModel.shoeDataComplete.value!!
    }
}