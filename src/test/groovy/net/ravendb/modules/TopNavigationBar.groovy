package net.ravendb.modules

import geb.Module


class TopNavigationBar extends Module {

    static content = {
        filesLink { $("a[href^='#filesystems/files']") }
        countersLink { $("a[href^='#counterstorages/counters']") }
        documentsLink { $("a[href^='#databases/documents']") }
        tasksLink { $("a[href^='#databases/tasks']") }

        goToDocInput { $("input#goToDocInput") }

        resourcesLink { $("a[href='#resources']") }
    }
}
