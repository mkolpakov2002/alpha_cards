package com.example.alpha.ui.api.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.alpha.R
import com.example.alpha.data.api.Hardware
import com.example.alpha.data.api.Item
import com.example.alpha.databinding.FragmentItemBinding
import com.example.alpha.ui.auth.AuthViewModel
import com.google.mlkit.md.LiveBarcodeScanningFragment
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

class ItemFragment : Fragment() {

    private lateinit var binding: FragmentItemBinding
    private val viewModel: ItemViewModel by viewModels()
    private var itemId: Int = -1000
    private var placeId: Int = -1000
    private var roomId: Int = -1000
    private val authViewModel: AuthViewModel by activityViewModels()
    private var isNew = true
    private var hardwareList: List<Hardware> = emptyList()

    private var jwtToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(inflater, container, false)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.authResult.observe(viewLifecycleOwner) { authResult ->
            jwtToken = authResult?.jwtToken
        }

        itemId = arguments?.getInt("itemId", -1000) ?: -1000

        if (itemId != -1000) {
            viewModel.getItem(getAuthToken(), itemId)
            isNew = false
        }

        placeId = arguments?.getInt("placeId", -1000) ?: -1000
        roomId = arguments?.getInt("roomId", -1000) ?: -1000
        fetchHardwareList()
        setupObservers()
        setupListeners()
        setupBarcodeResultListener()
    }

    private fun fetchHardwareList() {
        viewModel.getHardwareList(getAuthToken())
    }

    private fun setupObservers() {
        binding.etPlace.isEnabled = false
        if(!isNew) {
            binding.etName.isEnabled = false
            binding.etInvKey.isEnabled = false
            binding.etHardware.isEnabled = false
            binding.etGroup.isEnabled = false
            binding.etStatus.isEnabled = false
            binding.etOwner.isEnabled = false
            binding.cbAvailable.isEnabled = false
            binding.btnSave.visibility = View.GONE
            binding.btnScan.visibility = View.INVISIBLE
            binding.btnAddSpecification.visibility = View.GONE
            binding.btnHardware.visibility = View.GONE
        } else {
            if (placeId != -1000 && roomId != -1000) {
                binding.etPlace.setText(placeId.toString())
                binding.etGroup.setText(roomId.toString())
            } else {
                binding.etPlace.setText("")
                binding.etGroup.setText("")
            }
            viewModel.hardwareList.observe(viewLifecycleOwner) { list ->
                hardwareList = list
            }
        }
        viewModel.item.observe(viewLifecycleOwner) { item ->
            item?.let {
                binding.etName.setText(item.name)
                binding.etInvKey.setText(item.inv_key)
                binding.etHardware.setText(item.hardware.toString())
                binding.etGroup.setText(item.room?.toString() ?: "")
                binding.etStatus.setText(item.status.toString())
                binding.etOwner.setText(item.owner)
                binding.etPlace.setText(item.place.toString())
                binding.cbAvailable.isChecked = item.available
                setupSpecifications(item.specifications)
            }
        }
    }

    private fun getAuthToken(): String {
        return jwtToken ?: ""
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val updatedItem = getUpdatedItem()
            viewModel.updateItem(getAuthToken(), updatedItem)
            observeViewModel()
        }

        binding.btnScan.setOnClickListener {
            navigateToLiveBarcodeScanningFragment()
        }

        binding.btnAddSpecification.setOnClickListener {
            showAddSpecificationDialog()
        }

        binding.btnHardware.setOnClickListener {
            showHardwareSelectionDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                // показываем диалоговое окно с текстом ошибки
                AlertDialog.Builder(requireContext())
                    .setTitle("Ошибка")
                    .setMessage(errorMessage)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                // если ошибки нет, показываем сообщение об успешном сохранении и переходим назад
                Toast.makeText(requireContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigateUp()
            }
        }
    }

    private fun showHardwareSelectionDialog() {
        val hardwareNames = hardwareList.map { it.name }
        val selectedIndex = hardwareList.indexOfFirst { it.id == binding.etHardware.text.toString().toIntOrNull() }

        AlertDialog.Builder(requireContext())
            .setTitle("Выберите оборудование")
            .setSingleChoiceItems(hardwareNames.toTypedArray(), selectedIndex) { dialog, which ->
                val selectedHardware = hardwareList[which]
                binding.etHardware.setText(selectedHardware.id.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun setupBarcodeResultListener() {
        setFragmentResultListener(LiveBarcodeScanningFragment.REQUEST_KEY) { _, bundle ->
            val scannedInvKey = bundle.getString("barcode")
            scannedInvKey?.let {
                viewModel.updateInvKey(it)
                binding.etInvKey.setText(it)
            }
        }
    }

    private fun navigateToLiveBarcodeScanningFragment() {
        val action = ItemFragmentDirections.actionItemFragmentToLiveBarcodeScanningFragment()
        findNavController().navigate(action)
    }

    private fun getUpdatedItem(): Item {
        val newId = if (isNew) 0 else itemId
        val name = binding.etName.text.toString()
        val invKey = binding.etInvKey.text.toString()
        val hardware = binding.etHardware.text.toString().toIntOrNull() ?: 0
        val room = binding.etGroup.text.toString().toIntOrNull()
        val status = binding.etStatus.text.toString().toIntOrNull() ?: 0
        val owner = binding.etOwner.text.toString()
        val place = binding.etPlace.text.toString().toIntOrNull() ?: 0
        val available = binding.cbAvailable.isChecked
        val specifications = getUpdatedSpecifications()

        return Item(
            id = newId,
            name = name,
            inv_key = invKey,
            hardware = hardware,
            room = room,
            status = status,
            owner = owner,
            place = place,
            available = available,
            specifications = specifications
        )
    }

    private fun setupSpecifications(specifications: Map<String, JsonElement>) {
        binding.specificationsContainer.removeAllViews()
        specifications.forEach { (key, value) ->
            val specificationView = createSpecificationView(key, value)
            binding.specificationsContainer.addView(specificationView)
        }
    }

    private fun createSpecificationView(key: String, value: JsonElement): View {
        val view = layoutInflater.inflate(R.layout.item_specification, null)
        val etKey = view.findViewById<EditText>(R.id.etKey)
        val etValue = view.findViewById<EditText>(R.id.etValue)
        val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)

        etKey.setText(key)
        etValue.setText(value.jsonPrimitive.content)

        if(!isNew) {
            btnDelete.visibility = View.GONE
            etKey.isEnabled = false
            etValue.isEnabled = false
        }
        btnDelete.setOnClickListener {
            binding.specificationsContainer.removeView(view)
        }

        return view
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getUpdatedSpecifications(): Map<String, JsonElement> {
        val specifications = mutableMapOf<String, JsonElement>()
        for (i in 0 until binding.specificationsContainer.childCount) {
            val view = binding.specificationsContainer.getChildAt(i)
            val etKey = view.findViewById<EditText>(R.id.etKey)
            val etValue = view.findViewById<EditText>(R.id.etValue)

            val key = etKey.text.toString()
            val value = etValue.text.toString()
            specifications[key] = JsonPrimitive(value)
        }
        return specifications
    }

    private fun showAddSpecificationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_specification, null)
        val etKey = dialogView.findViewById<EditText>(R.id.etKey)
        val etValue = dialogView.findViewById<EditText>(R.id.etValue)

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val key = etKey.text.toString()
                val value = etValue.text.toString()
                if (key.isNotEmpty() && value.isNotEmpty())
                    addSpecification(key, value)
                else
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun addSpecification(key: String, value: String) {
        val specificationView = createSpecificationView(key, JsonPrimitive(value))
        binding.specificationsContainer.addView(specificationView)
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