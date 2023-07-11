package com.space.quizapp.presentation.base.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.space.quizapp.R
import com.space.quizapp.presentation.home.ui.HomeFragmentDirections
import com.space.quizapp.presentation.model.DialogItem
import com.space.quizapp.presentation.navigation.NavigationCommand
import com.space.quizapp.presentation.navigation.QuizEvent


abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<QuizEvent<NavigationCommand>>()
    val navigation: LiveData<QuizEvent<NavigationCommand>> get() = _navigation

    private val _dialog = MutableLiveData<QuizEvent<DialogItem>>()
    val dialog get() = _dialog

    fun navigate(navDirections: NavDirections) {
        _navigation.value = QuizEvent(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = QuizEvent(NavigationCommand.Back)
    }

    fun setDialog(dialog: DialogItem) {
        _dialog.value = QuizEvent(dialog)
    }

    fun closeLoaderDialog() {
        _dialog.value = QuizEvent(DialogItem.LoaderDialog(false))
    }

     fun logOut() {
        setDialog(DialogItem.QuestionDialog(title = R.string.want_log_out, onYesButton = {
            navigate(HomeFragmentDirections.actionGlobalLogOut())
        }))
    }
}