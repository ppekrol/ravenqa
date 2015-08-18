package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateResourceModalDialog
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.DisableEnableResourceModalDialog
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

    private resourceDiv
    private resourceDropdownButton
    private resourceTakedownMenuOption

    static at = {
        createNewResourceButton.displayed
    }

    static content = {
        // modules
        topNavigation(required:false) { module TopNavigationBar }
        createResourceModalDialog { module CreateResourceModalDialog }
        deleteResourceModalDialog { module DeleteResourceModalDialog }
        disableEnableResourceModalDialog { module DisableEnableResourceModalDialog }

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
        resourceDropdownButtonSelector { "a.dropdown-toggle" }
        resourceDropDownTakedownsSelector { "li.dropdown-submenu" }
        resourceDropDownDisableEnableSelector { "span[data-bind=\"text: disabled() ? 'Enable' : 'Disable'\"]" }
        resourceDisabledTextContainerSelector { "span.stats-disabled" }
    }

    def getResource(String resourceName) {
        resourceContainer.each {
            if(it.find(resourceNameContainerSelector).text().equals(resourceName)) {
                resourceDiv = it
                resourceDropdownButton = it.find(resourceDropdownButtonSelector)
                resourceTakedownMenuOption = it.find(resourceDropDownTakedownsSelector)
            }
        }
        assert resourceDiv
        assert resourceDropdownButton
        assert resourceTakedownMenuOption
    }

    def disable(String resourceName) {
        getResource(resourceName)

        resourceDropdownButton.click()
        waitFor { resourceTakedownMenuOption.displayed }

        Actions actions = new Actions(browser.driver)
        actions.moveToElement(resourceTakedownMenuOption.firstElement())
            .click(resourceDiv.find(resourceDropDownDisableEnableSelector).firstElement())
            .build()
            .perform()
        waitFor { disableEnableResourceModalDialog.header.displayed }

        disableEnableResourceModalDialog.disableButton.click()
        waitFor { resourceDiv.find(resourceDisabledTextContainerSelector) }
    }

    def enable(String resourceName) {
        getResource(resourceName)

        resourceDropdownButton.click()
        waitFor { resourceTakedownMenuOption.displayed }

        Actions actions = new Actions(browser.driver)
        actions.moveToElement(resourceTakedownMenuOption.firstElement())
            .click(resourceDiv.find(resourceDropDownDisableEnableSelector).firstElement())
            .build()
            .perform()
        waitFor { disableEnableResourceModalDialog.header.displayed }

        disableEnableResourceModalDialog.enableButton.click()
        waitFor { !resourceDiv.find(resourceDisabledTextContainerSelector) }
    }

    def checkResources(List<String> resourcesNames) {
        resourcesNames.each {
            checkResource(it)
        }
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

        waitFor(message: "Resource "+name+" not found on the list.") {
            getResourceLink(name)
        }
    }

    def deleteResources(List<String> names) {
        checkResources(names)

        deleteButton.click()
        waitFor { deleteResourceModalDialog.header.displayed }
        deleteResourceModalDialog.confirmButton.click()

        names.each {
            waitFor { !getResourceLink(it) }
        }
    }

    def deleteResource(String name, String deleteOption=null) {
        deleteButton.click()
        waitFor { deleteResourceModalDialog.header.displayed }
        deleteResourceModalDialog.confirm(deleteOption)
        waitFor { !getResourceLink(name) }
    }

    def checkAndDeleteResource(String name, String deleteOption=null) {
        checkResource(name)
        deleteResource(name, deleteOption)
    }
}
