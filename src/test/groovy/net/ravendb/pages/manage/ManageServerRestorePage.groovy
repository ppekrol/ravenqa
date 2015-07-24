package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar
import net.ravendb.modules.manage.ManageServerMenu


class ManageServerRestorePage extends Page {

    private final static String DB_RESTORE_SUCCESS = "Database was successfully restored!"
    private final static String FS_RESTORE_SUCCESS = "File system was successfully restored!"

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
        fileSystemBackupLocationInput { $("input#Text1") }
        fileSystemNameInput { $("input#filesystemName") }
    }

    def restoreDatabase(String location, String name) {
        databaseBackupLocationInput = location
        databaseNameInput = name
        restoreDatabaseButton.click()

        messagesContainer.waitForMessage(DB_RESTORE_SUCCESS)
    }

    def restoreFileSystem(String location, String name) {
        fileSystemTab.click()
        waitFor { fileSystemBackupLocationInput.displayed }

        fileSystemBackupLocationInput = location
        fileSystemNameInput = name
        restoreFileSystemButton.click()

        messagesContainer.waitForMessage(FS_RESTORE_SUCCESS)
    }
}
