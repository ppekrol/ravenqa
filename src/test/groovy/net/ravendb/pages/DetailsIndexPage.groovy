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

		runQueryButton { $("button[title='Run the query (Alt+R)']") }
		queryStatsButton { $("a[title='Show Query Stats In Dialog']") }

		queryResultsList { $("div#queryResultsGrid div.ko-grid-row") }
    }

	int getRowsCount() {
		int rowsCount = 0
		queryResultsList.each {
			if(!it.@style.contains("display: none")) {
				rowsCount += 1
			}
		}

		return rowsCount
	}

}
