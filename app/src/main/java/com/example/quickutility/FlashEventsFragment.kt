package com.example.quickutility

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quickutility.databinding.FragmentFlashEventsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FlashEventsFragment : Fragment() {

    private lateinit var btnHome5: Button
    private lateinit var eventListView: ListView
    private lateinit var fabAddEvent: FloatingActionButton
    private val events = mutableListOf<QuickEvent>()
    private lateinit var eventAdapter: EventAdapter

    private var _binding: FragmentFlashEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlashEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components
        btnHome5 = binding.btnHome5
        eventListView = binding.eventListView
        fabAddEvent = binding.fabAddEvent

        // Setup the event adapter
        eventAdapter = EventAdapter(requireContext(), events)
        eventListView.adapter = eventAdapter

        // Button to navigate back to the main screen
        btnHome5.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Home Page...", Toast.LENGTH_SHORT).show()
            // Navigate back to MainActivity (or home screen)
            navigateToHome()
        }

        // Floating action button to add a new event
        fabAddEvent.setOnClickListener {
            showAddEventDialog()
        }
    }

    // Dialog to add a new event
    private fun showAddEventDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_event, null)

        // Find the EditText views
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val timeEditText = dialogView.findViewById<EditText>(R.id.editTextTime)

        // Show the dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Add New Event")
            .setView(dialogView)  // Setting the inflated layout
            .setPositiveButton("Add") { _, _ ->
                val title = titleEditText.text.toString()
                val time = timeEditText.text.toString()
                if (title.isNotEmpty() && time.isNotEmpty()) {
                    events.add(QuickEvent(title, time))
                    Toast.makeText(requireContext(), "Event added...", Toast.LENGTH_SHORT).show()
                    eventAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Method to handle the navigation back to the home screen
    private fun navigateToHome() {
        // If you're using a Fragment and want to pop it from the back stack:
        parentFragmentManager.popBackStack()

        // Or, if you want to go back to the MainActivity directly and make sure the home UI is visible:
        activity?.let {
            if (it is MainActivity) {
                it.showHomeUI() // Assuming `showHomeUI()` is a method in MainActivity that shows the home UI
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


