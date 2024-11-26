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

    // Updated QuickEvent class to only contain title and date (no description)
    data class QuickEvent(
        val title: String,
        val date: String // Only title and date now
    )

    // EventAdapter class to bind events data to the ListView
    class EventAdapter(
        context: Context,
        private val events: MutableList<QuickEvent>
    ) : ArrayAdapter<QuickEvent>(context, R.layout.event_item, events) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)

            val event = events[position]

            // Bind the event data to the UI elements in the list item
            val titleTextView: TextView = view.findViewById(R.id.eventTitle)
            val dateTextView: TextView = view.findViewById(R.id.eventTime)

            // Set the text for the TextViews to display event details
            titleTextView.text = event.title
            dateTextView.text = event.date

            return view
        }

        // Method to add an event to the adapter list and refresh the view
        fun addEvent(event: QuickEvent) {
            events.add(event)
            notifyDataSetChanged() // Notify the adapter that the data has changed
        }
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
            // Navigate back to the previous screen
            requireActivity().onBackPressed()
        }

        // Floating action button to add a new event
        fabAddEvent.setOnClickListener {
            showAddEventDialog()
        }
    }

    // Dialog to add a new event (only title and date)
    private fun showAddEventDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_event, null)

        // Find the EditText views for title and date (no description anymore)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val dateEditText = dialogView.findViewById<EditText>(R.id.editTextTime)

        // Show the dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Add New Event")
            .setView(dialogView)  // Setting the inflated layout
            .setPositiveButton("Add") { _, _ ->
                val title = titleEditText.text.toString().trim()
                val date = dateEditText.text.toString().trim()

                // Validate input and add the event
                if (title.isNotEmpty() && date.isNotEmpty()) {
                    // Add the event with title and date only (no description)
                    events.add(QuickEvent(title, date))
                    Toast.makeText(requireContext(), "Event added...", Toast.LENGTH_SHORT).show()
                    eventAdapter.notifyDataSetChanged() // Update the adapter to reflect the new data
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
