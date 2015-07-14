package net.ravendb

import net.ravendb.pages.CounterStoragePage;
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
     * User can create new File System with default configuration and delete it.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Click File System icon.
     * @Step Fill file system name input and click Create button.
     * @Step Click on created file system.
     * @Step Delete created file system.
     * @verification File system created and user can navigate to it, file system deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteFilesystemWithDefaultConfiguration() {
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

    /**
     * User can create new Counter Storage with default configuration and delete it.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Click Counter Storage icon.
     * @Step Fill counter storage name input and click Create button.
     * @Step Click on created counter storage.
     * @Step Delete created counter storage.
     * @verification Counter storage created and user can navigate to it, counter storage deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteCounterStorageWithDefaultConfiguration() {
        at ResourcesPage

        String lastCreatedCounterStorageName = "cs" + rand.nextInt()
        createResource(lastCreatedCounterStorageName, ResourcesPage.RESOURCE_TYPE_COUNTER_STORAGE)

        waitFor(message: "Counter storage "+lastCreatedCounterStorageName+" not found on the list.") {
            getResourceLink(lastCreatedCounterStorageName)
        }

        getResourceLink(lastCreatedCounterStorageName).click()
        waitFor { at CounterStoragePage }

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        checkAndDeleteResource(lastCreatedCounterStorageName)
    }

    /**
     * User can create new Time Series with default configuration and delete it.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Click Time Series icon.
     * @Step Fill time series name input and click Create button.
     * @Step Click on created time series.
     * @Step Delete created time series.
     * @verification Time series created and user can navigate to it, time series deleted.
     */
    @Test(groups="Smoke",enabled=false)
    void canCreateAndDeleteTimeSeriesWithDefaultConfiguration() {
        at ResourcesPage

        String lastCreatedTimeSeriesName = "ts" + rand.nextInt()
        createResource(lastCreatedTimeSeriesName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        waitFor(message: "Time series "+lastCreatedTimeSeriesName+" not found on the list.") {
            getResourceLink(lastCreatedTimeSeriesName)
        }

        getResourceLink(lastCreatedTimeSeriesName).click()
        waitFor { at FileSystemPage }

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        checkAndDeleteResource(lastCreatedTimeSeriesName)
    }
}
