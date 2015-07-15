package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class NewDocumentPage extends Page {

    static at = {
        documentNameInput
    }

    static content = {
        topNavigation { module TopNavigationBar }

        saveButton { $("button[title='Save (Alt+S)']") }
        documentNameInput { $("input#documentName") }
    }


    def createAndSaveDocument(String name) {
        documentNameInput = name
        saveButton.click()
        waitFor { saveButton.@disabled == 'true' }
    }
}
