package com.example.quizit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizit.data.QuizRepository
import com.example.quizit.data.model.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val repository = QuizRepository()
    private val questions = repository.loadQuestions()

/*    private var _score = 0
    val score: Int
        get() = _score*/

/*
    init {
        testDelay()
    }
*/

/*    private fun testDelay() {
        viewModelScope.launch {
            delay(8000)
            _score.value = 1000
        }
    }*/

    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
    get() = _score

/*    private var _currentQuestion = questions.random()
    val currentQuestion: Question
        get() = _currentQuestion*/

    private val _currentQuestion = MutableLiveData<Question>(questions.random())
    val currentQuestion: LiveData<Question>
        get() = _currentQuestion

    private val _gameEnd = MutableLiveData<Boolean>(false)
    val gameEnd: LiveData<Boolean>
        get() = _gameEnd


    fun checkAnswer(isMusician: Boolean) {
        if (isMusician == currentQuestion.value?.isMusician) {
         //  _score ++
            _score.value = _score.value?.plus(1)
            getNextQuestion()
        } else {
            _gameEnd.value = true
        }
    }

    private fun getNextQuestion() {
        val nextQuestion = questions.random()

        if (nextQuestion == currentQuestion.value) {
            getNextQuestion()
        } else {
            _currentQuestion.value = nextQuestion
        }
    }

    fun restartGame() {
       // _score = 0
        _score.value = 0
        getNextQuestion()
        _gameEnd.value = false
    }
}
