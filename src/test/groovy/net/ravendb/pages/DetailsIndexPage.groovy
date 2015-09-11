package net.ravendb.pages

import geb.Page
import net.ravendb.modules.ChooseColumnsModalDialog
import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.modules.TopNavigationBar


class DetailsIndexPage extends Page {

	final static String INDEX_QUERY_RESULTS_COLUMN_COMPANY = "Company"

    static at = {
        queryStatsButton
    }

    static content = {
		topNavigation { module TopNavigationBar }
		queryStatsModalDialog { module QueryStatsModalDialog }
		chooseColumnsModalDialog { module ChooseColumnsModalDialog }

		runQueryButton { $("button[title='Run the query (Alt+R)']") }
		chooseColumnsButton { $("button[title='Choose columns...']") }
		queryStatsButton { $("a[title='Show Query Stats In Dialog']") }

		queryResultsList { $("div#queryResultsGrid div.ko-grid-row") }
		queryResultsListHeaders(required:false) { $("div.ko-grid-column-header span[data-bind='text: header']") }
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

	boolean isHeaderPresent(String title) {
		boolean present = false
		queryResultsListHeaders.each {
			if(it.text().equals(title)) {
				present = true
			}
		}

		return present
	}

}
