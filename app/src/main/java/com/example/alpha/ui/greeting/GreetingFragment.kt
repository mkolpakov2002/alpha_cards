package com.example.alpha.ui.greeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.alpha.R
import com.example.alpha.databinding.FragmentGreetingBinding
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.checkIfAuthIsGranted
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.checkIfCameraPermissionIsGranted
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.checkIfLocationPermissionIsGranted
import com.google.android.material.button.MaterialButton

class GreetingFragment : Fragment() {

    private lateinit var binding: FragmentGreetingBinding
    private lateinit var materialButtonNext: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGreetingBinding.inflate(
            inflater,
            container,
            false
        )

        materialButtonNext = binding.button

        materialButtonNext.setOnClickListener{
            if(!checkIfCameraPermissionIsGranted())
                Navigation.findNavController(binding.root).navigate(R.id.permissionCameraFragment)
            else if(!checkIfLocationPermissionIsGranted())
                Navigation.findNavController(binding.root).navigate(R.id.permissionLocationFragment)
            else if(!checkIfAuthIsGranted())
                Navigation.findNavController(binding.root).navigate(R.id.authFragment)
            else
                Navigation.findNavController(binding.root).navigate(R.id.navigation_home)
        }

        return binding.root
    }
}