package com.apprajapati.livedata

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private val model : MyViewModel by viewModels()

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView = findViewById(R.id.counterText)
        button  = findViewById(R.id.button)

        fragmentContainer = findViewById(R.id.fragmentContainer)

        model.myData.addObserver(this) {
            mydata->
                Log.d("Ajay", "Activity:: Data update $mydata")
                textView.text = model.myData.getValue().toString()
        }

        button.setOnClickListener{
            model.increaseMyData()
        }
        this.lifecycle.addObserver(MyLifeCycleObserver())
        addFragment(LiveDataShowCaseFragment(), supportFragmentManager)
    }

    private fun addFragment(fragment: Fragment, manager: FragmentManager) {
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun onStop() {
        super.onStop()
        repeat(10){
            model.increaseMyData()
        }
    }
}