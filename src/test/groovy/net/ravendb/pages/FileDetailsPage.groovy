package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar


class FileDetailsPage extends Page {

    public final static String SAVED_MESSAGE = "Saved "

    static at = {
        filenameInput.displayed
    }

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        alert { module AlertTextModule }

        // toolbar
        saveButton { $("button[title='Save (Alt+S)']") }

        // content
        filenameInput { $("input[title='File name']") }
        fileMetadataContainer { $("pre#fileMetadataEditor textarea") }
    }

}
