package com.apprajapati.livedata

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner


class OwnLiveData<T>(private var initValue: T) {

    private val observers = arrayListOf<(T?)->Unit>()

    private lateinit var lifecycleOwner: LifecycleOwner

    fun setValue(v : T) {
        if(lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){
            initValue = v
            Log.d("Ajay","Observers notified")
            for(observer in observers){
                observer.invoke(initValue)
            }
        }else{
            Log.d("Ajay","Trying but not STARTED state...")
        }
    }

    fun getValue(): T? {
       return initValue
    }

    fun increaseValue(){
        if(initValue is Int){
            val add = (initValue as Int).plus(1)
            setValue(add as T)
        }
    }

    /*
        A simple way to think of this as having a reference of a function.
        And whenever value is changed, that function call is made with the value.

        observers are storing functions pointer/references.
        and that function body is invoked() when change of value happens using setValue() method.
    */
    fun addObserver(owner: LifecycleOwner, observer: (T?) -> Unit) {
        lifecycleOwner = owner
        observers.add(observer)
    }

    fun removeObserver(observer: (T?) -> Unit) {
        observers.remove(observer)
    }
}