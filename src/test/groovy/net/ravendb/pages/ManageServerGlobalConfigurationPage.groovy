package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerGlobalConfigurationPage extends Page {

    static at = {
        createGlobalConfigurationForPeriodicExportButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        createGlobalConfigurationForPeriodicExportButton { $("button", text:"Create global configuration for Periodic Export") }
    }
}
