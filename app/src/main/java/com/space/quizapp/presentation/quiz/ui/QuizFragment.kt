package com.space.quizapp.presentation.quiz.ui

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.common.mapper.toPointString
import com.space.quizapp.common.util.QuizConstants.SUBJECT_ID
import com.space.quizapp.databinding.FragmentQuizBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.quiz.adapter.AnswerAdapter
import com.space.quizapp.presentation.quiz.vm.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.format


@AndroidEntryPoint
class QuizFragment :
    BaseFragment<FragmentQuizBinding, QuizViewModel>(FragmentQuizBinding::inflate) {

    override val viewModel: QuizViewModel by viewModels()
    private val adapter = AnswerAdapter(onItemClicked = {
        viewModel.onAnswerClick(it)
    })

    override fun onBind() {
        val subjectId = arguments?.getString(SUBJECT_ID)
        viewModel.startQuiz(subjectId)

        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun setObserves() {
        collectFlow(viewModel.quizState) {
            binding.questionText.text = it.question
            adapter.submitList(it.answers)

            binding.currentPointText.text =
                format(resources.getString(R.string.current_point), it.currentPoint.toPointString())

            binding.questionProgressText.text =
                format(
                    resources.getString(R.string.question_progress),
                    it.questionIndex.toString(),
                    it.questionCount.toString()
                )

            binding.quizProgressBar.setProgress(it.questionIndex, true)
            binding.quizProgressBar.max = it.questionCount

            adapter.correctAnswer = it.correctAnswerIndex
            binding.titleText.text = it.quizTitle

            if (it.isLastQuestion)
                changeSubmitButtonToFinish()
        }
    }

    override fun setListeners() {
        binding.cancelImage.setOnClickListener {
            viewModel.onCancelClick()
        }

        binding.submitButton.setOnClickListener {
            viewModel.onSubmitButtonClick()
        }
    }

    private fun changeSubmitButtonToFinish() {
        binding.submitButton.text =
            resources.getString(R.string.finish)
    }
}