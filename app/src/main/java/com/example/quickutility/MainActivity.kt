package com.example.quickutility

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var btnCalendar: Button
    private lateinit var btnCalculator: Button
    private lateinit var btnQuickConverter: Button
    private lateinit var btnFlashNews: Button
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var mainContent: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_view)
        btnCalendar = findViewById(R.id.btnCalendar)
        btnCalculator = findViewById(R.id.btnCalculator)
        btnQuickConverter = findViewById(R.id.btnQuickConvetor) // Corrected ID
        btnFlashNews = findViewById(R.id.btnFlashNews)
        mainContent = findViewById(R.id.main_content) // Main content layout containing buttons
        fragmentContainer = findViewById(R.id.fragment_container) // Fragment container

        // Set up Toolbar for drawer toggle
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up the NavigationView item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_settings -> openSettings()
                R.id.nav_help -> openHelp()
                R.id.nav_calendar -> openCalendarFragment()
                R.id.nav_calculator -> openCalculatorFragment()
                R.id.nav_quick_convetor -> openQuickConvetorFragment()
                R.id.nav_flash_events -> openFlashEventsFragment()
            }
            true
        }

        // Button Click Listeners
        btnFlashNews.setOnClickListener {
            openActivity(FlashNews::class.java, "Opening FlashNews Page...")
        }

        btnQuickConverter.setOnClickListener {
            openActivity(QuickConverter::class.java, "Opening QuickConverter Page...")
        }

        btnCalculator.setOnClickListener {
            openActivity(Calculator::class.java, "Opening Calculator Page...")
        }

        btnCalendar.setOnClickListener {
            Toast.makeText(this, "Opening Calendar Page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }
    }

    private fun openSettings() {
        Toast.makeText(this, "Opening Setting Page...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Setting::class.java)
        startActivity(intent)
    }

    private fun openHelp() {
        Toast.makeText(this, "Opening Help Page...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Help::class.java)
        startActivity(intent)
    }

    private fun openCalendarFragment() {
        Toast.makeText(this, "Opening Calendar Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        mainContent.visibility = View.GONE
        // Show the FrameLayout fragment container by setting visibility to visible
        fragmentContainer.visibility = View.VISIBLE

        // Create the fragment transaction
        val calendarFragment = CalendarFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the CalendarFragment
        transaction.replace(R.id.fragment_container, calendarFragment, CalendarFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }

    // Method to go back to main content (buttons/images) when navigating back
    override fun onBackPressed() {
        super.onBackPressed()
        // Show main content again when fragment is popped off the back stack
        mainContent.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }

    private fun openActivity(activityClass: Class<*>, message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    private fun openCalculatorFragment() {
        Toast.makeText(this, "Opening Calculator Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        mainContent.visibility = View.GONE
        // Show the FrameLayout fragment container by setting visibility to visible
        fragmentContainer.visibility = View.VISIBLE

        // Create the fragment transaction
        val calculatorFragment = CalculatorFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the CalendarFragment
        transaction.replace(R.id.fragment_container, calculatorFragment, CalendarFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }
    private fun openQuickConvetorFragment() {
        Toast.makeText(this, "Opening Quick Converter Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        mainContent.visibility = View.GONE
        // Show the FrameLayout fragment container by setting visibility to visible
        fragmentContainer.visibility = View.VISIBLE

        // Create the fragment transaction
        val quickConverterFragment = QuickConverterFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the CalendarFragment
        transaction.replace(R.id.fragment_container, quickConverterFragment, QuickConverterFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }
    private fun openFlashEventsFragment() {
        Toast.makeText(this, "Opening Flash Events Fragment...", Toast.LENGTH_SHORT).show()

        // Hide all UI elements (buttons, images)
        mainContent.visibility = View.GONE
        // Show the FrameLayout fragment container by setting visibility to visible
        fragmentContainer.visibility = View.VISIBLE

        // Create the fragment transaction
        val flashEventsFragment = FlashEventsFragment()
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the container with the CalendarFragment
        transaction.replace(R.id.fragment_container, flashEventsFragment, FlashEventsFragment::class.java.simpleName)
        transaction.addToBackStack(null)  // Add fragment to back stack for navigation
        transaction.commit()
    }
    fun showHomeUI() {
        mainContent.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }
}
