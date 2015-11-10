package net.ravendb.test

import net.ravendb.modules.manage.ManageServeSQLReplication
import net.ravendb.pages.ConfigurationsPage
import net.ravendb.pages.CounterStoragePage
import net.ravendb.pages.DatabaseSQLReplicationPage
import net.ravendb.pages.DocumentPage
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.FileSystemPage
import net.ravendb.pages.IndexesPage
import net.ravendb.pages.NewIndexPage
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.SettingsPage

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
    @Test(groups="Smoke",enabled=false)
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
            "canCreateAndDeleteFilesystemWithDefaultConfiguration"
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
            "canCreateAndDeleteFilesystemWithDefaultConfiguration"
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

    @Test(groups="Smoke")
    void canCreateDatabaseAndFilesystemWithVersioning() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.VERSIONING_BUNDLE])
        waitFor { versioningModalDialog.addVersioningButton.displayed }

        versioningModalDialog.closeButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        assert databaseVersioningLink.displayed

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        String lastCreatedFileSystem = "fs" + rand.nextInt()
        createResource(lastCreatedFileSystem, ResourcesPage.RESOURCE_TYPE_FILESYSTEM, [ResourcesPage.FILESYSTEM_VERSIONING_BUNDLE])
        waitFor { versioningModalDialog.closeButton.displayed }

        versioningModalDialog.closeButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedFileSystem).click()
        waitFor { at FileSystemPage }

        topNavigation.filesystemSettingsLink.click()
        waitFor { at SettingsPage }
        assert filesystemVersioningLink.displayed
    }

    @Test(groups="Smoke")
    void canCreateDatabaseAndFilesystemWithEncryption() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.ENCRYPTION_BUNDLE])
        waitFor { createEncryptionModalDialog.header.displayed }

        createEncryptionModalDialog.saveButton.click()
        waitFor { saveEncryptionModalDialog.header.displayed }

        saveEncryptionModalDialog.okButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        clickDocument("Raven/Encryption/Verification")
        waitFor { at DocumentPage }

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        String lastCreatedFileSystem = "fs" + rand.nextInt()
        createResource(lastCreatedFileSystem, ResourcesPage.RESOURCE_TYPE_FILESYSTEM, [ResourcesPage.FILESYSTEM_ENCRYPTION_BUNDLE])
        waitFor { createEncryptionModalDialog.header.displayed }

        createEncryptionModalDialog.saveButton.click()
        waitFor { saveEncryptionModalDialog.header.displayed }

        saveEncryptionModalDialog.okButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedFileSystem).click()
        waitFor { at FileSystemPage }

        topNavigation.configurationsLink.click()
        waitFor { at ConfigurationsPage }
        assert encryptionConfigurationLink.displayed
    }

    @Test(groups="Smoke")
    void canEditDatabaseSettings() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE)
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        waitFor { editDatabaseSettingsButton.displayed }

        editDatabaseSettingsButton.click()
        waitFor { areYouSure.okButton.displayed }

        areYouSure.okButton.click()
        waitFor { !editDatabaseSettingsButton.displayed }
        waitFor { saveButton.displayed }
    }

    @Test(groups="Smoke")
    void canCreateDatabaseWithCompression() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.COMPRESSION_BUNDLE])

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        waitFor { databaseSettingsBundleContainer.displayed }

        assert isBundlePresent("Compression")    }

    @Test(groups="Smoke")
    void canCreateDatabaseWithScriptedIndex() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.SCRIPTED_INDEX_BUNDLE])
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        newIndexButton.click()
        waitFor { at NewIndexPage }

        String indexName = "i" + rand.nextInt()
        def maps = [
            """
            from order in docs.Orders
            where order.IsShipped
            select new {order.Date,order.Amount,RegionId = order.Region.Id}
            """.toString()
        ]
        def indexScript = [
            """
            var company = LoadDocument(this.Company);
            if (company == null)
                    return;
            company.Orders = { Count: this.Count, Total: this.Total };
            PutDocument(this.Company, company);
            """.toString()
        ]
        def deleteScript = [
            """
            var company = LoadDocument(key);
            if (company == null)
                    return;
            delete company.Orders;
            PutDocument(key, company);
            """.toString()
        ]
        createAndSaveScriptedIndex(indexName, maps, indexScript, deleteScript)
        waitFor { at NewIndexPage }

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }
        waitFor { getIndexLink(indexName) }
    }

    /**
     * User can create new database with quotas and then edit quotas.
     * @Step Navigate to resources page
     * @Step Create new resource with Quotas bundle.
     * @Step Add quotas configuration.
     * @Step Go to Documents, then to Settings.
     * @Step Edit quotas configuration.
     * @verification Database with quotas created and edited.
     */
    @Test(groups="Smoke")
    void canCreateAndEditDatabaseWithQuotas() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.QUOTAS_BUNDLE])
        waitFor { quotasModalDialog.header.displayed }

        quotasModalDialog.addAndSaveQuotasConfiguration("1", "2", "1", "2")
        quotasModalDialog.closeButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        assert quotasLink.displayed

        quotasLink.click()
        waitFor { quotasModalDialog.saveButton.displayed }
        quotasModalDialog.addAndSaveQuotasConfiguration("2", "3", "4", "5")
    }

    @Test(groups="Smoke")
    void canCreateDatabaseSqlReplication() {
        at ResourcesPage

        String stringName = "SQLEXPRESS"
        String provider = ManageServeSQLReplication.SQL_PROVIDER_SQLCLIENT

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.SQL_REPLICATION_BUNDLE])
        waitFor { manageServeSQLReplication.saveButton.displayed }

        manageServeSQLReplication.addConnection(stringName, provider)
        manageServeSQLReplication.saveConnectionStringSettings()
        manageServeSQLReplication.closeButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        assert databaseSQLReplicationLink.displayed
    }


    /**
     * User can setup database SQL replication.
     * @Step Navigate to resources page.
     * @Step Create new resource with SQL Replication bundle.
     * @Step Go to Documents, then to Settings.
     * @Step Add connection string and create new SQL Replication.
     * @Step Delete SQL Replication.
     * @verification SQL Replication created and deleted.
     */
    @Test(groups="Smoke")
    void canSetupAndDeleteDatabaseSqlReplication() {
        at ResourcesPage

        String stringName = "SQLEXPRESS"
        String name = "OrdersAndLines"
        String sourceDocumentCollection = "Orders"
        String tableNameFirst = "Orders"
        String documentKeyColumnFirst = "Id"
        String tableNameSecond = "Product"
        String documentKeyColumnSecond = "Count"
        String provider = ManageServeSQLReplication.SQL_PROVIDER_SQLCLIENT
        def script =
        """
            var orderData = {Id: documentId,OrderLinesCount: this.Lines.length,TotalCost: 0};
        """.toString()

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.SQL_REPLICATION_BUNDLE])
        waitFor { manageServeSQLReplication.closeButton.displayed }

        manageServeSQLReplication.closeButton.click()
        waitFor { at ResourcesPage }

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }
        assert getRowsCount() == 0

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        assert databaseSQLReplicationLink.displayed

        databaseSQLReplicationLink.click()
        waitFor { at DatabaseSQLReplicationPage }

        manageConnectionStringsButton.click()
        waitFor { manageServeSQLReplication.saveButton.displayed }
        manageServeSQLReplication.addConnection(stringName, provider)
        manageServeSQLReplication.saveConnectionStringSettings()

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        databaseSQLReplicationLink.click()
        waitFor { at DatabaseSQLReplicationPage }
        createAndSaveNewSQLReplication(name, sourceDocumentCollection, tableNameFirst, documentKeyColumnFirst, tableNameSecond, documentKeyColumnSecond, script, provider)

        topNavigation.documentsLink.click()
        waitFor { at DocumentsPage }
        assert getRowsCount() == 2

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }

        databaseSQLReplicationLink.click()
        waitFor { at DatabaseSQLReplicationPage }
        waitFor { replicationContainer.displayed }

        editSqlReplicationLink.click()
        waitFor { at DatabaseSQLReplicationPage }

        delete()
        waitFor { at SettingsPage }
    }

    /**
     * User can add database custom functions.
     * @Step Navigate to resources page.
     * @Step Create new resource.
     * @Step Navigate to settings page.
     * @Step Create custom JavaScript functions
     * @verification Custom functions created.
     */
    @Test(groups="Smoke")
    void canAddDatabaseCustomFunctions() {
        at ResourcesPage

        String lastCreatedDatabaseName = "db" + rand.nextInt()
        createResource(lastCreatedDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        getResourceLink(lastCreatedDatabaseName).click()
        waitFor { at DocumentsPage }
        assert getRowsCount() == 0

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        assert databaseCustomFunctionsLink.displayed

        databaseCustomFunctionsLink.click()
        waitFor { customFunctions.saveButton.displayed }

        customFunctions.typeJavascript("exports.greet = function(name) {return \"Hello\" + name + \"!\"};")
        customFunctions.saveSettingsCustomFunctions()
        waitFor { at SettingsPage }
    }

    /**
     * User can setup database replication.
     * @Step Navigate to resources page.
     * @Step Create new resource and create new resource with Replication bundle.
     * @Step Add simple destination.
     * @verification Replication created.
     */
    @Test(groups="Smoke")
    void canCreateAndSetupDatabaseReplication() {
        at ResourcesPage

        String databaseNameToReplicateTo = "replicate" + rand.nextInt()
        createResource(databaseNameToReplicateTo, ResourcesPage.RESOURCE_TYPE_DATABASE)

        String primaryDatabaseName = "primary" + rand.nextInt()
        createResource(primaryDatabaseName, ResourcesPage.RESOURCE_TYPE_DATABASE, [ResourcesPage.REPLICATION_BUNDLE])

        getResourceLink(primaryDatabaseName).click()
        waitFor { at DocumentsPage }
        assert getRowsCount() == 0

        topNavigation.databaseSettingsLink.click()
        waitFor { at SettingsPage }
        assert databaseReplicationLink.displayed

        databaseReplicationLink.click()

        manageServerReplication.addSimpleDestination(
            "http://localhost:8080",
            databaseNameToReplicateTo
            )

        topNavigation.documentsLink.click()
        waitFor { at DocumentsPage }
        assert getRowsCount() == 2
    }    }
}
