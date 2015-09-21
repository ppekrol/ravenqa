package net.ravendb.pages

import geb.Page
import net.ravendb.modules.ChooseColumnsModalDialog
import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.modules.TopNavigationBar


class DetailsIndexPage extends Page {

	final static String INDEX_QUERY_RESULTS_COLUMN_COMPANY = "Company"
    final static String INDEX_QUERY_RESULTS_COLLECTION_COMPANIES_DOCUMENT = "companies/43"

    static at = {
        runQueryButton.displayed
    }

    static content = {
		// modules
		topNavigation { module TopNavigationBar }
		queryStatsModalDialog { module QueryStatsModalDialog }
		chooseColumnsModalDialog { module ChooseColumnsModalDialog }

        // breadcrumbs
        indexesBreadcrumbs { $("ul.breadcrumb a[data-bind='attr: { href: appUrls.indexes }']") }

		// tool bar
		runQueryButton { $("button[title='Run the query (Alt+R)']") }
		chooseColumnsButton { $("button[title='Choose columns...']") }
		termsButton { $("a[title='Navigate to index terms']") }
		queryStatsButton { $("a[title='Show Query Stats In Dialog']") }

		// results list
		queryResultsList { $("div#queryResultsGrid div.ko-grid-row") }
        documentsListLinksSelector { "a[href^='#databases/edit']" }
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

    def clickDocument(CharSequence name) {
        def docToClick
        queryResultsList.each {
            if(it.$(documentsListLinksSelector).$("span").text().equals(name)) {
                docToClick = it.$(documentsListLinksSelector)
            }
        }

        if(docToClick) {
            docToClick.click()
        }
    }

}
