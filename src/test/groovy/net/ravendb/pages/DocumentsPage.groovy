package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class DocumentsPage extends Page {

    static at = {
        documentsLink
    }

    static content = {
        topNavigation { module TopNavigationBar }

        documentsLink { $("a[href='#documents']") }
        documentNameInput { $("input#documentName") }
        saveButton { $("button[title='Save (Alt+S)']") }
    }

    def createAndSaveDocument(String name) {
        documentNameInput = name
        saveButton.click()
        waitFor { saveButton.@disabled == 'true' }

        topNavigation.documentsLink.click()
    }
}
