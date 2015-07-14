package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class FileSystemPage extends Page {

    static at = {
        newFolderButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        newFolderButton { $("button[title='Create folder']") }
    }
}
