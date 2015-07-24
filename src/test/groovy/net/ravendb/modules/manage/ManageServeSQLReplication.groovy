package net.ravendb.modules.manage

import geb.Module
import net.ravendb.modules.AlertTextModule;
import net.ravendb.modules.YesNoModalDialog;
import net.ravendb.utils.HtmlUtils

import org.openqa.selenium.JavascriptExecutor


class ManageServeSQLReplication extends Module {

    final static String SQL_PROVIDER_SQLCLIENT = "System.Data.SqlClient"
    final static String SQL_PROVIDER_SQLSERVERCE40 = "System.Data.SqlServerCe.4.0"
    final static String SQL_PROVIDER_OLEDB = "System.Data.OleDb"
    final static String SQL_PROVIDER_ORACLECLIENT = "System.Data.OracleClient"
    final static String SQL_PROVIDER_MYSQLCLIENT = "MySql.Data.MySqlClient"
    final static String SQL_PROVIDER_SQLSERVERCE35 = "System.Data.SqlServerCe.3.5"
    final static String SQL_PROVIDER_NPGSQL = "Npgsql"

    final static String SUCCESS_MESSAGE = "Saved Raven/Global/SqlReplication/Connections"

    static content = {
        yesNoDialog { module YesNoModalDialog }
        messagesContainer { module AlertTextModule }

        saveButton { $("button[title='Save changes (Alt+S)']") }
        createGlobalConfigurationForSQLReplicationButton { $("button", text:"Create global configuration for SQL Replication") }
        removeGlobalConfigurationForSQLReplicationButton { $("button", text:"Remove global configuration for SQL Replication") }
        newConnectionStringButton { $("button", text:"New connection string") }

        connectionContainers { $("div.sql-replication-connection-card") }
        connectionNameInputSelector { "input" }
        connectionSqlProviderSelector { "select" }
        connectionStringTextareaSelector { "textarea" }
    }

    def addConnection(String name, String provider, String connectionString = null) {
        HtmlUtils.scrollToTop(browser)
        newConnectionStringButton.click()

        def container = connectionContainers[0]
        def nameInput = container.$(connectionNameInputSelector)
        assert nameInput
        HtmlUtils.setElementsValueAndFireChangeEvent(browser, nameInput, name)

        def providerSelect = container.$(connectionSqlProviderSelector)
        providerSelect.click()
        def options = providerSelect.$("option")
        options.each {
            if(it.text().equals(provider)) {
                it.click()
            }
        }
        nameInput.click()

        if(provider.equals(ManageServeSQLReplication.SQL_PROVIDER_OLEDB)) {
            HtmlUtils.setElementsValueAndFireChangeEvent(browser, container.$(connectionStringTextareaSelector), connectionString)
        }
    }

    def save() {
        HtmlUtils.scrollToTop(browser)
        saveButton.click()
        waitFor(10, 0.1) {
            messagesContainer.containsMessage(ManageServeSQLReplication.SUCCESS_MESSAGE)
            saveButton.@disabled == "true"
            removeGlobalConfigurationForSQLReplicationButton.displayed
            }
    }

    def remove() {
        HtmlUtils.scrollToTop(browser)
        removeGlobalConfigurationForSQLReplicationButton.click()
        waitFor { yesNoDialog.yesButton.displayed }

        yesNoDialog.yesButton.click()
        waitFor(10, 0.1) {
            createGlobalConfigurationForSQLReplicationButton.displayed
        }
    }
}
