package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.OptionEntity
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizPlayViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _options = MutableStateFlow<List<OptionEntity>>(emptyList())
    val options = _options.asStateFlow()

    // STEP 4: ANSWERS MAP
    private val _answers =
        MutableStateFlow<Map<String, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    // STEP 5: Calculate marks
    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    // async loading of data (question + options are loaded early)
    private val _optionsMap =
        MutableStateFlow<Map<String, List<OptionEntity>>>(emptyMap())

    // STEP 6 - Result Screen
    private val _correctCount = MutableStateFlow(0)
    val correctCount = _correctCount.asStateFlow()

    private val _percentage = MutableStateFlow(0)
    val percentage = _percentage.asStateFlow()

    fun loadQuiz(quizId: String) {
        viewModelScope.launch {
            val questionList = repository.getQuestionsForQuiz(quizId)
            _questions.value = questionList

            if (questionList.isNotEmpty()) {
                loadOptionsForCurrentQuestion()
            }
        }
    }

//    fun loadOptionsForCurrentQuestion() {
//        val question = _questions.value[_currentIndex.value]
//
//        viewModelScope.launch {
//            _options.value =
//                repository.getOptionsForQuestion(question.questionId)
//        }
//    }

    fun loadOptionsForCurrentQuestion() {
        val question = _questions.value[_currentIndex.value]


        viewModelScope.launch {
            val opts = repository.getOptionsForQuestion(question.questionId)

            _options.value = opts

            // cache options for scoring
            val updatedMap = _optionsMap.value.toMutableMap()
            updatedMap[question.questionId] = opts
            _optionsMap.value = updatedMap
        }
    }

    // STEP 4: SAVE / UPDATE ANSWER
    fun selectOption(questionId: String, optionId: String) {
        val updated = _answers.value.toMutableMap()
        updated[questionId] = optionId
        _answers.value = updated
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.lastIndex) {
            _currentIndex.value++
            loadOptionsForCurrentQuestion()
        }
    }

    fun previousQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            loadOptionsForCurrentQuestion()
        }
    }

    fun submitQuiz(attemptId: Int) {

        // 1. calculate final result
        calculateScore()


        // 2. persist result
        viewModelScope.launch {
            repository.saveQuizResult(
                attemptId = attemptId,
                score = score.value,
                answers = answers.value
            )
        }
    }

    // STEP 5: Calculate Score
    fun calculateScore() {
        var correct = 0

        val answersMap = answers.value
        val optionsMap = _optionsMap.value

        questions.value.forEach { question ->
            val selectedOptionId =
                answersMap[question.questionId] ?: return@forEach

            val optionsForQuestion =
                optionsMap[question.questionId] ?: return@forEach

            val selectedOption = optionsForQuestion.find {
                it.optionId == selectedOptionId
            }

            if (selectedOption?.isCorrect == true) {
                correct++
            }
        }

        // STEP 6
        _correctCount.value = correct
        _score.value = correct * 10

        val total = questions.value.size
        _percentage.value =
            if (total == 0) 0 else (correct * 100) / total
    }
}