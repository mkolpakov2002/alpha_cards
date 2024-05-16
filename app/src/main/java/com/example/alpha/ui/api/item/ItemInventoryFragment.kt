package com.example.alpha.ui.api.item

import android.content.Intent
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
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.alpha.R
import com.example.alpha.data.api.Item
import com.example.alpha.ui.auth.AuthViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.md.LiveBarcodeScanningFragment
import java.io.File

class ItemInventoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter
    private lateinit var scanFab: ExtendedFloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var inventoryFab: ExtendedFloatingActionButton
    private var jwtToken: String? = null

    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: ItemInventoryViewModel by viewModels()

    private var placeId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_inventory, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        scanFab = view.findViewById(R.id.scanFab)
        progressBar = view.findViewById(R.id.progressBar)
        errorTextView = view.findViewById(R.id.errorTextView)
        inventoryFab = view.findViewById(R.id.inventoryFab)

        placeId = arguments?.getInt("placeId", -1000)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Инвентаризация"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        adapter = InventoryAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        scanFab.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_itemInventoryFragment_to_liveBarcodeScanningFragment)
        }

        inventoryFab.setOnClickListener {
            generateInventoryReport()
        }

        authViewModel.authResult.observe(viewLifecycleOwner) { authResult ->
            jwtToken = authResult?.jwtToken
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            if(placeId != null && placeId != -1000) {
                items.filter { it.place == placeId }
            }
            adapter.submitList(items)
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), "Здесь пока пусто!", Toast.LENGTH_SHORT).show()
            }
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

        setupBarcodeResultListener()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = getAuthToken()
        viewModel.refreshItems(token)
    }

    override fun onResume() {
        super.onResume()
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

    private fun setupBarcodeResultListener() {
        setFragmentResultListener(LiveBarcodeScanningFragment.REQUEST_KEY) { _, bundle ->
            val scannedInvKey = bundle.getString("barcode")
            scannedInvKey?.let {
                viewModel.markItemAsScanned(it)
            }
        }
    }

    private fun generateInventoryReport() {
        val items = viewModel.items.value ?: emptyList()
        val scannedItems = items.filter { viewModel.isItemScanned(it) }
        val unscannedItems = items.filter { !viewModel.isItemScanned(it) }

        val placeName = items.firstOrNull()?.place ?: "Unknown Place"

        val reportBuilder = StringBuilder()
        reportBuilder.append("Отчет об инвентаризации\n\n")
        reportBuilder.append("Место проведения: $placeName\n")
        reportBuilder.append("Всего предметов: ${items.size}\n")
        reportBuilder.append("Найдено предметов: ${scannedItems.size}\n")
        reportBuilder.append("Не найдено предметов: ${unscannedItems.size}\n\n")

        reportBuilder.append("Список предметов:\n")
        items.forEach { item ->
            val status = if (viewModel.isItemScanned(item)) "Найден" else "Не найден"
            reportBuilder.append("- ${item.name} (Инв. номер: ${item.inv_key}): $status\n")
        }

        val reportText = reportBuilder.toString()

        // Сохранение отчета в памяти устройства
//        saveReportToDevice(reportText)
//
//        // Отправка отчета (например, по электронной почте)
        sendReportByEmail(reportText)
    }

    private fun saveReportToDevice(reportText: String) {
        val fileName = "inventory_report_${System.currentTimeMillis()}.txt"
        val file = File(requireContext().getExternalFilesDir(null), fileName)
        file.writeText(reportText)
        Toast.makeText(requireContext(), "Отчет сохранен в файл: $fileName", Toast.LENGTH_SHORT).show()
    }

    private fun sendReportByEmail(reportText: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Отчет об инвентаризации")
            putExtra(Intent.EXTRA_TEXT, reportText)
        }
        startActivity(Intent.createChooser(intent, "Отправить отчет"))
    }
}

class InventoryAdapter(private val viewModel: ItemInventoryViewModel) :
    ListAdapter<Item, InventoryAdapter.ViewHolder>(ItemDiffCallback<Item>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val invKeyTextView: TextView = itemView.findViewById(R.id.invKeyTextView)
        private val scannedImageView: ImageView = itemView.findViewById(R.id.scannedImageView)
        private val resultTextView: TextView = itemView.findViewById(R.id.resultTextView)

        fun bind(item: Item) {
            nameTextView.text = item.name
            invKeyTextView.text = "Инв. номер: ${item.inv_key}"

            val isScanned = viewModel.isItemScanned(item)
            scannedImageView.visibility = if (isScanned) View.VISIBLE else View.GONE
            resultTextView.visibility = if (isScanned) View.VISIBLE else View.GONE

            iconImageView.setImageResource(R.drawable.ic_item)
        }
    }
}