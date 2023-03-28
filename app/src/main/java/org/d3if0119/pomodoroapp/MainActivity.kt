package org.d3if0119.pomodoroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.d3if0119.pomodoroapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = Home()
        val tasksFragment = Tasks()

        setCurrentFragment(homeFragment)

        binding.navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home->setCurrentFragment(homeFragment)
                R.id.tasks->setCurrentFragment(tasksFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}