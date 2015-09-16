package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class FileDetailsPage extends Page {

    public final static String SAVED_MESSAGE = "Saved "
    public final static String RENAME_SUCCESS = "Successfully renamed file"
    public final static String DELETE_SUCCESS = "Deleted "

    static at = {
        filenameInput.displayed
    }

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        deleteFileModalDialog { module DeleteResourceModalDialog }
        alert { module AlertTextModule }

        // toolbar
        saveButton { $("button[title='Save (Alt+S)']") }
        renameFileButton { $("button[title='Rename file (Alt+E)']") }
        deleteFileButton { $("button[title='Delete (Alt+shift+Del)']") }

        // content
        filenameInput { $("input[title='File name']") }
        fileMetadataContainer { $("pre#fileMetadataEditor textarea") }

        // rename file modal dialog
        renameFileInput(required:false) { $("input[data-bind='value: newName']") }
        renameFileOkButton(required:false) { $("button[data-bind='click: rename']") }
    }

}
