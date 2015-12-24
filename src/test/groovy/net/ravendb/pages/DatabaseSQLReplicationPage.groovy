package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
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

		newSQLReplicationButton { $("a[title='New SQL Replication (Alt+N)']") }
        manageConnectionStringsButton { $("a[title='Manage SQL connection strings (Alt+M)']") }
        replicationContainer { $("div.replication") }
        editSqlReplicationLink { $("a[href^='#databases/settings/editSqlReplication/OrdersAndLines']") }

        // form
        saveButton { $("button[title='Save the index (Alt+S)']") }
        nameInput { $("input[data-bind*='value: name']") }
        sourceDocumentCollectionInput { $("input#sourceDocumentCollection") }
        tableNameInput { $("input[data-bind*='value: tableName']") }
        documentKeyColumnInput { $("input[data-bind*='value: documentKeyColumn']") }
        scriptTextarea { $("pre#sqlReplicationEditor textarea") }
        showAdvancedSettingsButton { $("button[title='Switch to Advanced mode']") }
        advancedSettingsContainers { $("div.col-sm-6") }
    }

    def createAndSaveNewSQLReplication(
        String name,
        String sourceDocumentCollection,
        String tableName,
        String documentKeyColumn,
        String script,
        String provider
        ) {
        newSQLReplicationButton.click()
        waitFor { nameInput.displayed }
        nameInput = name
        sourceDocumentCollectionInput = sourceDocumentCollection
        tableNameInput = tableName
        documentKeyColumnInput = documentKeyColumn
        scriptTextarea = script
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
}
