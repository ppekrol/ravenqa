package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar


class DatabaseReplicationPage extends Page {

	static at = {
		saveChangesButton.displayed
	}

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        messagesContainer { module AlertTextModule }

        // form
        saveChangesButton { $("button[title='Save changes (Alt+S)']") }
        addDestinationButton { $("button[title='Add a replication destination (Alt+N)']") }
        urlInput { $("input[title='The address of the server to replicate to']") }
        databaseInput { $("input[title='The name of the database on the destination server to replicate to']") }
    }

    def addReplicationDestination(String urlToReplicateTo, String databaseNameToReplicateTo) {
        addDestinationButton.click()
        urlInput = urlToReplicateTo
        databaseInput = databaseNameToReplicateTo

        waitFor { !(saveChangesButton.@disabled == 'true') }
        saveChangesButton.click()

        messagesContainer.waitForMessage("Saved Replication settings.")
    }
}