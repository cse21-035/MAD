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
        private val events: MutableList<QuickEvent>,
        private val onEditClick: (Int) -> Unit, // Callback for Edit button click
        private val onDeleteClick: (Int) -> Unit // Callback for Delete button click
    ) : ArrayAdapter<QuickEvent>(context, R.layout.event_item, events) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)

            val event = events[position]

            // Bind the event data to the UI elements in the list item
            val titleTextView: TextView = view.findViewById(R.id.eventTitle)
            val dateTextView: TextView = view.findViewById(R.id.eventTime)
            val editButton: Button = view.findViewById(R.id.editButton)
            val deleteButton: Button = view.findViewById(R.id.deleteButton)

            // Set text for title and date
            titleTextView.text = event.title
            dateTextView.text = event.date

            // Set listeners for the edit and delete buttons
            editButton.setOnClickListener {
                onEditClick(position) // Call the edit callback with the event position
            }

            deleteButton.setOnClickListener {
                onDeleteClick(position) // Call the delete callback with the event position
            }

            return view
        }

        // Method to add an event to the adapter list and refresh the view
        fun addEvent(event: QuickEvent) {
            events.add(event)
            notifyDataSetChanged() // Notify the adapter that the data has changed
        }

        // Method to remove an event from the list and refresh the view
        fun removeEvent(position: Int) {
            events.removeAt(position)
            notifyDataSetChanged() // Notify the adapter that the data has changed
        }

        // Method to update an event
        fun updateEvent(position: Int, updatedEvent: QuickEvent) {
            events[position] = updatedEvent
            notifyDataSetChanged() // Notify the adapter that the data has changed
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components
        btnHome5 = binding.btnHome5
        eventListView = binding.eventListView
        fabAddEvent = binding.fabAddEvent

        // Setup the event adapter and pass the edit and delete callbacks
        eventAdapter = EventAdapter(requireContext(), events,
            onEditClick = { position ->
                showEditEventDialog(position) // Show edit dialog when Edit button is clicked
            },
            onDeleteClick = { position ->
                deleteEvent(position) // Delete event when Delete button is clicked
            }
        )

        eventListView.adapter = eventAdapter

        // Button to navigate back to the main screen
        btnHome5.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Home Page...", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }

        // Floating action button to add a new event
        fabAddEvent.setOnClickListener {
            showAddEventDialog() // Show add event dialog
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

    // Show dialog to edit an event
    private fun showEditEventDialog(position: Int) {
        val event = events[position]
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_event, null)

        // Find the EditText views for title and date
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val dateEditText = dialogView.findViewById<EditText>(R.id.editTextTime)

        // Pre-populate the fields with the current event data
        titleEditText.setText(event.title)
        dateEditText.setText(event.date)

        // Show the dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Edit Event")
            .setView(dialogView)  // Setting the inflated layout
            .setPositiveButton("Save") { _, _ ->
                val newTitle = titleEditText.text.toString().trim()
                val newDate = dateEditText.text.toString().trim()

                // Validate input and update the event
                if (newTitle.isNotEmpty() && newDate.isNotEmpty()) {
                    val updatedEvent = QuickEvent(newTitle, newDate)
                    eventAdapter.updateEvent(position, updatedEvent) // Update the event in the list
                    Toast.makeText(requireContext(), "Event updated...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Delete an event
    private fun deleteEvent(position: Int) {
        // Remove the event from the list and update the adapter
        events.removeAt(position)
        eventAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Event deleted...", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
