package com.example.guessapp

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.guessapp.databinding.FragmentGameBinding
import java.util.EnumSet.of

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding

    private lateinit var viewModel:GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { isFinished->
            if(!isFinished){
                Log.i("isFinished", "Starting navigation")
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToScore(viewModel.count.value ?: 0))
                Log.i("isFinished", "Navigation id done")
                viewModel.turnGameFinished()
            }
        })


        viewModel.timeOver.observe(viewLifecycleOwner, Observer{ isTimeOver->
            if(isTimeOver){
                Toast.makeText(context, "Time is over, sorry...", Toast.LENGTH_SHORT).show()
                viewModel.decreaseOne()
            }
        })
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPauseTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResumeTimer()
    }



}