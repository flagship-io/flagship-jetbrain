package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Project
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "ProjectDataService", storages = [Storage("ProjectData.xml")])
class ProjectDataService : PersistentStateComponent<List<Project>> {
    private var projectList: List<Project> = emptyList()

    override fun getState(): List<Project> {
        return projectList
    }

    override fun loadState(state: List<Project>) {
        projectList = state
    }

    fun saveProject(project: Project) {
        val newProjects = state.plus(project)
        loadState(newProjects)
    }

    fun editProject(project: Project, newProject: Project) {
        val oldProjects = state.minus(project)
        val newProjects = oldProjects.plus(newProject)

        loadState(newProjects)
    }

    fun deleteProject(project: Project) {
        val newProjects = state.minus(project)
        loadState(newProjects)
    }
}
