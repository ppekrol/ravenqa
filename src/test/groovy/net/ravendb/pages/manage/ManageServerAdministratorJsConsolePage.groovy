package net.ravendb.pages.manage

import java.util.List;

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerAdministratorJsConsolePage extends Page {

    public final static OPERATION_DB_STATS = "Get database stats"
    public final static OPERATION_CONFIG_VALUES = "Get configuration values"
    public final static OPERATION_CHANGE_CONFIG = "Change configuration on the fly"
    public final static OPERATION_IDLE_OPERATIONS = "Run idle operations"
    public final static OPERATION_PUT_DOCUMENT = "Put document"

    public final static TARGET_DB_SYSTEM = "<system>"

    static at = {
        executeCommandButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        menu { module ManageServerMenu }

        executeCommandButton { $("button", text:"Execute command") }
        targetDatabaseInput { $("input#databaseName") }
        operationTypeButton { $("button#operationType") }
        scriptEditor { $("pre.ace_editor")[0].$("textarea") }
        resultsContainer { $("pre.ace_editor")[1].$("textarea") }
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

    def readResults() {

    }
}
