package net.ravendb

import net.ravendb.pages.DatabasePage
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test

class ResourcesTest extends TestBase {

    /**
     * User can create new database with default configuration.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Fill database name input and click Create button.
     * @verification Database created and user can navigate to it.
     */
    @Test(groups="Smoke")
    void canCreateDatabaseWithDefaultConfiguration() {
        at ResourcesPage

        createNewResourceButton.click()
        waitFor { createDatabaseModalDialog.createButton.displayed }

        String dbName = "db" + rand.nextInt()
        createDatabaseModalDialog.databaseIcon.click()
        createDatabaseModalDialog.databaseNameInput = dbName
        createDatabaseModalDialog.createButton.click()
        waitFor { createNewResourceButton.displayed }

        waitFor(message: "Database "+dbName+" not found on the list.") {
            getDatabaseLink(dbName)
        }

        getDatabaseLink(dbName).click()
        waitFor { at DatabasePage }
    }
}
