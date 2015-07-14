package net.ravendb.pages

import geb.Page


class FileSystemPage extends Page {

    static at = {
        newFolderButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        newFolderButton { $("button[title='Create folder']") }
    }
}
