package net.ravendb.pages

import net.ravendb.modules.TasksMenu;
import net.ravendb.modules.TopNavigationBar;
import geb.Page


class TasksCreateSampleDataPage extends Page {

    static at = {
        createSampleDataButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        menu { module TasksMenu }

        createSampleDataButton { $("button[data-bind='click: generateSampleData']").parent() }
        progressBar { $("div.progress") }
    }

    def createSampleData() {
        createSampleDataButton.click()
        waitFor(10, 0.1) { progressBar.displayed }
        waitFor(10, 0.1) { !progressBar.displayed }
    }
}
