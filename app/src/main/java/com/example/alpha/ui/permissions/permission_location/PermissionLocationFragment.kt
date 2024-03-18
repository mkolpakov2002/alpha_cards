package com.example.alpha.ui.permissions.permission_location

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.alpha.R
import com.example.alpha.databinding.FragmentPermissionLocationBinding
import com.example.alpha.rxpermissions3.RxPermissions
import com.google.android.material.button.MaterialButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable


class PermissionLocationFragment : Fragment() {
    private var binding: FragmentPermissionLocationBinding? = null
    private var materialButtonGrant: MaterialButton? = null
    private var rxPermissions: RxPermissions? = null
    private val disposables = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionLocationBinding.inflate(
            inflater,
            container,
            false
        )

        rxPermissions = RxPermissions(this)

        materialButtonGrant = binding!!.button

        materialButtonGrant!!.setOnClickListener{
            // Must be done during an initialization phase like onCreate
            disposables.add(rxPermissions!!
                .requestEachCombined(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { granted ->
                    if (!granted.granted) { // Always true pre-M
                        Toast.makeText(requireActivity(), "Разрешение не дано.", Toast.LENGTH_SHORT).show()
                    }
                    Navigation.findNavController(binding!!.root).navigate(R.id.navigation_home)
                })
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        binding = null
    }

//    override fun onClick(v: View?) {
//        if(checkIfCameraPermissionIsGranted(requireContext(), binding!!.root)){
//            Navigation.findNavController(binding!!.root).navigate(com.example.alpha.R.id.navigation_home)
//        }
//    }
}