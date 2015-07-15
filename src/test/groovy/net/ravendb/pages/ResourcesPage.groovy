package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateResourceModalDialog
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar

import org.openqa.selenium.interactions.Actions


class ResourcesPage extends Page {

    final static String RESOURCE_TYPE_DATABASE = "db"
    final static String RESOURCE_TYPE_FILESYSTEM = "fs"
    final static String RESOURCE_TYPE_COUNTER_STORAGE = "cs"
    final static String RESOURCE_TYPE_TIME_SERIES = "ts"

    final static String FILTER_OPTION_DATABASES = "database"
    final static String FILTER_OPTION_FILESYSTEM = "filesystem"
    final static String FILTER_OPTION_COUNTER_STORAGE = "counterstorage"
    final static String FILTER_OPTION_TIME_SERIES = "timeSeries"

    static at = {
        createNewResourceButton
    }

    static content = {
        // modules
        topNavigation(required:false) { module TopNavigationBar }
        createResourceModalDialog { module CreateResourceModalDialog }
        deleteResourceModalDialog { module DeleteResourceModalDialog }

        // tool bar
        createNewResourceButton { $("button[title='Create a new resource. (Alt+N)']") }
        searchInput { $("input[title='Search for a database or file system (Alt+/)']") }
        deleteButton { $("button[title='Delete selected databases or file systems']") }
        filterSelect { $("select.form-control") }
        manageYourServerButton { $("button", text:"Manage Your Server") }

        // resources list
        resourceContainer(required:false) { $("div.resource") }
        resourceNameContainerSelector { "a.resource-name span" }
        resourceCheckboxSelector { "div.checkbox" }
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
                if(it.find(resourceNameContainerSelector).displayed) {
                    link = it.find(resourceNameContainerSelector)
                }
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
            case RESOURCE_TYPE_COUNTER_STORAGE:
                createResourceModalDialog.counterStorageIcon.click()
                createResourceModalDialog.counterStorageNameInput = name
                break
            case RESOURCE_TYPE_TIME_SERIES:
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
