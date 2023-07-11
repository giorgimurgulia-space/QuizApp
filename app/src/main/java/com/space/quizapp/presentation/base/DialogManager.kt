package com.space.quizapp.presentation.base

import android.content.Context
import com.space.quizapp.presentation.base.view.BaseDialogView
import com.space.quizapp.presentation.model.DialogItem
import com.space.quizapp.presentation.view.DialogLoaderView
import com.space.quizapp.presentation.view.DialogNotificationView
import com.space.quizapp.presentation.view.DialogQuestionView

object DialogManager {

    fun getDialog(dialog: DialogItem, context: Context): BaseDialogView? {
        return when (dialog.viewType) {
            DialogItem.ViewType.LOADER -> {
                dialog as DialogItem.LoaderDialog
                if (dialog.isProgressbar) {
                    DialogLoaderView(context)
                } else {
                    null
                }
            }
            DialogItem.ViewType.QUESTION -> {
                dialog as DialogItem.QuestionDialog
                DialogQuestionView(context).setContent(dialog)
            }
            DialogItem.ViewType.NOTIFICATION -> {
                dialog as DialogItem.NotificationDialog
                DialogNotificationView(context).setContent(dialog)
            }
        }
    }

}