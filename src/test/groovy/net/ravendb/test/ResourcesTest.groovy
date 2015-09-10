package net.ravendb.test

import net.ravendb.pages.CounterStoragePage
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.FileSystemPage
import net.ravendb.pages.DocumentPage
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

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

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

        getResourceLink(lastCreatedTimeSeriesName).click()
        waitFor { at FileSystemPage }

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        checkAndDeleteResource(lastCreatedTimeSeriesName)
    }

    /**
     * User can search resources.
     * @Step Navigate to resources page
     * @Step Create new resource.
     * @Step Search for resource.
     * @verification Resource found.
     */
    @Test(groups="Smoke",dependsOnMethods="canCreateAndDeleteDatabaseWithDefaultConfiguration")
    void canSearchResource() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        searchInput = lastCreatedDatabaseName
        def visibleResourcesCount = 0
        resourceContainer.each {
            if(it.displayed) {
                visibleResourcesCount += 1
            }
        }
        visibleResourcesCount == 1
        assert getResourceLink(lastCreatedDatabaseName)
    }

    /**
     * User can filter resources.
     * @Step Navigate to resources page
     * @Step Create new resources.
     * @Step Filter resources.
     * @verification Resources filtered.
     */
    @Test(
        groups="Smoke",
        dependsOnMethods=[
            "canCreateAndDeleteDatabaseWithDefaultConfiguration",
            "canCreateAndDeleteFilesystemWithDefaultConfiguration",
            "canCreateAndDeleteCounterStorageWithDefaultConfiguration"
            ]
        )
    void canFilterResources() {
        at ResourcesPage

        String dbName = "db" + rand.nextInt()
        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        String fsName = "fs" + rand.nextInt()
        createResource(fsName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        String csName = "cs" + rand.nextInt()
        createResource(csName, ResourcesPage.RESOURCE_TYPE_COUNTER_STORAGE)

        String tsName = "ts" + rand.nextInt()
        createResource(tsName, ResourcesPage.RESOURCE_TYPE_TIME_SERIES)

        searchInput = ""
        filterSelect = ResourcesPage.FILTER_OPTION_DATABASES
        assert getResourceLink(dbName)
        assert !getResourceLink(fsName)
        assert !getResourceLink(csName)
        assert !getResourceLink(tsName)

        filterSelect = ResourcesPage.FILTER_OPTION_FILESYSTEM
        assert !getResourceLink(dbName)
        assert getResourceLink(fsName)
        assert !getResourceLink(csName)
        assert !getResourceLink(tsName)

        filterSelect = ResourcesPage.FILTER_OPTION_COUNTER_STORAGE
        assert !getResourceLink(dbName)
        assert !getResourceLink(fsName)
        assert getResourceLink(csName)
        assert !getResourceLink(tsName)

        filterSelect = ResourcesPage.FILTER_OPTION_TIME_SERIES
        assert !getResourceLink(dbName)
        assert !getResourceLink(fsName)
        assert !getResourceLink(csName)
        assert getResourceLink(tsName)
    }

    /**
     * User can delete multiple resources.
     * @Step Navigate to resources page
     * @Step Create new resources.
     * @Step Select all created resources.
     * @Step Click trash button.
     * @verification Resources deleted.
     */
    @Test(
        groups="Smoke",
        dependsOnMethods=[
            "canCreateAndDeleteDatabaseWithDefaultConfiguration",
            "canCreateAndDeleteFilesystemWithDefaultConfiguration",
            "canCreateAndDeleteCounterStorageWithDefaultConfiguration"
            ]
        )
    void canDeleteMultipleResources() {
        at ResourcesPage

        String dbName = "db" + rand.nextInt()
        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        String fsName = "fs" + rand.nextInt()
        createResource(fsName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        String csName = "cs" + rand.nextInt()
        createResource(csName, ResourcesPage.RESOURCE_TYPE_COUNTER_STORAGE)

        String tsName = "ts" + rand.nextInt()
        createResource(tsName, ResourcesPage.RESOURCE_TYPE_TIME_SERIES)

        deleteResources([dbName, fsName, csName, tsName])
    }

    /**
     * User can delete resources and files on disk.
     * @Step Navigate to resources page
     * @Step Create new resources.
     * @Step Delete resource selecting "Delete everything" option.
     * @verification Resource deleted and files on disk removed.
     */
    @Test(groups="Smoke",dependsOnMethods="canCreateAndDeleteDatabaseWithDefaultConfiguration")
    void canDeleteResourceAndFilesOnDisk() {
        at ResourcesPage

        String dbName = "db" + rand.nextInt()
        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        getResourceLink(dbName).click()
        waitFor { at DocumentsPage }

        newDocumentButton.click()
        waitFor { at DocumentPage }

        CharSequence documentName = "doc" + rand.nextInt()
        createAndSaveDocument(documentName)

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        checkAndDeleteResource(dbName, deleteResourceModalDialog.DELETE_OPTION_DELETE_EVERYTING)

        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        getResourceLink(dbName).click()
        waitFor { at DocumentsPage }
        sleep(5000)

        collectionsList.size() == 2
        documentsList.size() == 0
    }

    /**
     * User can disable and enable resource.
     * @Step Navigate to resources page.
     * @Step Create new resource.
     * @Step Disable and then enable resource.
     * @verification Resource disabled and enabled.
     */
    @Test(groups="Smoke")
    void canDisableEnableResource() {
        at ResourcesPage

        String dbName = "db" + rand.nextInt()
        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        disable(dbName)

        enable(dbName)
    }
}
