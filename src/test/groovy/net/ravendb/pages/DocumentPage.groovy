package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class DocumentPage extends Page {

    static at = {
        documentNameInput
    }

    static content = {
        topNavigation { module TopNavigationBar }

        saveButton { $("button[title='Save (Alt+S)']") }
        csharpButton { $("button[data-bind='click: generateCode, visible: lodaedDocumentName']") }

        documentNameInput { $("input#documentName") }

        // generated class modal dialog
        generatedClassModalHeader(required:false) { $("h4", text:"Generated Class") }
        generatedClassCodeContainer(required:false) { generatedClassModalHeader.parent().parent().$("textarea") }
    }

    def createAndSaveDocument(String name) {
        documentNameInput = name
        saveButton.click()
        waitFor { saveButton.@disabled == 'true' }
    }

    def clickRecentDocument(String name) {
        def docToClick
        $("div[data-bind='visible:topRecentDocuments().length > 0']").$("span").each {
            if(it.text().equals(name)) {
                docToClick = it
            }
        }

        if(docToClick) {
            docToClick.click()
        }
    }
}
