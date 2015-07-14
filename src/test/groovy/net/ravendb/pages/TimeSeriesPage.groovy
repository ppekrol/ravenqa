package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class TimeSeriesPage extends Page {

    static at = {
        topNavigation
    }

    static content = {
        topNavigation { module TopNavigationBar }
    }
}
