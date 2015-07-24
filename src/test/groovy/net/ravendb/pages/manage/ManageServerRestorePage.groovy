package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar
import net.ravendb.modules.manage.ManageServerMenu


class ManageServerRestorePage extends Page {

    private final static String DB_RESTORE_SUCCESS = "Database was successfully restored!"

    static at = {
        restoreDatabaseButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }
        topNavigation { module TopNavigationBar }
        messagesContainer { module AlertTextModule }

        databaseTab { $("a[href='#restoreDb']") }
        fileSystemTab { $("a[href='#restoreFs']") }
        counterStorageTab { $("a[href='#restoreCs']") }

        restoreDatabaseButton { $("button", text:"Restore Database") }
        restoreFileSystemButton { $("button", text:"Restore Filesystem") }
        restoreCounterStorageButton { $("button", text:"Restore Filesystem") }

        databaseBackupLocationInput { $("input#backupLocation") }
        databaseNameInput { $("input#databaseName") }
    }

    def restoreDatabase(String location, String name) {
        databaseBackupLocationInput = location
        databaseNameInput = name
        restoreDatabaseButton.click()

        messagesContainer.waitForMessage(DB_RESTORE_SUCCESS)
    }
}
