package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class SettingsPage extends Page {

    static at = {
        settingsBreadcrumb.displayed
    }

    static content = {
        // modules
        topNavigation(required:false) { module TopNavigationBar }

        // content
        settingsBreadcrumb { $("ul.breadcrumb span", text:"Settings") }

        databaseVersioningLink(required:false) { $("a[href^='#databases/settings']") }
        filesystemVersioningLink(required:false) { $("a[href^='#filesystems/settings']") }
    }
}
