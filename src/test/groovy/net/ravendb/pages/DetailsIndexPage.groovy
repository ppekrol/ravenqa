package net.ravendb.pages

import geb.Page
import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.modules.TopNavigationBar


class DetailsIndexPage extends Page {

    static at = {
        queryStatsButton
    }

    static content = {
		topNavigation { module TopNavigationBar }
		queryStatsModalDialog { module QueryStatsModalDialog }

        queryStatsButton { $("a[title='Show Query Stats In Dialog']") }
    }

}
