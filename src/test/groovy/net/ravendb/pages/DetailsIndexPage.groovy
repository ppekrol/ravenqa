package net.ravendb.pages

import geb.Page
import net.ravendb.modules.QueryStatsModalDialog


class DetailsIndexPage extends Page {

    static at = {
        queryStatsButton
    }

    static content = {
		queryStatsModalDialog { module QueryStatsModalDialog }

        queryStatsButton { $("a[title='Show Query Stats In Dialog']") }
    }

}
