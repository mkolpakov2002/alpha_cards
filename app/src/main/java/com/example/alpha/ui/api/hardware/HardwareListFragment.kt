package com.example.alpha.ui.api.hardware

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.alpha.R
import com.example.alpha.data.api.BuildingItem
import com.example.alpha.data.api.DataScheme
import com.example.alpha.data.api.Hardware
import com.example.alpha.data.api.Item
import com.example.alpha.data.api.LabItem
import com.example.alpha.data.api.PlaceItem
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.Section
import com.example.alpha.data.api.Terminal
import com.example.alpha.ui.auth.AuthViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HardwareListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter<Hardware>
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private var jwtToken: String? = null

    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: HardwareListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        errorTextView = view.findViewById(R.id.errorTextView)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Оборудование"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        adapter = ItemAdapter(viewModel::onItemClick, viewModel::onItemLongClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

        authViewModel.authResult.observe(viewLifecycleOwner) { authResult ->
            jwtToken = authResult?.jwtToken
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) {
                Toast.makeText(requireContext(),"Здесь пока пусто!", Toast.LENGTH_SHORT).show()
            }
            adapter.submitList(items)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
            errorTextView.visibility = View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorTextView.text = errorMessage
            errorTextView.visibility = if (errorMessage.isNotBlank()) View.VISIBLE else View.GONE
            recyclerView.visibility = if (errorMessage.isNotBlank()) View.GONE else View.VISIBLE
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = getAuthToken()
        viewModel.refreshItems(token)
    }

    private fun getAuthToken(): String {
        return jwtToken ?: ""
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Navigation.findNavController(requireView()).navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class ItemAdapter<T : DataScheme>(
    private val onItemClick: (T) -> Unit,
    private val onItemLongClick: (T) -> Unit
) : ListAdapter<T, ItemAdapter<T>.ViewHolder>(ItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(item: T) {
            nameTextView.text = when (item) {
                is Hardware -> item.name
                is Item -> item.name
                is Terminal -> item.name
                is Section -> item.name
                is Room -> item.name
                is PlaceItem -> item.name
                is LabItem -> item.name
                is BuildingItem -> item.name
                else -> ""
            }

            iconImageView.setImageResource(getIconResId(item))

            itemView.setOnClickListener {
                onItemClick(item)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(item)
                true
            }
        }

        private fun getIconResId(item: T): Int {
            return when (item) {
                is Hardware -> R.drawable.ic_hardware
                is Item -> R.drawable.ic_item
                is Terminal -> R.drawable.ic_terminal
                is Section -> R.drawable.ic_section
                is Room -> R.drawable.ic_room
                is PlaceItem -> R.drawable.ic_place
                is LabItem -> R.drawable.ic_lab
                is BuildingItem -> R.drawable.ic_building
                else -> R.drawable.ic_default
            }
        }
    }
}

class ItemDiffCallback<T : DataScheme> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when {
            oldItem is Hardware && newItem is Hardware -> oldItem.id == newItem.id
            oldItem is Item && newItem is Item -> oldItem.id == newItem.id
            oldItem is Terminal && newItem is Terminal -> oldItem.id == newItem.id
            oldItem is Section && newItem is Section -> oldItem.id == newItem.id
            oldItem is Room && newItem is Room -> oldItem.id == newItem.id
            oldItem is PlaceItem && newItem is PlaceItem -> oldItem.id == newItem.id
            oldItem is LabItem && newItem is LabItem -> oldItem.id == newItem.id
            oldItem is BuildingItem && newItem is BuildingItem -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return when {
            oldItem is Hardware && newItem is Hardware ->
                oldItem.name == newItem.name &&
                        oldItem.type == newItem.type &&
                        oldItem.image_link == newItem.image_link &&
                        oldItem.specifications == newItem.specifications &&
                        oldItem.item_specifications_template == newItem.item_specifications_template

            oldItem is Item && newItem is Item ->
                oldItem.name == newItem.name &&
                        oldItem.inv_key == newItem.inv_key &&
                        oldItem.hardware == newItem.hardware &&
                        oldItem.room == newItem.room &&
                        oldItem.status == newItem.status &&
                        oldItem.owner == newItem.owner &&
                        oldItem.place == newItem.place &&
                        oldItem.available == newItem.available &&
                        oldItem.specifications == newItem.specifications

            oldItem is Terminal && newItem is Terminal ->
                oldItem.name == newItem.name

            oldItem is Section && newItem is Section ->
                oldItem.name == newItem.name &&
                        oldItem.description == newItem.description &&
                        oldItem.room == newItem.room

            oldItem is Room && newItem is Room ->
                oldItem.name == newItem.name &&
                        oldItem.lab == newItem.lab &&
                        oldItem.building == newItem.building &&
                        oldItem.type == newItem.type

            oldItem is PlaceItem && newItem is PlaceItem ->
                oldItem.name == newItem.name &&
                        oldItem.description == newItem.description &&
                        oldItem.section == newItem.section

            oldItem is LabItem && newItem is LabItem ->
                oldItem.name == newItem.name

            oldItem is BuildingItem && newItem is BuildingItem ->
                oldItem.name == newItem.name &&
                        oldItem.address == newItem.address

            else -> false
        }
    }
}