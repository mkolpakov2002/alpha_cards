package com.example.alpha.ui.api.place

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
import com.example.alpha.ui.api.room.ItemDiffCallback
import com.example.alpha.ui.api.room.RoomListViewModel
import com.example.alpha.ui.api.section.SectionListFragmentDirections
import com.example.alpha.ui.auth.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PlaceListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var editFab: FloatingActionButton
    private lateinit var deleteFab: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private var jwtToken: String? = null

    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: PlaceListViewModel by viewModels()

    private var sectionId: Int? = null
    private var roomId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        sectionId = arguments?.getInt("sectionId", -1000)
        roomId = arguments?.getInt("roomId", -1000)

        recyclerView = view.findViewById(R.id.recyclerView)
        editFab = view.findViewById(R.id.editFab)
        deleteFab = view.findViewById(R.id.deleteFab)
        progressBar = view.findViewById(R.id.progressBar)
        errorTextView = view.findViewById(R.id.errorTextView)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Ячейки хранения"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        adapter = ItemAdapter(this::onItemClick, this::onItemLongClick, viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        editFab.setOnClickListener {
            viewModel.onEditItemClick()
        }

        deleteFab.setOnClickListener {
            viewModel.onDeleteItemClick(jwtToken ?: "")
        }

        authViewModel.authResult.observe(viewLifecycleOwner) { authResult ->
            jwtToken = authResult?.jwtToken
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            if (items.isEmpty()) {
                Toast.makeText(requireContext(),"Здесь пока пусто!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.selectedItemCount.observe(viewLifecycleOwner) { count ->
            editFab.visibility = if (count == 1) View.VISIBLE else View.GONE
            deleteFab.visibility = if (count > 0) View.VISIBLE else View.GONE
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

    private fun onItemLongClick(placeItem: PlaceItem) {

    }

    private fun onItemClick(placeItem: PlaceItem) {
        val action = PlaceListFragmentDirections.actionPlaceListFragmentToItemListFragment(placeItem.id, roomId?: -1000)
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = getAuthToken()
        viewModel.refreshItems(token, sectionId)
    }

    override fun onResume() {
        super.onResume()
        val token = getAuthToken()
        viewModel.refreshItems(token, sectionId)
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

class ItemAdapter(
    private val onItemClick: (PlaceItem) -> Unit,
    private val onItemLongClick: (PlaceItem) -> Unit,
    private val viewModel: PlaceListViewModel
) : ListAdapter<PlaceItem, ItemAdapter.ViewHolder>(ItemDiffCallback<PlaceItem>()) {

    private val expandedPositions = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val createdTextView: TextView = itemView.findViewById(R.id.createdTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val sectionTextView: TextView = itemView.findViewById(R.id.sectionTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val button: MaterialButton = itemView.findViewById(R.id.button)

        fun bind(item: PlaceItem, position: Int) {
            nameTextView.text = item.name

            val isExpanded = expandedPositions.contains(position)

            if (isExpanded) {

                val sectionText = viewModel.sections.value?.find { it.id == item.section }?.name

                createdTextView.text = "Создано " + item.created
                createdTextView.visibility = View.VISIBLE
                idTextView.text = "Id " + item.id.toString()
                idTextView.visibility = View.VISIBLE
                sectionTextView.text = "Секция: " + sectionText
                sectionTextView.visibility = View.VISIBLE
                descriptionTextView.text = "Описание: " + item.description
                descriptionTextView.visibility = View.VISIBLE
                button.text = "Предметы →"
                button.visibility = View.VISIBLE
            } else {
                createdTextView.visibility = View.GONE
                idTextView.visibility = View.GONE
                descriptionTextView.visibility = View.GONE
                sectionTextView.visibility = View.GONE
                button.visibility = View.GONE
            }

            iconImageView.setImageResource(getIconResId())

            itemView.setOnClickListener {
                if (isExpanded) {
                    expandedPositions.remove(position)
                } else {
                    expandedPositions.add(position)
                }
                notifyItemChanged(position)
            }

            button.setOnClickListener {
                onItemClick(item)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(item)
                true
            }
        }

        private fun getIconResId(): Int {
            return R.drawable.ic_place
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