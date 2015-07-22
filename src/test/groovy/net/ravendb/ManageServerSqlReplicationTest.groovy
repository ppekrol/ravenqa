package net.ravendb

import net.ravendb.modules.ManageServeSQLReplication
import net.ravendb.pages.ManageServerGlobalConfigurationPage
import net.ravendb.pages.ManageServerPage
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test


class ManageServerSqlReplicationTest extends TestBase {

    /**
     * User can create and delete Global Configuration for SQL Replication with all available options.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create SQL Replication configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteSqlReplicationWithAllAvailableOptions() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        sqlReplicationTab.click()
        waitFor { sqlReplication.createGlobalConfigurationForSQLReplicationButton.displayed }
        sqlReplication.createGlobalConfigurationForSQLReplicationButton.click()

        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_SQLCLIENT,
            ManageServeSQLReplication.SQL_PROVIDER_SQLCLIENT
            )
        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_SQLSERVERCE40,
            ManageServeSQLReplication.SQL_PROVIDER_SQLSERVERCE40
            )
        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_OLEDB,
            ManageServeSQLReplication.SQL_PROVIDER_OLEDB,
            "test"
            )
        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_ORACLECLIENT,
            ManageServeSQLReplication.SQL_PROVIDER_ORACLECLIENT
            )
        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_MYSQLCLIENT,
            ManageServeSQLReplication.SQL_PROVIDER_MYSQLCLIENT
            )
        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_SQLSERVERCE35,
            ManageServeSQLReplication.SQL_PROVIDER_SQLSERVERCE35
            )
        sqlReplication.addConnection(
            ManageServeSQLReplication.SQL_PROVIDER_NPGSQL,
            ManageServeSQLReplication.SQL_PROVIDER_NPGSQL
            )

        sqlReplication.save()

        sqlReplication.remove()
    }
}
