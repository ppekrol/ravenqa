package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateDatabaseModalDialog
import net.ravendb.modules.DeleteDatabaseModalDialog

import org.openqa.selenium.interactions.Actions


class ResourcesPage extends Page {

    static at = {
        createNewResourceButton
    }

    static content = {
        createNewResourceButton { $("button[title='Create a new resource. (Alt+N)']") }
        deleteButton { $("button[title='Delete selected databases or file systems']") }
        manageYourServerButton { $("button", text:"Manage Your Server") }

        resourceContainer(required:false) { $("div.resource") }
        resourceNameContainerSelector { "a.resource-name span" }
        resourceCheckboxSelector { "div.checkbox" }

        createDatabaseModalDialog { module CreateDatabaseModalDialog }
        deleteDatabaseModalDialog { module DeleteDatabaseModalDialog }
    }

    def checkResource(String resourceName) {
        def checkbox
        resourceContainer.each {
            if(it.find(resourceNameContainerSelector).text().equals(resourceName)) {
                checkbox = it.find(resourceCheckboxSelector)
            }
        }

        assert checkbox : "No checkbox found for resource " +resourceName
        checkbox.jquery.show()

        Actions actions = new Actions(browser.driver)
        actions.moveToElement(checkbox.firstElement())
        actions.click()
        actions.perform()
    }

    def getResourceLink(String resourceName) {
        def link
        resourceContainer.each {
            if(it.find(resourceNameContainerSelector).text().equals(resourceName)) {
                link = it.find(resourceNameContainerSelector)
            }
        }
        link
    }

    def createDatabase(String dbName) {
        createNewResourceButton.click()
        waitFor { createDatabaseModalDialog.createButton.displayed }

        createDatabaseModalDialog.databaseIcon.click()
        createDatabaseModalDialog.databaseNameInput = dbName
        createDatabaseModalDialog.createButton.click()
        waitFor { createNewResourceButton.displayed }
    }

    def deleteDatabase(String dbName) {
        deleteButton.click()
        waitFor { deleteDatabaseModalDialog.header.displayed }
        deleteDatabaseModalDialog.confirmButton.click()
        waitFor { !getResourceLink(dbName) }
    }

    def checkAndDeleteDatabase(String dbName) {
        checkResource(dbName)
        deleteDatabase(dbName)
    }
}
