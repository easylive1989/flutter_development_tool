package com.github.easylive1989.flutterdevelopmenttool.actions

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.searches.MethodReferencesSearch
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.annotations.NotNull


class PopupDialogAction : AnAction() {

    override fun update(e: AnActionEvent) {
        // Set the availability based on whether a project is open
        // Set the availability based on whether a project is open
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    override fun actionPerformed(@NotNull event: AnActionEvent) {
        val currentProject: Project? = event.getProject()
        val dlgMsg = StringBuffer(event.getPresentation().getText().toString() + " Selected!")
        val dlgTitle: String = event.getPresentation().getDescription()
        val offset: Int?= CommonDataKeys.CARET.getData(DataManager.getInstance().getDataContext())?.offset;
        val psiFile: PsiFile? = event.getData(CommonDataKeys.PSI_FILE)
        val element = psiFile?.findElementAt(offset!!)
        var message = ""
        if (element != null) {
            message += java.lang.String.format("\nSelected Element: %s", element.toString())
            val containingMethod: PsiMethod? = PsiTreeUtil.getParentOfType(element, PsiMethod::class.java)
            if (containingMethod != null) {
                val result = MethodReferencesSearch.search(containingMethod)
                message += java.lang.String.format("\nSelected Method: %s", containingMethod.firstChild.language.toString())
                val containingClass: PsiClass? = containingMethod.getContainingClass()
                if (containingClass != null) {
                    message += java.lang.String.format("\nSelected Class: %s", containingClass.toString())
                }
            }
        }

        dlgMsg.append(message)
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon())
    }
}
