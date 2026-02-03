
package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.OptionEntity
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.quiz.QuizRepository
import com.example.smartquiz.ui.common.UiState
import com.example.smartquiz.utils.QuizConfig
import com.example.smartquiz.utils.QuizConfig.CURRENT_ATTEMPT_ID
import com.example.smartquiz.utils.QuizConfig.DANGER_TIME_SECONDS
import com.example.smartquiz.utils.QuizConfig.WARNING_TIME_SECONDS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizPlayViewModel @Inject constructor(
    private val repository: QuizRepository,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _questions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _options = MutableStateFlow<List<OptionEntity>>(emptyList())
    val options = _options.asStateFlow()

    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    private val _visitedQuestions = MutableStateFlow<Set<Int>>(emptySet())
    val visitedQuestions = _visitedQuestions.asStateFlow()


    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount = _correctCount.asStateFlow()

    private val _percentage = MutableStateFlow(0)
    val percentage = _percentage.asStateFlow()


    var currentAttemptId: Int = CURRENT_ATTEMPT_ID
        private set

    private var loadedAttemptId: Int? = null

    val attemptedCount: StateFlow<Int> =
        answers.map { it.size }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val unattemptedCount: StateFlow<Int> =
        combine(questions, attemptedCount) { q, a -> q.size - a }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)


    private val _showPalette = MutableStateFlow(false)
    val showPalette = _showPalette.asStateFlow()

    private val _showSubmitDialog = MutableStateFlow(false)
    val showSubmitDialog = _showSubmitDialog.asStateFlow()

    private val _showTimeoutDialog = MutableStateFlow(false)
    val showTimeoutDialog = _showTimeoutDialog.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished = _isQuizFinished.asStateFlow()


    private val totalTimeSeconds = QuizConfig.TIME_LIMIT_SECONDS

    private val _remainingSeconds = MutableStateFlow(totalTimeSeconds)
    val remainingSeconds = _remainingSeconds.asStateFlow()

    val remainingTimeText: StateFlow<String> =
        remainingSeconds.map { seconds ->
            val m = seconds / 60
            val s = seconds % 60
            "%02d:%02d".format(m, s)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, "00:00")

    val timeTakenText: StateFlow<String> =
        remainingSeconds
            .map { remaining ->
                val taken = totalTimeSeconds - remaining
                val minutes = taken / 60
                val seconds = taken % 60
                "%02d:%02d".format(minutes, seconds)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                "00:00"
            )


    val timerProgress: StateFlow<Float> =
        remainingSeconds.map { it.toFloat() / totalTimeSeconds }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 1f)

    enum class TimerColorState { NORMAL, WARNING, DANGER }

    val timerColorState: StateFlow<TimerColorState> =
        remainingSeconds.map {
            when {
                it <= DANGER_TIME_SECONDS -> TimerColorState.DANGER
                it <= WARNING_TIME_SECONDS -> TimerColorState.WARNING
                else -> TimerColorState.NORMAL
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, TimerColorState.NORMAL)

    val isBlinking: StateFlow<Boolean> =
        remainingSeconds
            .map { seconds ->
                seconds in 1..(2 * 60)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                false
            )
    private var timerJob: Job? = null
    private var timerStarted = false
    private var isSubmitted = false



    private val optionsCache = mutableMapOf<String, List<OptionEntity>>()


    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()



    fun loadQuiz(quizId: String, attemptId: Int) {
        viewModelScope.launch {
            if (loadedAttemptId == attemptId) return@launch

            loadedAttemptId = attemptId
            _uiState.value = UiState(isLoading = true)

            resetState()
            currentAttemptId = attemptId

            _questions.value = repository.getQuestionsForQuiz(quizId)

            if (_questions.value.isNotEmpty()) {
                markVisited(0)
                loadOptionsForCurrentQuestion()
            }

            startTimerOnce()
            _uiState.value = UiState(isLoading = false)
        }
    }

    private fun resetState() {
        _currentIndex.value = 0
        _answers.value = emptyMap()
        _visitedQuestions.value = emptySet()
        _score.value = 0
        _correctCount.value = 0
        _percentage.value = 0
        _remainingSeconds.value = totalTimeSeconds
        optionsCache.clear()
        isSubmitted = false
        timerStarted = false
        _isQuizFinished.value = false
        _showTimeoutDialog.value = false
        _showSubmitDialog.value = false
        _showPalette.value = false
    }



    private fun startTimerOnce() {
        if (timerStarted) return
        timerStarted = true

        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0 && !isSubmitted) {
                delay(1000)
                _remainingSeconds.value--
            }

            if (_remainingSeconds.value <= 0 && !isSubmitted) {
                _showTimeoutDialog.value = true
                submitQuiz(isTimeout = true)
            }
        }
    }


    private fun markVisited(index: Int) {
        _visitedQuestions.value = _visitedQuestions.value + index
    }

    private fun loadOptionsForCurrentQuestion() {
        val question = _questions.value[_currentIndex.value]

        optionsCache[question.questionId]?.let {
            _options.value = it
            return
        }

        viewModelScope.launch {
            val opts = repository.getOptionsForQuestion(question.questionId)
            optionsCache[question.questionId] = opts
            _options.value = opts
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
            markVisited(_currentIndex.value)
            loadOptionsForCurrentQuestion()
        }
    }

    fun previousQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            markVisited(_currentIndex.value)
            loadOptionsForCurrentQuestion()
        }
    }

    fun jumpToQuestion(index: Int) {
        if (index in _questions.value.indices) {
            _currentIndex.value = index
            markVisited(index)
            loadOptionsForCurrentQuestion()
        }
    }


    fun togglePalette() {
        _showPalette.value = !_showPalette.value
    }

    fun openSubmitDialog() {
        _showSubmitDialog.value = true
    }

    fun closeSubmitDialog() {
        _showSubmitDialog.value = false
    }



    fun submitQuiz(isTimeout: Boolean = false) {
        if (currentAttemptId <= 0 || isSubmitted) return
        isSubmitted = true

        timerJob?.cancel()
        calculateScore()

        val timeTakenSeconds = totalTimeSeconds - _remainingSeconds.value

        viewModelScope.launch {
            repository.saveQuizResult(
                attemptId = currentAttemptId,
                score = _score.value,
                timeTakenSeconds = timeTakenSeconds,
                answers = _answers.value
            )
            repository.updateUserStreak(sessionManager.getUid()!!)
        }

        _isQuizFinished.value = true
    }

    private fun calculateScore() {
        var correct = 0
        _questions.value.forEach { question ->
            val selected = _answers.value[question.questionId] ?: return@forEach
            val option =
                optionsCache[question.questionId]?.find { it.optionId == selected }
            if (option?.isCorrect == true) correct++
        }

        _correctCount.value = correct
        _score.value = correct * QuizConfig.POINTS_PER_QUESTION
        _percentage.value =
            if (_questions.value.isEmpty()) 0
            else (correct * 100) / _questions.value.size
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}
