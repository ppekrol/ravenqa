package net.ravendb.modules.manage

import geb.Module
import net.ravendb.modules.AlertTextModule;
import net.ravendb.modules.YesNoModalDialog;
import net.ravendb.utils.HtmlUtils


class ManageServerVersioning extends Module {

    final static String SUCCESS_MESSAGE = "Saved all bulk of documents"

    static content = {
        yesNoDialog { module YesNoModalDialog }
        messagesContainer { module AlertTextModule }

        saveButton { $("button[title='Save Changes(Alt+S)']") }
        createGlobalConfigurationForVersioningButton { $("button", text:"Create global configuration for Versioning") }
        removeGlobalConfigurationForVersioningButton { $("button",text:"Remove global configuration for Versioning") }
        addVersioningButton { $("button[title='Add a replication destination (Alt+N)']") }
    }

    def add() {
        addVersioningButton.click()
    }

    def save() {
        HtmlUtils.scrollToTop(browser)
        saveButton.click()
        waitFor(10, 0.1) {
            messagesContainer.containsMessage(ManageServerVersioning.SUCCESS_MESSAGE)
            saveButton.@disabled == "true"
            removeGlobalConfigurationForVersioningButton.displayed
        }
    }

    def remove() {
        HtmlUtils.scrollToTop(browser)
        removeGlobalConfigurationForVersioningButton.click()
        waitFor { yesNoDialog.yesButton.displayed }

        yesNoDialog.yesButton.click()
        waitFor(10, 0.1) {
            createGlobalConfigurationForVersioningButton.displayed
        }
    }
}
