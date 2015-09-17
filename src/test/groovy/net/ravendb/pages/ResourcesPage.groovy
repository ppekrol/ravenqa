package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateEncryptionModalDialog
import net.ravendb.modules.CreateResourceModalDialog
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.DisableEnableResourceModalDialog
import net.ravendb.modules.SaveEncryptionKeyModalDialog
import net.ravendb.modules.TopNavigationBar
import net.ravendb.modules.manage.ManageServerVersioning

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

    final static String COMPRESSION_BUNDLE = "Compression"
    final static String ENCRYPTION_BUNDLE = "Encryption"
    final static String EXPIRATION_BUNDLE = "Expiration"
    final static String PERIODIC_EXPORT_BUNDLE = "Periodic Export"
    final static String QUOTAS_BUNDLE = "Quotas"
    final static String REPLICATION_BUNDLE = "Replication"
    final static String SCRIPTED_INDEX_BUNDLE = "Scripted Index"
    final static String SQL_REPLICATION_BUNDLE = "SQL Replication"
    final static String VERSIONING_BUNDLE = "Versioning"

    final static String FILESYSTEM_ENCRYPTION_BUNDLE = "Filesystem Encryption"
    final static String FILESYSTEM_VERSIONING_BUNDLE = "Filesystem Versioning"

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
        versioningModalDialog { module ManageServerVersioning }
        createEncryptionModalDialog { module CreateEncryptionModalDialog }
        saveEncryptionModalDialog { module SaveEncryptionKeyModalDialog }

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

    def createResource(String name, String resourceType, def bundles = []) {
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

        bundles.each {
            switch(it) {
                case COMPRESSION_BUNDLE:
                    createResourceModalDialog.compressionButton.click()
                    break
                case ENCRYPTION_BUNDLE:
                    createResourceModalDialog.encryptionButton.click()
                    break
                case EXPIRATION_BUNDLE:
                    createResourceModalDialog.expirationButton.click()
                    break
                case PERIODIC_EXPORT_BUNDLE:
                    createResourceModalDialog.periodicExportButton.click()
                    break
                case QUOTAS_BUNDLE:
                    createResourceModalDialog.quotasButton.click()
                    break
                case REPLICATION_BUNDLE:
                    createResourceModalDialog.replicationButton.click()
                    break
                case SCRIPTED_INDEX_BUNDLE:
                    createResourceModalDialog.scriptedIndexButton.click()
                    break
                case SQL_REPLICATION_BUNDLE:
                    createResourceModalDialog.sqlReplicationButton.click()
                    break
                case VERSIONING_BUNDLE:
                    createResourceModalDialog.versioningButton.click()
                    break
                case FILESYSTEM_VERSIONING_BUNDLE:
                    createResourceModalDialog.filesystemVersioningButton.click()
                    break
                case FILESYSTEM_ENCRYPTION_BUNDLE:
                    createResourceModalDialog.filesystemEncryptionButton.click()
                    break
            }
        }

        createResourceModalDialog.createButton.click()
        waitFor { createNewResourceButton.displayed }
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
