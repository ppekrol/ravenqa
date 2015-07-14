package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class DatabasePage extends Page {

    static at = {
        newDocumentButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        newDocumentButton { $("button[title='Create new document (Alt+N)']") }
    }
}
