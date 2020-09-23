package com.example.guessapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessapp.Score
import com.example.guessapp.databinding.FragmentScoreBinding
import kotlinx.android.synthetic.main.fragment_game.*


class Score : Fragment() {
   private lateinit var binding:FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_score, container, false)
        binding.lifecycleOwner = this
        checkScore()

        binding.PlayAgainButton.setOnClickListener{
            playAgain()
        }
        return binding.root
    }

    private fun checkScore(){
        val args = ScoreArgs.fromBundle(requireArguments())
        binding.FinalScore.text = "Your score is: ${args.FinalScore}"
    }

    private fun playAgain(){
        findNavController().navigate(ScoreDirections.actionScoreToGameFragment())
    }

}