package com.github.flagshipio.jetbrain.toolWindow.project.campaign.scheduler

import com.github.flagshipio.jetbrain.dataClass.Scheduler

class SchedulerNodeViewModel(
    val scheduler: Scheduler,
) {
    val schedulerStartDate = scheduler.startDate
    val schedulerStopDate = scheduler.stopDate
    val schedulerTimezone = scheduler.timezone
}
