package net.ravendb.modules

import geb.Module


class QuotasModalDialog extends Module {

    static content = {
        header { $("h4", text:"Database Settings") }

        saveButton { $("button[title='Save Changes(Alt+S)']") }
        maximumSizeInput { $("div[data-bind='with: maximumSize'] input[data-bind*='numericValue: effectiveValue']") }
        warningLimitThresholdInput { $("div[data-bind='with: warningLimitThreshold'] input[data-bind*='numericValue: effectiveValue']") }
        maximumNumberOfDocsInput { $("div[data-bind='with: maxNumberOfDocs'] input[data-bind*='numericValue: effectiveValue']") }
        warningThresholdForDocsInput { $("div[data-bind='with: warningThresholdForDocs'] input[data-bind*='numericValue: effectiveValue']") }
        closeButton { $("button[data-bind='click: close']") }
    }

    def addAndSaveQuotasConfiguration(
        String maxSize,
        String warningLimit,
        String maxNumberOfDocs,
        String warningForDocs) {
        maximumSizeInput = maxSize
        warningLimitThresholdInput = warningLimit
        maximumNumberOfDocsInput = maxNumberOfDocs
        warningThresholdForDocsInput = warningForDocs

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()
    }
}
