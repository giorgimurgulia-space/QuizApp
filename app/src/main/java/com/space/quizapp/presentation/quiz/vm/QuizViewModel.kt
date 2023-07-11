package com.space.quizapp.presentation.quiz.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.R
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toPointString
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.usecase.auth.GetCurrentUserIdUseCase
import com.space.quizapp.domain.usecase.quiz.*
import com.space.quizapp.domain.usecase.user.InsertUserPointUseCse
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.model.DialogItem
import com.space.quizapp.presentation.model.QuizUIModel
import com.space.quizapp.presentation.quiz.ui.QuizPagePayLoad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val startQuizUseCase: StartQuizUseCase,
    private val getNextQuestionUseCase: GetNextQuestionUseCase,
    private val getNextAnswersUseCase: GetNextAnswersUseCase,
    private val insertAnswerUseCase: InsertAnswerUseCase,
    private val getCurrentUseIdUseCase: GetCurrentUserIdUseCase,
    private val getQuizPointUseCase: GetQuizPointUseCase,
    private val insertUserPointUseCse: InsertUserPointUseCse
) : BaseViewModel() {

    private lateinit var currentQuiz: QuizUIModel

    private val _quizState = MutableStateFlow(QuizPagePayLoad())
    val quizState get() = _quizState.asStateFlow()

    fun startQuiz(subjectId: String?) {
        if (subjectId.isNullOrEmpty()) {
            setDialog(
                DialogItem.NotificationDialog(
                    icon = false,
                    title = R.string.error_message_close,
                    onCloseButton = { navigateBack() },
                )
            )
        } else {
            viewModelScope.launch {
                startQuizUseCase.invoke(subjectId).toResult().collectLatest {
                    it.onSuccess { quiz ->
                        currentQuiz = quiz.toUIModel()
                        _quizState.tryEmit(
                            _quizState.value.copy(
                                quizTitle = quiz.quizTitle, questionCount = quiz.questionsCount
                            )
                        )
                        closeLoaderDialog()
                        getQuestion()
                    }
                    it.onLoading {
                        setDialog(DialogItem.LoaderDialog())
                    }
                    it.onError {
                        setDialog(
                            DialogItem.NotificationDialog(
                                icon = false,
                                title = R.string.error_message_close,
                                onCloseButton = { navigateBack() },
                            )
                        )
                    }
                }
            }
        }
    }

    fun onAnswerClick(answerIndex: Int) {
        insertAnswerUseCase.invoke(answerIndex)

        _quizState.tryEmit(
            _quizState.value.copy(
                currentPoint = getQuizPointUseCase.invoke()
            )
        )
    }

    fun onSubmitButtonClick() {
        if (_quizState.value.isLastQuestion) finishQuiz()
        else getQuestion()
    }

    fun onCancelClick() {
        setDialog(
            DialogItem.QuestionDialog(title = R.string.cancel_quiz,
                onYesButton = { cancelQuiz() })
        )
    }

    private fun getQuestion() {
        val newQuestion = getNextQuestionUseCase.invoke()

        _quizState.tryEmit(
            _quizState.value.copy(
                question = newQuestion.questionTitle,
                correctAnswerIndex = newQuestion.correctAnswerIndex,
                questionIndex = newQuestion.questionIndex,
                isLastQuestion = newQuestion.questionIndex == currentQuiz.questionsCount
            )
        )

        viewModelScope.launch {
            getNextAnswersUseCase.invoke().map {
                it.map { answer ->
                    answer.toUIModel()
                }
            }.toResult().collectLatest {

                it.onSuccess { answers ->
                    _quizState.tryEmit(_quizState.value.copy(answers = answers))
                    closeLoaderDialog()
                }
                it.onLoading {
                    setDialog(DialogItem.LoaderDialog())
                }
                it.onError {
                    setDialog(
                        DialogItem.NotificationDialog(
                            icon = false,
                            title = R.string.error_message_close,
                            onCloseButton = { navigateBack() },
                        )
                    )
                }
            }
        }
    }

    private fun cancelQuiz() {
        val point = getQuizPointUseCase.invoke()
        if (point >= 1) finishQuiz()
        else {
            setDialog(
                DialogItem.NotificationDialog(
                    icon = true,
                    description = point.toPointString(),
                    onCloseButton = { navigateBack() })
            )
        }
    }

    private fun finishQuiz() {
        val point = getQuizPointUseCase.invoke()
        insertQuizPoint(point)
        setDialog(
            DialogItem.NotificationDialog(
                icon = true,
                title = R.string.congratulation,
                description = point.toPointString(),
                onCloseButton = { navigateBack() })
        )
    }


    private fun insertQuizPoint(point: Float) {
        viewModelScope.launch {
            insertUserPointUseCse.invoke(
                PointModel(
                    getCurrentUseIdUseCase.invoke(),
                    currentQuiz.id,
                    currentQuiz.quizTitle,
                    currentQuiz.quizDescription,
                    currentQuiz.quizIcon,
                    point,
                    currentQuiz.questionsCount
                )
            )
        }
    }

}