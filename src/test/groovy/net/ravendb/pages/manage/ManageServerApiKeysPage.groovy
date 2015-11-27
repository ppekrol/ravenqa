package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.manage.ManageServerMenu


class ManageServerApiKeysPage extends Page {

    static at = {
        createNewAPIKeyButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }
        messagesContainer { module AlertTextModule }

        createNewAPIKeyButton { $("button[title='Create a new API Key (Alt+N)']") }
        nameInput { $("input[data-bind*='value: name']") }
        resourceNameFirstInput { $("input#databaseName00") }
        resourceNameSecondInput { $("input#databaseName01") }
        dropdownToogle { $("button.dropdown-toggle") }
        dropdownOption { $("ul.dropdown-menu li a") }
        addDatabaseButton { $("button[title='Add database']") }
        saveButton { $("button[title='Save changes (Alt+S)']") }
        removeButton { $("button[title='Remove this API key']") }
    }

    def selectOption(CharSequence name) {
        def container
        dropdownToogle.each {
            if(it.getAttribute("innerHTML").contains(name)) {
                container = it
            }
        }
        if(container) {
            container.click()

            def option
            dropdownOption.each {
                if(it.getAttribute("innerHTML").contains(name)) {
                    option = it
                }
            }
            assert option
            option.click()
        }
    }

    def createNewApiKey(String name, String resourceFirst, String resourceSecond) {
        waitFor { createNewAPIKeyButton.click() }
        nameInput = name
        selectOption("Enabled")
        resourceNameFirstInput = resourceFirst
        addDatabaseButton.click()
        resourceNameSecondInput = resourceSecond

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved API keys.")
    }

    def deleteApiKey() {
        waitFor { removeButton.click() }

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved API keys.")
    }
}
