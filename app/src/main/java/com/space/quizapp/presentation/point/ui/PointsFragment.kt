package com.space.quizapp.presentation.point.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.databinding.FragmentPointsBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.point.adapter.PointAdapter
import com.space.quizapp.presentation.point.vm.PointsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PointsFragment :
    BaseFragment<FragmentPointsBinding, PointsViewModel>(FragmentPointsBinding::inflate) {

    override val viewModel: PointsViewModel by viewModels()
    private val adapter = PointAdapter()

    override fun onBind() {
        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun setListeners() {
        binding.backImage.setOnClickListener {
            viewModel.navigateBack()
        }
        binding.logOutButton.setOnClickListener {
            viewModel.logOut()
        }
    }

    override fun setObserves() {
        collectFlow(viewModel.points) {
            if (it.isEmpty()) {
                binding.noPointsText.visibility = View.VISIBLE
            } else {
                binding.noPointsText.visibility = View.GONE
                adapter.submitList(it)
            }
        }
    }
}