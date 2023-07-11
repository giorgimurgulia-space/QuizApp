package com.space.quizapp.presentation.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.space.quizapp.databinding.LayoutQuestionDialogBinding
import com.space.quizapp.presentation.base.view.BaseDialogView
import com.space.quizapp.presentation.model.DialogItem

class DialogQuestionView(context: Context) : BaseDialogView(context) {

    override val binding = LayoutQuestionDialogBinding.inflate(LayoutInflater.from(context))

    fun setContent(dialog: DialogItem.QuestionDialog): DialogQuestionView {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)

        binding.titleText.text = context.getString(dialog.title)
        binding.yesButton.setOnClickListener {
            dialog.onYesButton()
            dismiss()
        }
        binding.noButton.setOnClickListener {
            dismiss()
        }
        setContentView(binding.root)
        return this
    }
}
