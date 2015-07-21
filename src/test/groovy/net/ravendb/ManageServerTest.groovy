package net.ravendb

import java.awt.Robot
import java.awt.event.KeyEvent

import net.ravendb.modules.ManageServerPeriodicExport
import net.ravendb.modules.ManageServerReplication
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.ManageServerAdminLogsPage
import net.ravendb.pages.ManageServerAdministratorJsConsolePage
import net.ravendb.pages.ManageServerApiKeysPage
import net.ravendb.pages.ManageServerBackupPage
import net.ravendb.pages.ManageServerCompactPage
import net.ravendb.pages.ManageServerGatherDebugInfoPage
import net.ravendb.pages.ManageServerGlobalConfigurationPage
import net.ravendb.pages.ManageServerIoTestPage
import net.ravendb.pages.ManageServerLicenseInformationPage
import net.ravendb.pages.ManageServerPage
import net.ravendb.pages.ManageServerRestorePage
import net.ravendb.pages.ManageServerServerSmugglingPage
import net.ravendb.pages.ManageServerTrafficWatchPage
import net.ravendb.pages.ManageServerWindowsAuthenticationPage
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test


class ManageServerTest extends TestBase {

    /**
     * User can click all the links available on Manage Server page.
     * @Step Click Manage Server button on Resources page.
     * @Step Click all the links available on left panel.
     * @verification All pages displayed with no errors.
     */
    @Test(groups="Smoke")
    void canClickAllManageServerLinks() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.apiKeysLink.click()
        waitFor { at ManageServerApiKeysPage }

        menu.windowsAuthenticationLink.click()
        waitFor {at ManageServerWindowsAuthenticationPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        menu.serverSmugglingLink.click()
        waitFor { at ManageServerServerSmugglingPage }

        menu.backupLink.click()
        waitFor { at ManageServerBackupPage }

        menu.compactLink.click()
        waitFor { at ManageServerCompactPage }

        menu.restoreLink.click()
        waitFor { at ManageServerRestorePage }

        Robot robot = new Robot()
        robot.keyPress(KeyEvent.VK_PAGE_DOWN)
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN)

        menu.adminLogsLink.click()
        waitFor { at ManageServerAdminLogsPage }

        menu.trafficWatchLink.click()
        waitFor { at ManageServerTrafficWatchPage }

        menu.licenseInformationLink.click()
        waitFor { at ManageServerLicenseInformationPage }

        menu.gatherDebugInfoLink.click()
        waitFor { at ManageServerGatherDebugInfoPage }

        menu.ioTestLink.click()
        waitFor { at ManageServerIoTestPage }

        menu.administratorJsConsole.click()
        waitFor { at ManageServerAdministratorJsConsolePage }

        //TODO not sure what is happening here
        //menu.studioConfigLink.click()
        //waitFor { at ManageServerStudioConfigPage }

        menu.toSystemDatabaseLink.click()
        waitFor { menu.areYouSureModalDialog.okButton.displayed }
        menu.areYouSureModalDialog.okButton.click()
        waitFor { at DocumentsPage }
    }

    /**
     * User can create and delete Global Configuration for Periodic Export.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToFilesystem() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToFilesystemConfiguration(
            true,
            "test",
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Periodic Export to Glacier.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToGlacier() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToRemoteServerConfiguration(
            true,
            ManageServerPeriodicExport.REMOTE_SERVER_GLACIER,
            "test-glacier",
            "AWSAccount",
            "AWSKey",
            ManageServerPeriodicExport.AWS_REGION_US_EAST_1,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Periodic Export to S3.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToS3() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToRemoteServerConfiguration(
            true,
            ManageServerPeriodicExport.REMOTE_SERVER_S3,
            "test-s3",
            "AWSAccount",
            "AWSKey",
            ManageServerPeriodicExport.AWS_REGION_US_EAST_1,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Periodic Export to Azure.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToAzure() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToRemoteServerConfiguration(
            true,
            ManageServerPeriodicExport.REMOTE_SERVER_AZURE,
            "test-azure",
            "AWSAccount",
            "AWSKey",
            ManageServerPeriodicExport.AWS_REGION_US_EAST_1,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Replication with single destination.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Replication configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteReplicationWithOneDestination() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        replicationTab.click()
        waitFor { replication.createGlobalConfigurationForReplication.displayed }
        replication.createGlobalConfigurationForReplication.click()

        replication.fillBaseForm(
            ManageServerReplication.CLIENT_FAILOVER_ALLOW_READS_FROM_SECONDARIES,
            ManageServerReplication.CONFLICT_RESOLUTION_LOCAL
            )

        replication.addDestination(
            true,
            "http://localhost:8080",
            ManageServerReplication.CREDENTIALS_NONE
            )

        replication.save()

        replication.remove()
    }

    /**
     * User can create and delete Global Configuration for Replication with multiple destinations.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Replication configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke",dependsOnMethods="canCreateAndDeleteReplicationWithOneDestination")
    void canCreateAndDeleteReplicationWithMultipleDestinations() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        replicationTab.click()
        waitFor { replication.createGlobalConfigurationForReplication.displayed }
        replication.createGlobalConfigurationForReplication.click()

        replication.fillBaseForm(
            ManageServerReplication.CLIENT_FAILOVER_ALLOW_READS_FROM_SECONDARIES_AND_WRITES_TO_SECONDARIES,
            ManageServerReplication.CONFLICT_RESOLUTION_LATEST
            )

        replication.addDestination(
            true,
            "http://localhost:8080",
            ManageServerReplication.CREDENTIALS_NONE
            )

        replication.addDestination(
            true,
            "http://localhost:8081",
            ManageServerReplication.CREDENTIALS_NONE
            )

        replication.save()

        replication.remove()
    }


    /**
     * User can create and delete Global Configuration for Replication with credentials.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Replication configuration.
     * @verification Configuration created and deleted.
     */
    @Test(
        groups="Smoke",
        dependsOnMethods=[
            "canCreateAndDeleteReplicationWithOneDestination",
            "canCreateAndDeleteReplicationWithMultipleDestinations"
            ]
        )
    void canCreateAndDeleteReplicationWithCredentials() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        replicationTab.click()
        waitFor { replication.createGlobalConfigurationForReplication.displayed }
        replication.createGlobalConfigurationForReplication.click()

        replication.fillBaseForm(
            ManageServerReplication.CLIENT_FAILOVER_FAILS_IMMEDIATELY,
            ManageServerReplication.CONFLICT_RESOLUTION_REMOTE
            )

        replication.addDestination(
            true,
            "http://localhost:8080",
            ManageServerReplication.CREDENTIALS_USER,
            "username",
            "password",
            "domain"
            )

        replication.addDestination(
            true,
            "http://localhost:8081",
            ManageServerReplication.CREDENTIALS_API,
            null,
            null,
            null,
            "APIKey"
            )

        replication.save()

        replication.remove()
    }

    /**
     * User can create and delete Global Configuration for Replication with advanced options.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Replication configuration.
     * @verification Configuration created and deleted.
     */
    @Test(
        groups="Smoke",
        dependsOnMethods="canCreateAndDeleteReplicationWithOneDestination"
        )
    void canCreateAndDeleteReplicationWithAdvancedOptions() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        replicationTab.click()
        waitFor { replication.createGlobalConfigurationForReplication.displayed }
        replication.createGlobalConfigurationForReplication.click()

        replication.fillBaseForm(
            ManageServerReplication.CLIENT_FAILOVER_READ_FROM_ALL_SERVERS,
            ManageServerReplication.CONFLICT_RESOLUTION_LOCAL
            )

        replication.addDestination(
            true,
            "http://localhost:8080",
            ManageServerReplication.CREDENTIALS_NONE,
            null,
            null,
            null,
            null,
            true,
            "http://localhost:8081",
            true,
            ManageServerReplication.FAILOVER_SKIP,
            ManageServerReplication.TRANSITIVE_REPLICATION_CHANGED_AND_REPLICATED
            )

        replication.save()

        replication.remove()
    }
}
