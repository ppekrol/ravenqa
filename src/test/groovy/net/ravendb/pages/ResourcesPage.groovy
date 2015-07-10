package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateDatabaseModalDialog


class ResourcesPage extends Page {

    static at = {
        createNewResourceButton
    }

    static content = {
        createNewResourceButton { $("button", title:"Create a new resource. (Alt+N)") }
        resourceNameContainer { $("a.resource-name span") }

        createDatabaseModalDialog { module CreateDatabaseModalDialog }
    }

    def getDatabaseLink(String dbName) {
        def link
        resourceNameContainer.each {
            if(it.text().equals(dbName)) {
                link = it
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
}
