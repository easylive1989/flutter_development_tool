package com.github.easylive1989.flutterdevelopmenttool;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.searches.MethodReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

public class PopupDialogAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();
        StringBuffer dlgMsg = new StringBuffer(event.getPresentation().getText() + " Selected!");
        String dlgTitle = event.getPresentation().getDescription();
        int offset = CommonDataKeys.CARET.getData(DataManager.getInstance().getDataContext()).getOffset();
        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        PsiElement element = psiFile.findElementAt(offset);
        var message = "";
        if (element != null) {
            message += java.lang.String.format("\nSelected Element: %s", element);
            PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element);
            if (containingMethod != null) {
                Query<PsiReference> result = MethodReferencesSearch.search(containingMethod);
                message += String.format("\nSelected Method: %s", containingMethod.getFirstChild().getLanguage());
                PsiClass containingClass = containingMethod.getContainingClass();
                if (containingClass != null) {
                    message += java.lang.String.format("\nSelected Class: %s", containingClass);
                }
            }
        }

        dlgMsg.append(message);
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }
}
