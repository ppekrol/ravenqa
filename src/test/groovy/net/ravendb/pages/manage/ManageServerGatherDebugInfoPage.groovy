package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerGatherDebugInfoPage extends Page {

    static at = {
        createInfoPackageButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        menu { module ManageServerMenu }

        createInfoPackageButton { $("button[title='Creates package with stacktrace (available ONLY for system database)']") }
        parallelStacksContainer { $("svg#parallelStacks") }
    }
}
