package com.userstipa.currency.ui.uitls

import androidx.transition.Transition

//This listener is triggered in two scenarios.
// The first is when the transition animation is completed.
// The second one is after change configuration

class OnTransitionEnd(
    isFirstCreateView: Boolean,
    private var listener: () -> Unit
) : Transition.TransitionListener {

    private var isAlreadyInvoked = false

    init {
        if (!isFirstCreateView && !isAlreadyInvoked) {
            invokeListener()
        }
    }

    override fun onTransitionStart(transition: Transition) {
        // No action needed
    }

    override fun onTransitionCancel(transition: Transition) {
        // No action needed
    }

    override fun onTransitionPause(transition: Transition) {
        // No action needed
    }

    override fun onTransitionResume(transition: Transition) {
        // No action needed
    }

    override fun onTransitionEnd(transition: Transition) {
        if (!isAlreadyInvoked) {
            invokeListener()
        }
    }

    private fun invokeListener() {
        listener.invoke()
        isAlreadyInvoked = true
    }
}