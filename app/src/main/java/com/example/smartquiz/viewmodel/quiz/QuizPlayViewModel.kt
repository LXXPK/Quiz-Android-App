package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.OptionEntity
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import com.example.smartquiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizPlayViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {



    private val _questions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _options = MutableStateFlow<List<OptionEntity>>(emptyList())
    val options = _options.asStateFlow()

    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount = _correctCount.asStateFlow()

    private val _percentage = MutableStateFlow(0)
    val percentage = _percentage.asStateFlow()

    private val optionsCache =
        mutableMapOf<String, List<OptionEntity>>()


    private var quizStartTimeMillis: Long = 0L
    private var timerJob: Job? = null
    private var isSubmitted = false

    private val _elapsedSeconds = MutableStateFlow(0)
    val elapsedSeconds = _elapsedSeconds.asStateFlow()

    val elapsedTimeText: StateFlow<String> =
        elapsedSeconds.map { seconds ->
            val h = seconds / 3600
            val m = (seconds % 3600) / 60
            val s = seconds % 60
            "%02d:%02d:%02d".format(h, m, s)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "00:00:00"
        )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    fun loadQuiz(quizId: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)

            try {
                resetState()
                startTimer()

                _questions.value =
                    repository.getQuestionsForQuiz(quizId)

                if (_questions.value.isNotEmpty()) {
                    loadOptionsForCurrentQuestion()
                }

                _uiState.value = UiState(isLoading = false)

            } catch (e: Exception) {
                _uiState.value = UiState(
                    isLoading = false,
                    errorMessage = "Failed to load quiz"
                )
            }
        }
    }

    private fun resetState() {
        _currentIndex.value = 0
        _answers.value = emptyMap()
        _score.value = 0
        _correctCount.value = 0
        _percentage.value = 0
        _elapsedSeconds.value = 0
        optionsCache.clear()
        isSubmitted = false
    }

    private fun startTimer() {
        quizStartTimeMillis = System.currentTimeMillis()
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _elapsedSeconds.value =
                    ((System.currentTimeMillis() - quizStartTimeMillis) / 1000).toInt()
            }
        }
    }



    private fun loadOptionsForCurrentQuestion() {
        val question = _questions.value[_currentIndex.value]

        viewModelScope.launch {
            val opts =
                repository.getOptionsForQuestion(question.questionId)

            _options.value = opts
            optionsCache[question.questionId] = opts
        }
    }

    fun selectOption(questionId: String, optionId: String) {
        _answers.value = _answers.value.toMutableMap().apply {
            put(questionId, optionId)
        }
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
        if (isSubmitted) return
        isSubmitted = true

        timerJob?.cancel()
        calculateScore()

        val timeTakenSeconds =
            ((System.currentTimeMillis() - quizStartTimeMillis) / 1000).toInt()

        viewModelScope.launch {
            repository.saveQuizResult(
                attemptId = attemptId,
                score = _score.value,
                timeTakenSeconds = timeTakenSeconds,
                answers = _answers.value
            )
        }
    }

    private fun calculateScore() {
        var correct = 0

        _questions.value.forEach { question ->
            val selectedOptionId =
                _answers.value[question.questionId] ?: return@forEach

            val option =
                optionsCache[question.questionId]
                    ?.find { it.optionId == selectedOptionId }

            if (option?.isCorrect == true) {
                correct++
            }
        }

        _correctCount.value = correct
        _score.value = correct * 10

        val total = _questions.value.size
        _percentage.value =
            if (total == 0) 0 else (correct * 100) / total
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
