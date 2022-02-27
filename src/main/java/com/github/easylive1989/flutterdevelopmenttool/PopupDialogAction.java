package com.github.easylive1989.flutterdevelopmenttool;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.lang.dart.psi.DartMethodDeclaration;
import org.jetbrains.annotations.NotNull;

public class PopupDialogAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        int offset = CommonDataKeys.CARET.getData(DataManager.getInstance().getDataContext()).getOffset();
        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        var message = "";
        if (psiFile == null) {
            show(event, message);
            return;
        }

        PsiElement element = psiFile.findElementAt(offset);
        if (element == null) {
            show(event, message);
            return;
        }

        message += String.format("\nSelected Element: %s", element);
        message += String.format("\nSelected Language: %s", element.getLanguage());

        DartMethodDeclaration method = PsiTreeUtil.getParentOfType(element, DartMethodDeclaration.class);
        if (method == null) {
            show(event, message);
            return;
        }

//      Query<PsiReference> result = MethodReferencesSearch.search(containingMethod);
        message += String.format("\nSelected Method: %s", method);

        show(event, message);
    }

    private void show(AnActionEvent event, String message) {
        Project currentProject = event.getProject();
        StringBuffer dlgMsg = new StringBuffer(event.getPresentation().getText() + " Selected!");
        String dlgTitle = event.getPresentation().getDescription();
        dlgMsg.append(message);
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }
}
