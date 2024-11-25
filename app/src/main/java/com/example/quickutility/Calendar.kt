package com.example.quickutility

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.* // Ensure this is here

class Calendar : AppCompatActivity() {

    private lateinit var btnHome2: Button
    private lateinit var calendarView: android.widget.CalendarView
    private lateinit var fabEvent: FloatingActionButton
    private lateinit var tvEventsView: TextView

    // HashMap to store events for each date (key: date in milliseconds, value: list of event descriptions)
    private val eventMap: MutableMap<Long, MutableList<String>> = mutableMapOf()

    // Currently selected date in milliseconds
    private var selectedDateInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Initialize views
        btnHome2 = findViewById(R.id.btnHome2)
        tvEventsView = findViewById(R.id.tvEventsView)
        calendarView = findViewById(R.id.calendarView)
        fabEvent = findViewById(R.id.fabEvent)

        // Set up Home button to navigate back to MainActivity
        btnHome2.setOnClickListener {
            Toast.makeText(this, "Opening Home Page...", Toast.LENGTH_SHORT).show();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set up CalendarView listener to capture selected date
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Get the selected date in milliseconds
            val calendar = java.util.Calendar.getInstance() // Fully qualified Calendar
            calendar.set(year, month, dayOfMonth)
            selectedDateInMillis = calendar.timeInMillis

            // Update the TextView to show events for the selected date
            updateEventDisplay()
        }

        // Set up FAB to add events
        fabEvent.setOnClickListener {
            // Show an AlertDialog to input the event
            showEventInputDialog()
        }
    }

    // Function to show an input dialog for event creation
    private fun showEventInputDialog() {
        val input = EditText(this)
        input.hint = "Enter event description"

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Event")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val eventDescription = input.text.toString()
                if (eventDescription.isNotEmpty()) {
                    Toast.makeText(this, "Event added...", Toast.LENGTH_SHORT).show();
                    addEvent(eventDescription)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    // Function to add event for the selected date
    private fun addEvent(eventDescription: String) {
        // Check if there is already an event list for the selected date
        if (eventMap[selectedDateInMillis] == null) {
            eventMap[selectedDateInMillis] = mutableListOf()
        }

        // Add the new event to the list
        eventMap[selectedDateInMillis]?.add(eventDescription)

        // Update the TextView to display the events
        updateEventDisplay()
    }

    // Function to update the TextView with events for the selected date
    private fun updateEventDisplay() {
        val events = eventMap[selectedDateInMillis]

        if (events.isNullOrEmpty()) {
            tvEventsView.text = "No events for this day."
        } else {
            val eventListString = events.joinToString("\n")
            tvEventsView.text = eventListString
        }
    }
}
