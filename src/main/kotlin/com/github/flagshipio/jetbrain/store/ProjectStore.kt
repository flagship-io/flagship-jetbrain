package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.services.ProjectDataService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class ProjectStore(project: Project) {

    private var projectDataService: ProjectDataService
    private val cliCommand = CliCommand()

    init {
        projectDataService = project.getService(ProjectDataService::class.java)
    }

    fun refreshProject(): List<com.github.flagshipio.jetbrain.dataClass.Project>? {
        val projectsCLI = cliCommand.listProjectCli()
        val campaignsCLI = cliCommand.listCampaignCli()

        var projects : List<com.github.flagshipio.jetbrain.dataClass.Project> = emptyList()

        if (projectsCLI != null) {
            if (campaignsCLI != null) {
                for (projectCLI in projectsCLI) {
                    val project = com.github.flagshipio.jetbrain.dataClass.Project(projectCLI.id, projectCLI.name, ArrayList())
                    for (campaign in campaignsCLI) {
                        if (campaign.projectID == projectCLI.id){
                            project.campaign?.add(campaign)
                        }
                        }
                    projects = projects.plus(project)
                }
            }
            projectDataService.loadState(projects)
        }
        return projects
    }

    fun saveProject(project: com.github.flagshipio.jetbrain.dataClass.Project): com.github.flagshipio.jetbrain.dataClass.Project? {
        val cliResponse = cliCommand.addProjectCli(project)
        if (cliResponse != null) {
            projectDataService.saveProject(cliResponse)
            Messages.showMessageDialog("Project saved", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun editProject(project: com.github.flagshipio.jetbrain.dataClass.Project, newProject: com.github.flagshipio.jetbrain.dataClass.Project): com.github.flagshipio.jetbrain.dataClass.Project? {
        val cliResponse = project.id?.let { cliCommand.editProjectCli(it, newProject) }
        if (cliResponse != null) {
            projectDataService.editProject(project, cliResponse)
            Messages.showMessageDialog("Project edited", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun deleteProject(project: com.github.flagshipio.jetbrain.dataClass.Project): String? {
        val cliResponse = project.id?.let { cliCommand.deleteProjectCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted", true)) {
                projectDataService.deleteProject(project)
                Messages.showMessageDialog("Project deleted", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun switchProject(project: com.github.flagshipio.jetbrain.dataClass.Project, state :String): String? {
        val cliResponse = project.id?.let { cliCommand.switchProjectStateCli(it, state) }
        if (cliResponse != null) {
            if (cliResponse.contains("project status", true)) {
                Messages.showMessageDialog("Project status changed", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun getProjects(): List<com.github.flagshipio.jetbrain.dataClass.Project> {
        return projectDataService.state
    }
}