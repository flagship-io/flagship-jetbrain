package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.dataClass.*

class SchedulerNodeViewModel(
    val scheduler: Scheduler,
) {
    val schedulerStartDate = scheduler.startDate
    val schedulerStopDate = scheduler.stopDate
    val schedulerTimezone = scheduler.timezone
}
