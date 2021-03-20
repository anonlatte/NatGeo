package com.anonlatte.natgeo.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    timeout: Long = 300L,
    scope: CoroutineScope,
    function: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(timeout)
            function(param)
        }
    }
}
