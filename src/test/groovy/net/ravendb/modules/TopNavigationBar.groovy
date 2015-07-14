package net.ravendb.modules

import geb.Module


class TopNavigationBar extends Module {

    static content = {
        filesLink { $("a[href^='#filesystems/files']") }
        countersLink { $("a[href^='#counterstorages/counters']") }
        resourcesLink { $("a[href='#resources']") }

        goToDocInput { $("input#goToDocInput") }
    }
}
