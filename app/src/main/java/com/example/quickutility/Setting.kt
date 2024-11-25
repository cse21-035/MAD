package com.example.quickutility

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.content.res.Configuration
import androidx.appcompat.app.AlertDialog

class Setting : AppCompatActivity() {

    private lateinit var btnHome6: Button
    private lateinit var fontSpinner: Spinner
    private lateinit var themeSwitch: Switch
    private lateinit var textView: TextView
    private lateinit var btnAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Initialize UI components
        btnHome6 = findViewById(R.id.btnHome6)
        fontSpinner = findViewById(R.id.spinnerFont)
        themeSwitch = findViewById(R.id.switchTheme)
        textView = findViewById(R.id.sampleText)  // Updated to the correct TextView ID for font preview
        btnAbout = findViewById(R.id.btnAbout)

        // Home button to navigate back to MainActivity
        btnHome6.setOnClickListener {
            Toast.makeText(this, "Opening Home Page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Setup theme switch (dark mode / light mode)
        setupThemeSwitch()

        // Setup font spinner and handle font selection
        setupFontSpinner()

        // Setup About button to show app details
        setupAboutButton()
    }

    // Function to setup the theme switch for dark/light mode
    private fun setupThemeSwitch() {
        themeSwitch.isChecked = isDarkMode()

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(this, "Dark mode enabled...", Toast.LENGTH_SHORT).show()
            } else {
                // Enable light mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(this, "Back to light mode...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to check if dark mode is enabled
    private fun isDarkMode(): Boolean {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    // Function to setup font spinner and apply selected font to TextView
    private fun setupFontSpinner() {
        val fontOptions = arrayOf("Sans-serif", "Serif", "Monospace")  // List of built-in fonts
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fontOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fontSpinner.adapter = adapter

        fontSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedFont = parentView.getItemAtPosition(position).toString()

                // Apply the selected font to the TextView
                applyFont(selectedFont)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing if no selection
            }
        }
    }

    // Function to apply font based on selection (using built-in system fonts)
    private fun applyFont(fontName: String) {
        val typeface: Typeface = when (fontName) {
            "Sans-serif" -> Typeface.SANS_SERIF  // Android's built-in Sans-serif font
            "Serif" -> Typeface.SERIF  // Android's built-in Serif font
            "Monospace" -> Typeface.MONOSPACE  // Android's built-in Monospace font
            else -> Typeface.DEFAULT  // Default system font
        }
        textView.typeface = typeface
    }

    // Function to setup About button
    private fun setupAboutButton() {
        btnAbout.setOnClickListener {
            // Show About dialog
            showAboutDialog()
        }
    }

    // Function to show About dialog
    private fun showAboutDialog() {
        val aboutMessage = """
            App version: 1.0.0
            Developed by Thabiso
            This is a sample settings app with dark mode and font customization.
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("About the App")
            .setMessage(aboutMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()  // Dismiss the dialog
            }
            .create()
            .show()
    }
}
