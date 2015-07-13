package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerBackupPage extends Page {

    static at = {
        startDatabaseBackupButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        startDatabaseBackupButton { $("button", text:"Start Database Backup") }
    }
}
