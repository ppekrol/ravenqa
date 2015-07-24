package net.ravendb.test.manage

import net.ravendb.modules.manage.ManageServerReplication;
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerGlobalConfigurationPage;
import net.ravendb.pages.manage.ManageServerPage;
import net.ravendb.test.TestBase;

import org.testng.annotations.Test


class ManageServerReplicationTest extends TestBase{

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
