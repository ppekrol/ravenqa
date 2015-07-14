package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateResourceModalDialog
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar

import org.openqa.selenium.interactions.Actions


class ResourcesPage extends Page {

    final static String RESOURCE_TYPE_DATABASE = "db"
    final static String RESOURCE_TYPE_FILESYSTEM = "fs"
    final static String RESOURCE_COUNTER_STORAGE = "cs"
    final static String RESOURCE_TIME_SERIES = "ts"

    static at = {
        createNewResourceButton
    }

    static content = {
        topNavigation(required:false) { module TopNavigationBar }

        createNewResourceButton { $("button[title='Create a new resource. (Alt+N)']") }
        deleteButton { $("button[title='Delete selected databases or file systems']") }
        manageYourServerButton { $("button", text:"Manage Your Server") }

        resourceContainer(required:false) { $("div.resource") }
        resourceNameContainerSelector { "a.resource-name span" }
        resourceCheckboxSelector { "div.checkbox" }

        createResourceModalDialog { module CreateResourceModalDialog }
        deleteResourceModalDialog { module DeleteResourceModalDialog }
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

    def createResource(String name, String resourceType) {
        createNewResourceButton.click()
        waitFor { createResourceModalDialog.createButton.displayed }

        switch(resourceType) {
            case RESOURCE_TYPE_DATABASE:
                createResourceModalDialog.databaseIcon.click()
                createResourceModalDialog.databaseNameInput = name
                break
            case RESOURCE_TYPE_FILESYSTEM:
                createResourceModalDialog.filesystemIcon.click()
                createResourceModalDialog.filesystemNameInput = name
                break
            case RESOURCE_COUNTER_STORAGE:
                createResourceModalDialog.counterStorageIcon.click()
                createResourceModalDialog.counterStorageNameInput = name
                break
            case RESOURCE_TIME_SERIES:
                createResourceModalDialog.timeSeriesIcon.click()
                createResourceModalDialog.timeSeriesNameInput = name
                break
        }

        createResourceModalDialog.createButton.click()
        waitFor { createNewResourceButton.displayed }
    }

    def deleteResource(String name) {
        deleteButton.click()
        waitFor { deleteResourceModalDialog.header.displayed }
        deleteResourceModalDialog.confirmButton.click()
        waitFor { !getResourceLink(name) }
    }

    def checkAndDeleteResource(String name) {
        checkResource(name)
        deleteResource(name)
    }
}
