package net.ravendb.test.manage

import net.ravendb.modules.manage.ManageServeSQLReplication;
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerGlobalConfigurationPage;
import net.ravendb.pages.manage.ManageServerPage;
import net.ravendb.test.TestBase;

import org.testng.annotations.Test


class ManageServerSqlReplicationTest extends TestBase {

    /**
     * User can create and delete Global Configuration for SQL Replication with all available options.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create SQL Replication configuration.
     * @verification Configuration created and deleted.
     */
    //TODO enable when able to handle leave page dialog
    @Test(groups="Smoke",enabled=false)
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
