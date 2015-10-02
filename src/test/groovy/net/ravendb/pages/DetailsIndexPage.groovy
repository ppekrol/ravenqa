package net.ravendb.pages

import geb.Page
import net.ravendb.modules.ChooseColumnsModalDialog
import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.modules.TopNavigationBar


class DetailsIndexPage extends Page {

	final static String INDEX_QUERY_RESULTS_COLUMN_COMPANY = "Company"
    final static String INDEX_QUERY_RESULTS_COLUMN_COUNT = "Count"
    final static String INDEX_QUERY_RESULTS_COLUMN_TOTAL = "Total"

    static at = {
        queryStatsButton
    }

    static content = {
		// modules
		topNavigation { module TopNavigationBar }
		queryStatsModalDialog { module QueryStatsModalDialog }
		chooseColumnsModalDialog { module ChooseColumnsModalDialog }

		// tool bar
		runQueryButton { $("button[title='Run the query (Alt+R)']") }
		chooseColumnsButton { $("button[title='Choose columns...']") }
		termsButton { $("a[title='Navigate to index terms']") }
		queryStatsButton { $("a[title='Show Query Stats In Dialog']") }

        // query filters
        selectField { $("span[data-bind='text:searchField()']") }
        selectFieldDropdown { $("a[href='#']") }
        stringFilterButton { $("button#fieldStartsWith") }
        rangeFilterButton { $("button#inRange") }
        inFilterButton { $("button#inCollection") }

		// results list
		queryResultsList { $("div#queryResultsGrid div.ko-grid-row") }
		queryResultsListHeaders(required:false) { $("div.ko-grid-column-header span[data-bind='text: header']") }

        // string field filter modal dialog
        stringFieldFilterModalHeader(required:false) { $("h4", text:"Add string field filter") }
        fieldSubTextInput { $("input#firstInput") }
        applyButton { $("button[type='submit']") }

        // range filter modal dialog
        rangeFilterModalHeader { $("h4", text:"Add range filter") }
        fromInput { $("div.modal-body[data-bind='visible: !isDateTime()'] div#fromDate input") }
        toInput { $("div.modal-body[data-bind='visible: !isDateTime()'] div#toDate input") }

        // in filter modal dialog
        addInFilterModalHeader { $("h4", text:"Add in method filter") }
        searchValueInput { $("div.modal-body input.form-control") }
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

    def selectQueryFilter(CharSequence name) {
        def container
        selectFieldDropdown.each {
            if(it.getAttribute("innerHTML").contains(name)) {
                container = it
            }
        }
        assert container
        container.click()
    }

    def selectColumnToFilter(String columnName) {
        waitFor { selectField.displayed }
        selectField.click()
        selectQueryFilter(columnName)
    }
}
