package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.OptionEntity
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizPlayViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    /*
     * Flag to indicate whether the quiz is running in dummy mode.
     * This is NOT business logic – it’s just for UI clarity
     * (useful during demos / testing / development).
     */
    private val _isDummyMode = MutableStateFlow(true)
    val isDummyMode = _isDummyMode.asStateFlow()

    /*
     * List of questions for the quiz.
     * Loaded once when quiz starts.
     */
    private val _questions =
        MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions = _questions.asStateFlow()

    /*
     * Index of the current question being shown.
     * This drives navigation (Next / Previous).
     */
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    /*
     * Options for the currently visible question.
     * Updated every time currentIndex changes.
     */
    private val _options =
        MutableStateFlow<List<OptionEntity>>(emptyList())
    val options = _options.asStateFlow()

    /*
     * Stores user answers in-memory while playing quiz.
     * Key   → questionId
     * Value → selectedOptionId
     *
     * These answers are persisted ONLY when quiz is submitted.
     */
    private val _answers =
        MutableStateFlow<Map<String, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    /*
     * Final score after submission.
     * Stored as a fact in QuizAttemptEntity.
     */
    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    /*
     * Derived result values.
     * These are NOT stored in DB.
     * History module can derive them again later if needed.
     */
    private val _correctCount = MutableStateFlow(0)
    val correctCount = _correctCount.asStateFlow()

    private val _percentage = MutableStateFlow(0)
    val percentage = _percentage.asStateFlow()

    /*
     * Cache of options per question.
     * This is important for scoring because we should not
     * re-fetch options while calculating results.
     */
    private val _optionsMap =
        MutableStateFlow<Map<String, List<OptionEntity>>>(emptyMap())

    /* ---------------- TIME TRACKING ---------------- */

    // When the user started this quiz attempt
    private var quizStartTimeMillis: Long = 0L

    // Prevents double submission
    private var isSubmitted = false

    // Time spent by user on this quiz attempt (in seconds)
    private val _timeTakenSeconds = MutableStateFlow(0)
    val timeTakenSeconds = _timeTakenSeconds.asStateFlow()

    // Time spent so far (for live display)
    private val _elapsedSeconds = MutableStateFlow(0)
    val elapsedSeconds = _elapsedSeconds.asStateFlow()

    val elapsedTimeText = elapsedSeconds
        .map { seconds ->
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60
            "%02d:%02d:%02d".format(hours, minutes, secs)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "00:00:00"
        )

    private var timerJob: Job? = null

    /*
     * Entry point when quiz starts.
     * Loads all questions first, then loads options
     * for the first question.
     */
    fun loadQuiz(quizId: String) {
        viewModelScope.launch {

            // Reset state for safety
            _currentIndex.value = 0
            _answers.value = emptyMap()
            _optionsMap.value = emptyMap()
            _options.value = emptyList()
            isSubmitted = false

            // ⏱ Start time tracking
            quizStartTimeMillis = System.currentTimeMillis()
            timerJob?.cancel()

            timerJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    _elapsedSeconds.value =
                        ((System.currentTimeMillis() - quizStartTimeMillis) / 1000).toInt()
                }
            }

            // Load questions
            val questionList = repository.getQuestionsForQuiz(quizId)
            _questions.value = questionList

            // Load first question options
            if (questionList.isNotEmpty()) {
                loadOptionsForCurrentQuestion()
            }
        }
    }

    /*
     * Loads options for the current question index.
     * Also caches options for scoring later.
     */
    private fun loadOptionsForCurrentQuestion() {
        val index = _currentIndex.value
        val questions = _questions.value

        // Safety check to avoid crashes
        if (index !in questions.indices) return

        val question = questions[index]

        viewModelScope.launch {
            val opts =
                repository.getOptionsForQuestion(question.questionId)

            _options.value = opts

            // Cache options for result calculation
            val updated = _optionsMap.value.toMutableMap()
            updated[question.questionId] = opts
            _optionsMap.value = updated
        }
    }

    /*
     * Called when user selects an option.
     * If user changes selection, old value is replaced.
     */
    fun selectOption(questionId: String, optionId: String) {
        val updated = _answers.value.toMutableMap()
        updated[questionId] = optionId
        _answers.value = updated
    }

    /*
     * Move to next question if available.
     */
    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.lastIndex) {
            _currentIndex.value++
            loadOptionsForCurrentQuestion()
        }
    }

    /*
     * Move to previous question if available.
     */
    fun previousQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            loadOptionsForCurrentQuestion()
        }
    }

    /*
     * Final submission entry point.
     * 1. Calculate result
     * 2. Persist score and answers
     */
    /*
    * Called when user presses Submit.
    * Calculates score and time spent, then persists everything.
    */
    fun submitQuiz(attemptId: Int) {
        if (isSubmitted) return
        isSubmitted = true

        // Stop timer
        timerJob?.cancel()

        // Calculate score
        calculateScore()

        // Final time spent
        val finalTimeTakenSeconds =
            ((System.currentTimeMillis() - quizStartTimeMillis) / 1000).toInt()

        _timeTakenSeconds.value = finalTimeTakenSeconds

        viewModelScope.launch {
            repository.saveQuizResult(
                attemptId = attemptId,
                score = score.value,
                timeTakenSeconds = finalTimeTakenSeconds,
                answers = answers.value
            )
        }
    }

    /*
     * Calculates score based on selected answers
     * and option correctness.
     *
     * Unanswered questions are treated as incorrect.
     */
    private fun calculateScore() {
        var correct = 0

        questions.value.forEach { question ->
            val selectedOptionId =
                answers.value[question.questionId] ?: return@forEach


            val optionsForQuestion =
                _optionsMap.value[question.questionId] ?: return@forEach


            val selectedOption =
                optionsForQuestion.find {
                    it.optionId == selectedOptionId
                }


            if (selectedOption?.isCorrect == true) {
                correct++
            }
        }


        _correctCount.value = correct
        _score.value = correct * 10


        val total = questions.value.size
        _percentage.value =
            if (total == 0) 0 else (correct * 100) / total
    }

    /*
 * Used ONLY for dummy / preview / testing.
 * Allows setting fake result values directly.
 */
    fun setDummyResult(
        totalQuestions: Int,
        correctAnswers: Int,
        timeTakenSeconds: Int // dummy time input
    ) {
        // Result values
        _correctCount.value = correctAnswers
        _score.value = correctAnswers * 10
        _percentage.value =
            if (totalQuestions == 0) 0
            else (correctAnswers * 100) / totalQuestions


        // Dummy time spent (used by QuizResultScreen)
        _timeTakenSeconds.value = timeTakenSeconds


        // Fake questions list just to show total count in result UI
        _questions.value = List(totalQuestions) { index ->
            QuestionEntity(
                questionId = "q$index",
                quizId = "dummy",
                questionText = "Dummy Question ${index + 1}"
            )
        }
    }
}