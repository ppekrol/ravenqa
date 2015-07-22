package net.ravendb.modules

import geb.Module
import net.ravendb.utils.HtmlUtils


class ManageServerCustomFunctions extends Module {

    final static String SUCCESS_MESSAGE = "Custom functions saved"

    static content = {
        yesNoDialog { module YesNoModalDialog }
        messagesContainer { module AlertTextModule }

        saveButton { $("button[title='Save Changes (Alt+S)']") }
        createGlobalConfigurationForCustomFunctionsButton { $("button", text:"Create global configuration for Custom Functions") }
        removeGlobalConfigurationForCustomFunctionsButton { $("button", text:"Remove global configuration for Custom Functions") }

        editor { $("pre#customFunctionsEditor textarea") }
    }

    def typeJavascript(String code) {
        editor.firstElement().sendKeys(code)
    }

    def save() {
        HtmlUtils.scrollToTop(browser)
        saveButton.click()
        waitFor(10, 0.1) {
            messagesContainer.containsMessage(ManageServerCustomFunctions.SUCCESS_MESSAGE)
            saveButton.@disabled == "true"
            removeGlobalConfigurationForCustomFunctionsButton.displayed
            }
    }

    def remove() {
        HtmlUtils.scrollToTop(browser)
        removeGlobalConfigurationForCustomFunctionsButton.click()
        waitFor { yesNoDialog.yesButton.displayed }

        yesNoDialog.yesButton.click()
        waitFor(10, 0.1) {
            createGlobalConfigurationForCustomFunctionsButton.displayed
        }
    }
}
