package net.ravendb.pages

import geb.Page


class DatabasePage extends Page {

    static at = {
        newDocumentButton
    }

    static content = {
        newDocumentButton { $("button[title='Create new document (Alt+N)']") }
    }
}
