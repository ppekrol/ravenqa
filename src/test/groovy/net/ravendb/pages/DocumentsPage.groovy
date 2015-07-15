package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class DocumentsPage extends Page {

    static at = {
        selectAllDocumentsCheckbox
    }

    static content = {
        topNavigation { module TopNavigationBar }

        collectionsList { $("ul.document-collections li") }

        selectAllDocumentsCheckbox { $("button[title='Select all or none']") }
        selectAllDocumentsInfoAllDocumentsCountContainer(required:false) { $("strong[data-bind='text: documentCount']") }
        saveButton { $("button[title='Save (Alt+S)']") }
    }

    def createAndSaveDocument(String name) {
        documentNameInput = name
        saveButton.click()
        waitFor { saveButton.@disabled == 'true' }

        topNavigation.documentsLink.click()
    }
}
