package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.manage.ManageServerMenu


class ManageServerAdministratorJsConsolePage extends Page {

    public final static OPERATION_DB_STATS = "Get database stats"
    public final static OPERATION_CONFIG_VALUES = "Get configuration values"
    public final static OPERATION_CHANGE_CONFIG = "Change configuration on the fly"
    public final static OPERATION_IDLE_OPERATIONS = "Run idle operations"
    public final static OPERATION_PUT_DOCUMENT = "Put document"

    public final static TARGET_DB_SYSTEM = "<system>"

    public final static SUCCESS_MESSAGE = "Script executed"

    static at = {
        executeCommandButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        // modules
        menu { module ManageServerMenu }
        alert { module AlertTextModule }

        executeCommandButton { $("button", text:"Execute command") }
        targetDatabaseInput { $("input#databaseName") }
        operationTypeButton { $("button#operationType") }
        scriptEditor { $("pre.ace_editor")[0].$("textarea") }
        resultsContainer { $("pre.ace_editor")[1].$("textarea") }
        resultsContainerLines { resultsContainer.parent().$(".ace_line") }
    }

    def selectOperation(String operation) {
        operationTypeButton.click()

        def itemToClick
        operationTypeButton.parent().$("a").each {
            if(it.text().equals(operation)) {
                itemToClick = it
            }
        }

        if(itemToClick) {
            itemToClick.click()
        }
    }

    def typeScript(String script) {
        scriptEditor.firstElement().sendKeys(map)
    }
}
