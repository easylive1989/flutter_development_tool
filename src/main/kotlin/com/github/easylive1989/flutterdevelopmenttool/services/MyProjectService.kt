package com.github.easylive1989.flutterdevelopmenttool.services

import com.intellij.openapi.project.Project
import com.github.easylive1989.flutterdevelopmenttool.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
