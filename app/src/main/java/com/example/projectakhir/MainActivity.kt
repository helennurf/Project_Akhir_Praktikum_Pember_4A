package com.example.projectakhir

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.navigation_home -> FragmentHome()
                R.id.navigation_payments -> FragmentPayments()
                R.id.navigation_account -> FragmentAccount()
                else -> null
            }

                selectedFragment?.let {
                    val fragmentManager: FragmentManager = supportFragmentManager
                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.frame_container, it)
                    transaction.commit()
                }

                true
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val decorView: View = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        supportActionBar?.hide()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Set the default fragment
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, FragmentHome())
        transaction.commit()
    }
}