package net.ravendb

import net.ravendb.pages.DatabasePage
import net.ravendb.pages.FileSystemPage;
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test

class ResourcesTest extends TestBase {

    /**
     * User can create new database with default configuration and delete it.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Fill database name input and click Create button.
     * @Step Click on created database.
     * @Step Delete created database.
     * @verification Database created and user can navigate to it, database deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteDatabaseWithDefaultConfiguration() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        waitFor(message: "Database "+lastCreatedDatabaseName+" not found on the list.") {
            getResourceLink(lastCreatedDatabaseName)
        }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DatabasePage }

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        checkAndDeleteResource(lastCreatedDatabaseName)
    }

    /**
     * User can create new filesystem with default configuration and delete it.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Click Filesystem icon.
     * @Step Fill database name input and click Create button.
     * @Step Click on created database.
     * @Step Delete created database.
     * @verification Database created and user can navigate to it, database deleted.
     */
    void canCreateAndDeleteFilesystemWithDefaultCOnfiguration() {
        at ResourcesPage

        String lastCreatedFilesystemName = "fs" + rand.nextInt()
        createResource(lastCreatedFilesystemName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        waitFor(message: "Filesystem "+lastCreatedFilesystemName+" not found on the list.") {
            getResourceLink(lastCreatedFilesystemName)
        }

        getResourceLink(lastCreatedFilesystemName).click()
        waitFor { at FileSystemPage }

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        checkAndDeleteResource(lastCreatedFilesystemName)
    }
}
