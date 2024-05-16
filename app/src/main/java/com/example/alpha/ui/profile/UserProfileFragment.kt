package com.example.alpha.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.alpha.R
import com.example.alpha.data.Repository
import com.example.alpha.data.api.User
import com.example.alpha.databinding.FragmentUserProfileBinding
import com.example.alpha.ui.auth.AuthViewModel
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.setAuthIsGranted
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var materialButtonChangeProfile: MaterialButton

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var userTypeTextView: TextView

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Настройки профиля"

        nameTextView = binding.nameTextView
        emailTextView = binding.emailTextView
        phoneTextView = binding.phoneTextView
        userTypeTextView = binding.userTypeTextView

        materialButtonChangeProfile = binding.materialButtonChangeProfile

        materialButtonChangeProfile.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                setAuthIsGranted(false)
                authViewModel.setAuthResult(null)
//                Navigation.findNavController(it).navigate(R.id.authFragment)
            }
        }

        authViewModel.authResult.observe(viewLifecycleOwner) { authResult ->
            if (authResult != null) {
                val jwtToken = authResult.jwtToken
                val user = authResult.user
                updateUserProfile(user)
            }
            // Сохраните JWT-токен для дальнейших запросов
        }

        return root
    }


    fun updateUserProfile(user: User) {
        nameTextView.text = user.name
        emailTextView.text = user.email
        phoneTextView.text = user.phone ?: "Без номера телефона"

        val userType = when (user.user_type) {
            1 -> "Студент"
            2 -> "Сотрудник"
            else -> "Администратор"
        }
        userTypeTextView.text = "Тип пользователя: $userType"
    }

}