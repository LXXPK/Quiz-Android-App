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

    /*
     * Entry point when quiz starts.
     * Loads all questions first, then loads options
     * for the first question.
     */
    fun loadQuiz(quizId: String) {
        viewModelScope.launch {
            val questionList =
                repository.getQuestionsForQuiz(quizId)

            _questions.value = questionList

            if (questionList.isNotEmpty()) {
                _currentIndex.value = 0
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
    fun submitQuiz(attemptId: Int) {
        calculateScore()

        viewModelScope.launch {
            repository.saveQuizResult(
                attemptId = attemptId,
                score = score.value,
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
        correctAnswers: Int
    ) {
        _correctCount.value = correctAnswers
        _score.value = correctAnswers * 10
        _percentage.value =
            if (totalQuestions == 0) 0
            else (correctAnswers * 100) / totalQuestions

        // fake questions list just to show total count
        _questions.value = List(totalQuestions) {
            QuestionEntity(
                questionId = "q$it",
                quizId = "dummy",
                questionText = "Dummy Question"
            )
        }
    }
}