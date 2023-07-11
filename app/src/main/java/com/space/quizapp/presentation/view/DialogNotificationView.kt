package com.space.quizapp.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.space.quizapp.R
import com.space.quizapp.databinding.LayoutNotificationDialogBinding
import com.space.quizapp.presentation.base.view.BaseDialogView
import com.space.quizapp.presentation.model.DialogItem

class DialogNotificationView(context: Context) : BaseDialogView(context) {

    override val binding = LayoutNotificationDialogBinding.inflate(LayoutInflater.from(context))

    fun setContent(dialog: DialogItem.NotificationDialog): DialogNotificationView {
        if (!dialog.icon)
            binding.iconText.visibility = View.GONE

        if (dialog.title != null) {
            binding.titleText.text = context.getString(dialog.title)
        } else {
            binding.titleText.visibility = View.GONE
        }

        if (dialog.description.isNullOrEmpty()) {
            binding.descriptionText.visibility = View.GONE
        } else {
            binding.descriptionText.text = String.format(
                context.resources.getString(R.string.your_point),
                dialog.description
            )

        }

        binding.closeButton.setOnClickListener {
            dialog.onCloseButton()
            dismiss()
        }

        setContentView(binding.root)
        return this
    }
}
