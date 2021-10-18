package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentInstructionsBinding

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentInstructionsBinding>(
            inflater, R.layout.fragment_instructions, container, false)
        binding.nopeButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_instructionsFragment_to_welcomeFragment)
        }
        binding.yepButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_instructionsFragment_to_listFragment)
        }
        return binding.root
    }
}