package com.example.guessapp

import android.database.DatabaseUtils
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.combineTransform
import kotlin.time.measureTimedValue

class GameViewModel: ViewModel(){
    private var _word = MutableLiveData<String>()
    val word:LiveData<String>
        get() = _word
    private var _count = MutableLiveData<Int>()
    val count : LiveData<Int>
        get() = _count
    private var _gameFinished = MutableLiveData<Boolean>()
    val gameFinished : LiveData<Boolean>
        get() = _gameFinished
    private var _time = MutableLiveData<Long>()
    val time:LiveData<Long>
        get() = _time
    private var _timeOver = MutableLiveData<Boolean>()
    val timeOver:LiveData<Boolean>
        get() = _timeOver
    private lateinit var timer:CountDownTimer
    private var wasTimerPaused:Boolean = false
    private var timeUntil:Long = COUNTDOWN_TIME
    val currentTimeString = Transformations.map(time) { newTime ->
        DateUtils.formatElapsedTime(newTime)
    }

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }


    private var wordList: MutableList<String> = mutableListOf(
        "queen",
        "hospital",
        "basketball",
        "cat",
        "change",
        "snail",
        "soup",
        "calendar",
        "sad",
        "desk",
        "guitar",
        "home",
        "railway",
        "zebra",
        "jelly",
        "car",
        "crow",
        "trade",
        "bag",
        "roll",
        "bubble"
    )


    private fun setTimer(){
        timer = object:CountDownTimer(timeUntil, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _time.value = (millisUntilFinished / ONE_SECOND)
            }
            override fun onFinish() {
                timeUntil = COUNTDOWN_TIME
                _timeOver.value = true
            }
        }

    }


    private fun resetList() {
        if(wordList.size!=0) {
            _time.value = COUNTDOWN_TIME
            setTimer()
            _timeOver.value = false
            timer.start()
            wordList.shuffle()
            _word.value = wordList[0]
            wordList.removeAt(0)

        } else{
            _gameFinished.value = false
        }
    }

    fun addOne(){
        timer.cancel()
        _count.value = _count.value!! + 1
        resetList()
    }

    fun decreaseOne(){
        timer.cancel()
        _count.value = _count.value!! - 1
        resetList()
    }

    init{
        wasTimerPaused = false
        _count.value = 0
        _gameFinished.value = true
        _time.value = COUNTDOWN_TIME
        setTimer()
        resetList()
    }

    fun turnGameFinished(){
        _gameFinished.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun onPauseTimer(){
        timer.cancel()
        wasTimerPaused = true
    }

    fun onResumeTimer(){
        if(wasTimerPaused) {
            timeUntil = time.value!! * 1000 + 1000;
            setTimer()
            timer.start()
            wasTimerPaused = false

        }

    }

}