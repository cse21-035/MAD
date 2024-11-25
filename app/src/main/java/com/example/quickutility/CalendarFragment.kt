package com.example.quickutility

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.quickutility.databinding.FragmentCalendarBinding
import java.lang.reflect.Array.set
import java.util.* // Ensure you're using java.util.Calendar
import java.util.Calendar


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val eventMap: MutableMap<Long, MutableList<String>> = mutableMapOf() // Map to store events for dates
    private var selectedDateInMillis: Long = 0L // Store the selected date in milliseconds

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up the CalendarView
        val calendarView = binding.calendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Get the selected date in milliseconds using Calendar
            selectedDateInMillis = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.timeInMillis

            Toast.makeText(
                requireContext(),
                "Selected Date: $dayOfMonth/${month + 1}/$year",
                Toast.LENGTH_SHORT
            ).show()

            // Update the event display for the selected date
            updateEventDisplay()
        }

        // Handle FloatingActionButton for adding events
        binding.fabEvent.setOnClickListener {
            Toast.makeText(requireContext(), "Event added", Toast.LENGTH_SHORT).show()
            // Open Event Creation Dialog
            showEventInputDialog()
        }

        // Handle Home button click
        binding.btnHome2.setOnClickListener {
            Toast.makeText(requireContext(), "Home button clicked", Toast.LENGTH_SHORT).show()
            // Navigate back to the previous screen
            requireActivity().onBackPressed()
        }
    }

    // Function to show an input dialog for event creation
    private fun showEventInputDialog() {
        val input = EditText(requireContext())
        input.hint = "Enter event description"

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Event")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val eventDescription = input.text.toString()
                if (eventDescription.isNotEmpty()) {
                    Toast.makeText(requireContext(), "Event added...", Toast.LENGTH_SHORT).show()
                    addEvent(eventDescription)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    // Function to add event for the selected date
    private fun addEvent(eventDescription: String) {
        if (selectedDateInMillis == 0L) {
            Toast.makeText(requireContext(), "Please select a date first.", Toast.LENGTH_SHORT).show()
            return
        }

        // Add event to the list for the selected date
        eventMap.getOrPut(selectedDateInMillis) { mutableListOf() }.add(eventDescription)

        // Log the current state of eventMap for debugging purposes
        Log.d("CalendarFragment", "Event Map: $eventMap")

        // Update the TextView to display the events
        updateEventDisplay()
    }

    // Function to update the TextView with events for the selected date
    private fun updateEventDisplay() {
        val events = eventMap[selectedDateInMillis]

        // Find the TextView where you want to display events (make sure it's in the layout)
        val tvEventsView = binding.tvEventsView // Ensure that you have this TextView in the layout

        if (events.isNullOrEmpty()) {
            tvEventsView.text = "No events for this day."
        } else {
            val eventListString = events.joinToString("\n")
            tvEventsView.text = eventListString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
