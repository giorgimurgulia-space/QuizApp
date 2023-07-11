package com.space.quizapp.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.space.quizapp.R
import com.space.quizapp.databinding.LayoutLoaderDialogBinding
import com.space.quizapp.databinding.LayoutNotificationDialogBinding
import com.space.quizapp.presentation.base.view.BaseDialogView
import com.space.quizapp.presentation.model.DialogItem

class DialogLoaderView(context: Context) : BaseDialogView(context) {

    override val binding = LayoutLoaderDialogBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)
    }
}
