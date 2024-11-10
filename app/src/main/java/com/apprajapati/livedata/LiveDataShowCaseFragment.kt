package com.apprajapati.livedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class LiveDataShowCaseFragment : Fragment() {

    private var view : View ?= null

    private lateinit var mLivedataText : TextView

    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_livedata_showcase, container, false)

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        //requireActivity makes sure that viewmodel attached to activity's lifecycle is returned.

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLivedataText = view.findViewById(R.id.livedataText)
        viewModel.myData.addObserver(this) {
            data ->
                Log.d("Ajay", "Fragment:: Data update $data")
                mLivedataText.text = data.toString()
        }

        this.lifecycle.addObserver(MyLifeCycleObserver())
    }
}