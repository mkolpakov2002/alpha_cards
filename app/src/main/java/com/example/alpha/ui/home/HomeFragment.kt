package com.example.alpha.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.alpha.R
import com.example.alpha.api.ApiObject
import com.example.alpha.data.api.AuthResponse
import com.example.alpha.data.api.Hardware
import com.example.alpha.data.api.Item
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.Section
import com.example.alpha.data.api.Terminal
import com.example.alpha.data.api.User
import com.example.alpha.databinding.FragmentHomeBinding
import com.example.alpha.ui.auth.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val authViewModel: AuthViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: RoomAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    var jwtToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        swipeRefreshLayout = binding.swipeRefreshLayout

        adapter = RoomAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        authViewModel.authResult.observe(viewLifecycleOwner) { authResult ->
            jwtToken = authResult?.jwtToken
            // Сохраните JWT-токен для дальнейших запросов
        }

        homeViewModel.rooms.observe(viewLifecycleOwner) { rooms ->
            adapter.updateData(rooms)
        }

        swipeRefreshLayout.setOnRefreshListener {
            jwtToken?.let { jwtToken ->
                homeViewModel.refreshRooms(jwtToken)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            swipeRefreshLayout.isRefreshing = isLoading
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showErrorMessage(errorMessage)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        jwtToken?.let { jwtToken ->
            homeViewModel.refreshRooms(jwtToken)
        }
        if(!authViewModel.isAuthIsGranted()) {
            Navigation.findNavController(requireView()).navigate(R.id.authFragment)
        }
    }

    private fun showErrorMessage(message: String) {
        val snackbar = Snackbar.make(binding.swipeRefreshLayout, message,
            Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView: TextView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.BLUE)
        textView.textSize = 8f
        snackbar.show()
    }
}

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {
    private var rooms: List<Room> = emptyList()

    fun updateData(newRooms: List<Room>) {
        rooms = newRooms
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val detailsTextView: TextView = itemView.findViewById(R.id.detailsTextView)

        fun bind(room: Room) {
            nameTextView.text = room.name
            detailsTextView.text = "Type: ${room.type}"
        }
    }
}