package com.example.alpha.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.alpha.R
import com.example.alpha.data.Repository
import com.example.alpha.data.api.AuthResult
import com.example.alpha.databinding.FragmentHomeBinding
import com.example.alpha.ui.auth.AuthViewModel
import com.example.alpha.ui.permissions.permission.PermissionManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val authViewModel: AuthViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeApiItemAdapter
    private lateinit var recyclerView: RecyclerView

    var jwtToken: String? = null

    private lateinit var floatingActionButton: ExtendedFloatingActionButton

    val homeApiItems = listOf<HomeApiItem>(
        HomeApiItem(name = "Здания",
            description = "Информация о зданиях",
            imageUrl = R.drawable.ic_building),
        HomeApiItem(name = "Лаборатории",
            description = "Информация о лабораториях",
            imageUrl = R.drawable.ic_lab),
        HomeApiItem(name = "Ячейки хранения",
            description = "Информация об ячейках хранения (полках)",
            imageUrl = R.drawable.ic_place),
        HomeApiItem(name = "Комнаты",
            description = "Информация о комнатах (умных шкафах)",
            imageUrl = R.drawable.ic_room),
        HomeApiItem(name = "Стеллажи",
            description = "Информация о стеллажах",
            imageUrl = R.drawable.ic_section),
        HomeApiItem(name = "Терминалы",
            description = "Информация о терминалах",
            imageUrl = R.drawable.ic_terminal),
        HomeApiItem(name = "Предметы",
            description = "Информация о предметах",
            imageUrl = R.drawable.ic_item),
        HomeApiItem(name = "Оборудование",
            description = "Информация об оборудовании",
            imageUrl = R.drawable.ic_hardware)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Домашняя страница"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView

        adapter = HomeApiItemAdapter { position ->
            val item = homeApiItems[position]
            navigateToFragment(binding.root, item)
        }
        recyclerView.adapter = adapter

        adapter.updateData(homeApiItems)

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showErrorMessage(errorMessage)
            }
        }
    }

    private fun navigateToFragment(view: View, item: HomeApiItem) {
        when (item.name) {
            homeApiItems[0].name -> Navigation.findNavController(view).navigate(R.id.buildingListFragment)
            homeApiItems[1].name -> Navigation.findNavController(view).navigate(R.id.labListFragment)
            homeApiItems[2].name -> Navigation.findNavController(view).navigate(R.id.placeListFragment)
            homeApiItems[3].name -> Navigation.findNavController(view).navigate(R.id.roomListFragment)
            homeApiItems[4].name -> Navigation.findNavController(view).navigate(R.id.sectionListFragment)
            homeApiItems[5].name -> Navigation.findNavController(view).navigate(R.id.terminalListFragment)
            homeApiItems[6].name -> Navigation.findNavController(view).navigate(R.id.itemListFragment)
            homeApiItems[7].name -> Navigation.findNavController(view).navigate(R.id.hardwareListFragment)
            else -> return
        }
    }

    override fun onResume() {
        super.onResume()
        if(!authViewModel.isAuthIsGranted()) {
            val token = PermissionManager.getToken()
            if (token.isNullOrEmpty())
//                Navigation.findNavController(requireView()).navigate(R.id.authFragment)
            else {
                viewLifecycleOwner.lifecycleScope.launch {
                    val user = Repository.getInstance(token).getUser()
                    val authResult = AuthResult(token, user)
                    authViewModel.setAuthResult(authResult)
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        val snackbar = Snackbar.make(binding.root, message,
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

class HomeApiItemAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<HomeApiItemAdapter.HomeViewHolder>() {
    private var rooms: List<HomeApiItem> = emptyList()

    fun updateData(newRooms: List<HomeApiItem>) {
        rooms = newRooms
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_home_card, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val detailsTextView: TextView = itemView.findViewById(R.id.detailsTextView)
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)

        fun bind(room: HomeApiItem) {
            nameTextView.text = room.name
            detailsTextView.text = room.description
            iconImageView.setImageResource(room.imageUrl)
        }
    }
}