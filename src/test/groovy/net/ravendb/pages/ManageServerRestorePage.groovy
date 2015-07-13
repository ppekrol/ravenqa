package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerRestorePage extends Page {

    static at = {
        restoreDatabaseButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        restoreDatabaseButton { $("button", text:"Restore Database") }
    }
}
