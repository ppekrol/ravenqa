package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.manage.ManageServerMenu


class ManageServerBackupPage extends Page {

    private final static String DB_BACKUP_SUCCESS = "Database backup was successfully created!"

    static at = {
        startDatabaseBackupButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }
        messagesContainer { module AlertTextModule }

        databaseTab { $("a[href='#backupDb']") }
        fileSystemTab { $("a[href='#backupFs']") }
        counterStorageTab { $("a[href='#backupCs']") }

        startDatabaseBackupButton { $("button", text:"Start Database Backup") }
        startFileSystemBackupButton { $("button", text:"Start File System Backup") }
        counterStorageBackupButton { $("button", text:"Start File System Backup") }

        databaseNameInput { $("input[name='databaseName']") }
        fileSystemNameInput { $("input[name='filesystemName']") }
        couterStorageNameInput { $("input[name='countersstoragename']") }
        locationInput { $("input#location") }
    }

    String backupBatabase(String name) {
        String tmpPath = File.createTempDir().getAbsolutePath()
        return backupDatabase(name, tmpPath)
    }

    String backupDatabase(String name, String location) {
        databaseNameInput = name
        locationInput = location
        startDatabaseBackupButton.click()

        messagesContainer.waitForMessage(DB_BACKUP_SUCCESS)

        return location
    }
}