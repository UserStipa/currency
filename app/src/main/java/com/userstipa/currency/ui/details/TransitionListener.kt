package com.userstipa.currency.ui.details

import androidx.transition.Transition

class TransitionListener(
    private val onEnd: () -> Unit
) : Transition.TransitionListener {
    override fun onTransitionStart(transition: Transition) {}
    override fun onTransitionCancel(transition: Transition) {}
    override fun onTransitionPause(transition: Transition) {}
    override fun onTransitionResume(transition: Transition) {}
    override fun onTransitionEnd(transition: Transition) {
        onEnd.invoke()
    }
}