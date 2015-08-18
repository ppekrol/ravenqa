package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TasksMenu
import net.ravendb.modules.TopNavigationBar


class TasksImportDatabasePage extends Page {

    static at = {
        header.displayed
    }

    static content = {
        topNavigation { module TopNavigationBar }

        menu { module TasksMenu }

        header { $("h3", text:"Import Database") }
        chooseFileButton { $("input#importDatabaseFilePicker") }
    }
}
