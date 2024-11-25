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
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FlashNews : AppCompatActivity() {

    private lateinit var btnHome5: Button
    private lateinit var eventListView: ListView
    private lateinit var fabAddEvent: FloatingActionButton
    private val events = mutableListOf<QuickEvent>()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flash_news)

        // Initialize UI components
        btnHome5 = findViewById(R.id.btnHome5)
        eventListView = findViewById(R.id.eventListView)
        fabAddEvent = findViewById(R.id.fabAddEvent)

        // Setup the event adapter
        eventAdapter = EventAdapter(this, events)
        eventListView.adapter = eventAdapter

        // Button to navigate back to the main screen
        btnHome5.setOnClickListener {
            Toast.makeText(this, "Opening Home Page...", Toast.LENGTH_SHORT).show();
            finish() // Navigate back to previous activity (main activity)
        }

        // Floating action button to add a new event
        fabAddEvent.setOnClickListener {
            showAddEventDialog()
        }
    }

    // Dialog to add a new event
    private fun showAddEventDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null)

        // Find the EditText views
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val timeEditText = dialogView.findViewById<EditText>(R.id.editTextTime)

        // Show the dialog
        AlertDialog.Builder(this)
            .setTitle("Add New Event")
            .setView(dialogView)  // Setting the inflated layout
            .setPositiveButton("Add") { _, _ ->
                val title = titleEditText.text.toString()
                val time = timeEditText.text.toString()
                if (title.isNotEmpty() && time.isNotEmpty()) {
                    events.add(QuickEvent(title, time))
                    Toast.makeText(this, "Event added...", Toast.LENGTH_SHORT).show();
                    eventAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()

    }
}

// Data model class to represent a Quick Event
data class QuickEvent(val title: String, val time: String)

// Custom Adapter for the event list
class EventAdapter(context: Context, private val events: MutableList<QuickEvent>) :
    ArrayAdapter<QuickEvent>(context, 0, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            // Inflating the custom layout (event_item.xml) for each item
            convertView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        }

        val event = events[position]
        val titleTextView = convertView?.findViewById<TextView>(R.id.eventTitle)
        val timeTextView = convertView?.findViewById<TextView>(R.id.eventTime)

        titleTextView?.text = event.title
        timeTextView?.text = event.time

        // Edit and Delete button actions
        val editButton = convertView?.findViewById<Button>(R.id.editButton)
        val deleteButton = convertView?.findViewById<Button>(R.id.deleteButton)

        // Edit button click listener
        editButton?.setOnClickListener {
            showEditEventDialog(position)
        }

        // Delete button click listener
        deleteButton?.setOnClickListener {
            events.removeAt(position)
            notifyDataSetChanged()

        }

        return convertView!!
    }

    // Show dialog to edit an event
    private fun showEditEventDialog(position: Int) {
        val event = events[position]
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null)

        // Pre-fill the fields with existing event data
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val timeEditText = dialogView.findViewById<EditText>(R.id.editTextTime)

        titleEditText.setText(event.title)
        timeEditText.setText(event.time)

        // Show the edit dialog
        AlertDialog.Builder(context)
            .setTitle("Edit Event")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = titleEditText.text.toString()
                val newTime = timeEditText.text.toString()
                if (newTitle.isNotEmpty() && newTime.isNotEmpty()) {
                    events[position] = QuickEvent(newTitle, newTime)
                    notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
