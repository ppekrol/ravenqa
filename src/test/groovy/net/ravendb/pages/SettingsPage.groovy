package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar
import net.ravendb.modules.manage.ManageServerCustomFunctions
import net.ravendb.modules.manage.ManageServerReplication


class SettingsPage extends Page {

    static at = {
        settingsBreadcrumb.displayed
    }

    static content = {
        // modules
        topNavigation(required:false) { module TopNavigationBar }
        customFunctions { module ManageServerCustomFunctions }
        manageServerReplication { module ManageServerReplication }

        // content
        settingsBreadcrumb { $("ul.breadcrumb span", text:"Settings") }

        databaseCustomFunctionsLink(required:false) { $("a[href^='#databases/settings/customFunctionsEditor']") }
        databaseSQLReplicationLink(required:false) { $("a[href^='#databases/settings/sqlReplication?']") }
        databaseReplicationLink(required:false) { $("a[href^='#databases/settings/replication']") }
        databaseVersioningLink(required:false) { $("a[href^='#databases/settings']") }
        filesystemVersioningLink(required:false) { $("a[href^='#filesystems/settings']") }

        databaseSettingsDocContainer(required:false) { $("pre#dbDocEditor textarea") }
    }
}
