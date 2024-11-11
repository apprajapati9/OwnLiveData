package com.apprajapati.livedata

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/*
    Livedata uses observer pattern. Think of it as observing function references and calling them when value is changed.
 */
class OwnLiveData<T>(private var initValue: T) {

    //Every activity/fragment implements LifecycleOwner so we can keep track of lifecycle events.
    private val observers = HashMap<(T?)->Unit, LifecycleOwner>()

    fun setValue(v : T) {
        initValue = v //This allows this value to be updated even when in the background.
        //however, observers will not be notified.

        for((observer, lifecycleOwner) in observers){
            //invoke functions that are attached to a lifecycle that is STARTED. will not trigger if onStop state of fragment/activity's lifecycle.
            if(lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("Ajay","Observers notified")
                observer.invoke(initValue)
            }
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
        observers[observer] = owner
        owner.lifecycle.addObserver(OwnLiveDataObserver(observer))
    }

    //This triggers when OnCreate()/OnResume() lifecycle events are triggered on attached  lifecycle owner.
    fun notifyChange(observer: (T?) -> Unit) {
        Log.d("Ajay", "NotifyChange()")
        observer.invoke(initValue)
    }

    fun removeObserver(owner: LifecycleOwner, observer: (T?) -> Unit) {
        observers.remove(observer)
        owner.lifecycle.removeObserver(OwnLiveDataObserver(observer))
    }

    //LifecycleObservers allow us to independently handle lifecycle events, making code decoupled from activity.
    inner class OwnLiveDataObserver(private val observer: (T?) -> Unit): DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            Log.d("Ajay", "INNER ${owner.javaClass.name} onCreate")

        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            notifyChange(observer)
            Log.d("Ajay", "INNER ${owner.javaClass.name} onResume")

        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            removeObserver(owner, observer)
            Log.d("Ajay", "INNER ${owner.javaClass.name} onDestroy")

        }
    }
}