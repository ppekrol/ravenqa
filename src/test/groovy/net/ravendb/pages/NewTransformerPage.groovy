package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class NewTransformerPage extends Page {

    final static String TRANSFORMER_DELETE_SUCCESS = "Deleted "

    static at = {
        transformerNameInput.displayed
    }

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        messagesContainer { module AlertTextModule }
        deleteTransformerModalDialog { module DeleteResourceModalDialog }

        //tool bar
        saveButton { $("button[title='Save the transformer (Alt+S)']") }
        formatTransformerButton { $("button[title='Format transformer']") }
        deleteTransformerButton { $("button[title='Delete the transformer (Alt+Shift+Delete)']") }

        // form
        transformerNameInput { $("input#transformerName") }
        transformerAceEditor { $("pre#transformerAceEditor textarea") }
    }

    def createAndSaveTransformer(String name, List<String> transform) {
        transformerNameInput = name
        transform.eachWithIndex { transformText, index ->
            transformerAceEditor[index].firstElement().sendKeys(transformText)
        }

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved " + name)
    }

    def formatTransformer(String name) {
        formatTransformerButton.click()
        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved " + name)
    }
}
