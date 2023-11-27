package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.dataClass.Project

class ProjectNodeViewModel(
    val project: Project,
) {
    val projectName = project.name
    val projectId = project.id
    val projectCampaign = project.campaign
}
