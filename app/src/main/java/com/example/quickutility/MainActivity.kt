package com.example.quickutility

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var mainContent: ConstraintLayout
    private lateinit var btnHelp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        mainContent = findViewById(R.id.main_content) // Main content layout containing buttons
        fragmentContainer = findViewById(R.id.fragment_container) // Fragment container
        btnHelp = findViewById(R.id.btnHelp)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        btnHelp.setOnClickListener {
            val intent = Intent(this, Help::class.java )
            startActivity(intent)
        }

        // Bottom Navigation Listener
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_calendar -> openCalendarFragment()
                R.id.nav_calculator -> openCalculatorFragment()
                R.id.nav_quick_convetor -> openQuickConvetorFragment()
                R.id.nav_flash_events -> openFlashEventsFragment()
                R.id.nav_settings -> openSettings()
            }
            true
        }
    }

    private fun openSettings() {
        Toast.makeText(this, "Opening Setting Page...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Setting::class.java)
        startActivity(intent)
    }

    private fun openCalendarFragment() {
        Toast.makeText(this, "Opening Calendar Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        hideUIElements()

        // Create the fragment transaction
        val calendarFragment = CalendarFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the CalendarFragment
        transaction.replace(R.id.fragment_container, calendarFragment, CalendarFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }

    private fun openCalculatorFragment() {
        Toast.makeText(this, "Opening Calculator Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        hideUIElements()

        // Create the fragment transaction
        val calculatorFragment = CalculatorFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the CalculatorFragment
        transaction.replace(R.id.fragment_container, calculatorFragment, CalculatorFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }

    private fun openQuickConvetorFragment() {
        Toast.makeText(this, "Opening Quick Converter Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        hideUIElements()

        // Create the fragment transaction
        val quickConverterFragment = QuickConverterFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the QuickConverterFragment
        transaction.replace(R.id.fragment_container, quickConverterFragment, QuickConverterFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }

    private fun openFlashEventsFragment() {
        Toast.makeText(this, "Opening Flash Events Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        hideUIElements()

        // Create the fragment transaction
        val flashEventsFragment = FlashEventsFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the FlashEventsFragment
        transaction.replace(R.id.fragment_container, flashEventsFragment, FlashEventsFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }

    // Hide UI elements when opening fragments
    private fun hideUIElements() {
        // Hide toolbar and bottom navigation
        supportActionBar?.hide()
        bottomNavigationView.visibility = View.GONE

        // Hide main content and show the fragment container
        mainContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
    }

    // Method to go back to main content (buttons/images) when navigating back
    override fun onBackPressed() {
        super.onBackPressed()
        // Show toolbar and bottom navigation again
        supportActionBar?.show()
        bottomNavigationView.visibility = View.VISIBLE

        // Show main content again when fragment is popped off the back stack
        mainContent.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }

    private fun openActivity(activityClass: Class<*>, message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    fun showHomeUI() {
        mainContent.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }
}
