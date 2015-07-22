package net.ravendb.modules

import geb.Module
import net.ravendb.utils.HtmlUtils


class ManageServerQuotas extends Module {

    final static String SUCCESS_MESSAGE = "Global Settings were successfully saved!"

    static content = {
        yesNoDialog { module YesNoModalDialog }
        messagesContainer { module AlertTextModule }

        saveButton { $("button[title='Save Changes(Alt+S)']") }
        createGlobalConfigurationForQuotasButton { $("button", text:"Create global configuration for Quotas") }
        removeGlobalConfigurationForQuotasButton { $("button", text:"Remove global configuration for Quotas") }

        maximumSizeInput { $("input[data-bind=\"numericValue: maximumSize,valueUpdate: 'afterkeydown'\"]") }
        warningLimitThresholdInput { $("input[data-bind=\"numericValue: warningLimitThreshold, valueUpdate: 'afterkeydown'\"]") }
        maximumNumberOfDocsInput { $("input[data-bind=\"numericValue: maxNumberOfDocs,valueUpdate: 'afterkeydown'\"]") }
        warningThresholdForDocsInput { $("input[data-bind=\"numericValue: warningThresholdForDocs,valueUpdate: 'afterkeydown'\"]") }
    }

    def addConfiguration(
        String maxSize,
        String warningLimit,
        String maxNumberOfDocs,
        String warningForDocs) {
        maximumSizeInput = maxSize
        warningLimitThresholdInput = warningLimit
        maximumNumberOfDocsInput = maxNumberOfDocs
        warningThresholdForDocsInput = warningForDocs
    }

    def save() {
        HtmlUtils.scrollToTop(browser)
        saveButton.click()
        waitFor(10, 0.1) {
            messagesContainer.containsMessage(ManageServerQuotas.SUCCESS_MESSAGE)
            saveButton.@disabled == "true"
            removeGlobalConfigurationForQuotasButton.displayed
            }
    }

    def remove() {
        HtmlUtils.scrollToTop(browser)
        removeGlobalConfigurationForQuotasButton.click()
        waitFor { yesNoDialog.yesButton.displayed }

        yesNoDialog.yesButton.click()
        waitFor(10, 0.1) {
            createGlobalConfigurationForQuotasButton.displayed
        }
    }
}
