package net.ravendb

import net.ravendb.pages.DatabasePage
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test

class ResourcesTest extends TestBase {

    private String lastCreatedDatabaseName

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

        lastCreatedDatabaseName = "db" + rand.nextInt()
        createDatabase(lastCreatedDatabaseName)

        waitFor(message: "Database "+lastCreatedDatabaseName+" not found on the list.") {
            getResourceLink(lastCreatedDatabaseName)
        }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DatabasePage }
    }

    @Test(groups="Smoke",dependsOnMethods="canCreateDatabaseWithDefaultConfiguration")
    void canDeleteDatabase() {
        at ResourcesPage

        checkAndDeleteDatabase(lastCreatedDatabaseName)
    }
}
