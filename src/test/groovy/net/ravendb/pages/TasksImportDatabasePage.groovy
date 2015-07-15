package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TasksMenu
import net.ravendb.modules.TopNavigationBar


class TasksImportDatabasePage extends Page {

    static at = {
        chooseFileButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        menu { module TasksMenu }

        chooseFileButton { $("input#importDatabaseFilePicker") }
    }
}
