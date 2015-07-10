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
}
