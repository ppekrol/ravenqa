package net.ravendb.modules

import geb.Module
import net.ravendb.pages.ResourcesPage


class TopNavigationBar extends Module {

    static content = {
        filesLink { $("a[href^='#filesystems/files']") }
        countersLink { $("a[href^='#counterstorages/counters']") }
        documentsLink { $("a[href^='#databases/documents']") }
        indexesLink { $("a[href^='#databases/indexes']") }
        queryLink { $("a[href^='#databases/query']") }
        tasksLink { $("a[href^='#databases/tasks']") }
        databaseSettingsLink { $("a[href^='#databases/settings']") }
        filesystemSettingsLink { $("a[href^='#filesystems/settings']") }
        configurationsLink { $("a[href^='#filesystems/configuration']") }

        goToDocInput { $("input#goToDocInput") }
        goToDocList(required:false) { goToDocInput.parent().$("ul li") }

        menuCaret { $("li.vertical-navbar-menu-item i.fa-caret-down") }
        resourcesLink { $("a[href='#resources']") }

        activeDropdown { $("li.navbar-splitbutton.active") }
        transformersLink { $("a[href^='#databases/transformers']") }
        dataExplorationLink { $("a[href^='#databases/query/exploration']") }
        reportingLink { $("a[href^='#databases/query/reporting']") }
    }

    def switchToResources() {
        menuCaret.click()
        resourcesLink.click()
    }

    def switchToTransformers() {
        activeDropdown.next().click()
        transformersLink.click()
    }

    def switchToDataExplorationTool() {
        activeDropdown.next().click()
        dataExplorationLink.click()
    }

    def switchToReportingTool() {
        activeDropdown.next().click()
        reportingLink.click()
    }
}
