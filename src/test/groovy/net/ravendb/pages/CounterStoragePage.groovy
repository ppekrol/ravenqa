package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class CounterStoragePage extends Page {

    static at = {
        createNewCounterButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        createNewCounterButton { $("button[title='Create a new counter (Alt+N)']") }
    }
}
