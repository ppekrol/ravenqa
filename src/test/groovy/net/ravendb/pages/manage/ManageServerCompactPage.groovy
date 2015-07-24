package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.manage.ManageServerMenu;


class ManageServerCompactPage extends Page {

    final static DB_SUCCESS = "Database was successfully compacted!"
    final static FS_SUCCESS = "File system was successfully compacted!"

    static at = {
        startCompactionButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }
        messagesContainer { module AlertTextModule }

        databseTab { $("a[href='#compactDb']") }
        fileSystemTab { $("a[href='#compactFs']") }

        startCompactionButton { $("button", text:"Start Compaction") }

        databaseNameInput { $("input[name='databaseName']") }
        fileSystemNameInput { $("input[name='filesystemName']") }
        startCompactionButton { $("button", text:"Start Compaction") }
    }

    def compactDatabase(String name) {
        databaseNameInput = name
        databseTab.click()
        startCompactionButton.click()

        messagesContainer.waitForMessage(DB_SUCCESS)
    }

    def compactFileSystem(String name) {
        fileSystemNameInput = name
        fileSystemTab.click()
        startCompactionButton.click()

        messagesContainer.waitForMessage(FS_SUCCESS)
    }
}
