package com.apprajapati.livedata

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/*
    https://developer.android.com/topic/libraries/architecture/lifecycle
 */
class MyLifeCycleObserver : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d("Ajay", "${owner.javaClass.name} onCreate")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("Ajay", "${owner.javaClass.name} onPause")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("Ajay", "${owner.javaClass.name} onResume")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("Ajay", "${owner.javaClass.name} onStart")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("Ajay", "${owner.javaClass.name} onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d("Ajay", "${owner.javaClass.name} onDestroy")

    }
}