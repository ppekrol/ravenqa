package net.ravendb.pages

import java.util.List;

import geb.Page
import net.ravendb.modules.AreYouSureModalDialog
import net.ravendb.modules.QuotasModalDialog
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
        areYouSure { module AreYouSureModalDialog }
        quotasModalDialog { module QuotasModalDialog }
        customFunctions { module ManageServerCustomFunctions }
        manageServerReplication { module ManageServerReplication }

        // content
        settingsBreadcrumb { $("ul.breadcrumb span", text:"Settings") }

        quotasLink(required:false) { $("a[href^='#databases/settings/quotas?']") }
        databaseCustomFunctionsLink(required:false) { $("a[href^='#databases/settings/customFunctionsEditor']") }
        databaseSQLReplicationLink(required:false) { $("a[href^='#databases/settings/sqlReplication?']") }
        databaseReplicationLink(required:false) { $("a[href^='#databases/settings/replication']") }
        databaseVersioningLink(required:false) { $("a[href^='#databases/settings']") }
        filesystemVersioningLink(required:false) { $("a[href^='#filesystems/settings']") }

        editDatabaseSettingsButton { $("button[title='Edit Database Settings']") }
        saveButton { $("button[title='Save Changes (Alt+S)']") }
        databaseSettingsDocContainer(required:false) { $("pre#dbDocEditor textarea") }
        databaseSettingsBundleContainer { $("pre#dbDocEditor span.ace_string") }
    }

    boolean isBundlePresent(String title) {
        boolean present = false
        databaseSettingsBundleContainer.each {
            if(it.text().contains(title)) {
                present = true
}
        }

        return present
    }
}
