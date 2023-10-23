package com.nyx.mypurchases.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Timer(
    delayMillis: Long = 0,
    repeatMillis: Long = 0,
    backgroundWork: () -> Unit,
    mainThreadWork: (() -> Unit)? = null,
) {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(
        delayMillis: Long,
        repeatMillis: Long,
        action: () -> Unit,
    ) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private val timer: Job =
        startCoroutineTimer(delayMillis = delayMillis, repeatMillis = repeatMillis) {
            backgroundWork()

            scope.launch(Dispatchers.Main) {
                mainThreadWork?.invoke()
            }
        }

    fun startTimer() {
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}