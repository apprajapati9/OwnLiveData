package com.apprajapati.livedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apprajapati.livedata.databinding.FragmentLivedataShowcaseBinding

class LiveDataShowCaseFragment : Fragment() {

    private var _binding : FragmentLivedataShowcaseBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLivedataShowcaseBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        //requireActivity makes sure that viewmodel attached to activity's lifecycle is returned.

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.myData.addObserver(this) {
            data ->
                Log.d("Ajay", "Fragment:: Data update $data")
                binding.livedataText.text = data.toString()
        }

        this.lifecycle.addObserver(MyLifeCycleObserver())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}