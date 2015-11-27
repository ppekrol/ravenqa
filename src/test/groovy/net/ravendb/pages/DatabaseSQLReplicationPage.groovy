package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar
import net.ravendb.modules.manage.ManageServeSQLReplication


class DatabaseSQLReplicationPage extends Page {

	static at = {
		newSQLReplicationButton.displayed
	}

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        messagesContainer { module AlertTextModule }
        manageServeSQLReplication { module ManageServeSQLReplication }
        deleteModalDialog { module DeleteResourceModalDialog }

		newSQLReplicationButton { $("a[title='New SQL Replication (Alt+N)']") }
        manageConnectionStringsButton { $("a[title='Manage SQL connection strings (Alt+M)']") }
        replicationContainer { $("div.replication") }
        editSqlReplicationLink { $("a[href^='#databases/settings/editSqlReplication/OrdersAndLines']") }

        // form
        saveButton { $("button[title='Save the index (Alt+S)']") }
        deleteButton { $("button[title='Delete SQL Replication']") }

        nameInput { $("input[data-bind*='value: name']") }
        sourceDocumentCollectionInput { $("input#sourceDocumentCollection") }
        tableName0Input { $("input[data-bind*='value: tableName']")[0] }
        documentKeyColumn0Input { $("input[data-bind*='value: documentKeyColumn']")[0] }
        tableName1Input { $("input[data-bind*='value: tableName']")[1] }
        documentKeyColumn1Input { $("input[data-bind*='value: documentKeyColumn']")[1] }
        addNewTableButton { $("button[title='Add new table']") }
        dropdownToogle { $("button.dropdown-toggle") }
        dropdownOption { $("ul.dropdown-menu li a") }
        scriptTextarea { $("pre#sqlReplicationEditor textarea") }
        showAdvancedSettingsButton { $("button[title='Switch to Advanced mode']") }
        advancedSettingsContainers { $("div.col-sm-6") }
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

    def createAndSaveNewSQLReplication(
        String name,
        String sourceDocumentCollection,
        String tableNameFirst,
        String documentKeyColumnFirst,
        String tableNameSecond,
        String documentKeyColumnSecond,
        String script,
        String provider
        ) {
        newSQLReplicationButton.click()
        waitFor { nameInput.displayed }
        scriptTextarea = script
        nameInput = name
        sourceDocumentCollectionInput = sourceDocumentCollection
        tableName0Input = tableNameFirst
        documentKeyColumn0Input = documentKeyColumnFirst
        addNewTableButton.click()
        tableName1Input = tableNameSecond
        documentKeyColumn1Input = documentKeyColumnSecond
        selectOption("Enabled")
        showAdvancedSettingsButton.click()

        def container = advancedSettingsContainers[1]
        def providerSelect = container.$("select")
        providerSelect.click()
        def options = providerSelect.$("option")
        options.each {
            if(it.text().equals(provider)) {
                it.click()
            }
        }

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved Raven/SqlReplication/Configuration/Or")
    }

    def delete() {
        waitFor { deleteButton.click() }
        waitFor { deleteModalDialog.confirmButton.displayed }

        deleteModalDialog.confirmButton.click()
        messagesContainer.waitForMessage("Deleted Raven/SqlReplication/Configuration")
    }
}
