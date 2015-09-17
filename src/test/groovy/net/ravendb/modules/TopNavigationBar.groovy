package net.ravendb.modules

import geb.Module
import net.ravendb.pages.ResourcesPage


class TopNavigationBar extends Module {

    static content = {
        filesLink { $("a[href^='#filesystems/files']") }
        countersLink { $("a[href^='#counterstorages/counters']") }
        documentsLink { $("a[href^='#databases/documents']") }
        indexesLink { $("a[href^='#databases/indexes']") }
        tasksLink { $("a[href^='#databases/tasks']") }
        databaseSettingsLink { $("a[href^='#databases/settings']") }
        filesystemSettingsLink { $("a[href^='#filesystems/settings']") }

        goToDocInput { $("input#goToDocInput") }
        goToDocList(required:false) { goToDocInput.parent().$("ul li") }

        menuCaret { $("li.vertical-navbar-menu-item i.fa-caret-down") }
        resourcesLink { $("a[href='#resources']") }
    }

    def switchToResources() {
        menuCaret.click()
        resourcesLink.click()
    }
}
