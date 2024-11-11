package com.apprajapati.livedata

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.apprajapati.livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding ?= null
    private val binding get() = _binding!!

    private val model : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        model.myData.addObserver(this) {
            data->
                Log.d("Ajay", "Activity:: Data update $data")
                binding.counterText.text = model.myData.getValue().toString()
        }

        binding.button.setOnClickListener{
            model.increaseMyData()
        }
        //this.lifecycle.addObserver(MyLifeCycleObserver())
        addFragment(LiveDataShowCaseFragment(), supportFragmentManager)
    }

    private fun addFragment(fragment: Fragment, manager: FragmentManager) {
        manager.beginTransaction().replace(binding.fragmentContainer.id, fragment).commit()
    }

    override fun onStop() {
        super.onStop()
        //This is to test that livedata update will not be triggered when activity is in onStop state.
        repeat(10){
            model.increaseMyData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}