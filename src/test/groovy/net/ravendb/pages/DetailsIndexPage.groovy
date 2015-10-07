package net.ravendb.pages

import geb.Page
import net.ravendb.modules.ChooseColumnsModalDialog
import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.modules.TopNavigationBar


class DetailsIndexPage extends Page {

	final static String INDEX_QUERY_RESULTS_COLUMN_COMPANY = "Company"
    final static String INDEX_QUERY_RESULTS_COLLECTION_COMPANIES_DOCUMENT = "companies/43"
    final static String INDEX_QUERY_RESULTS_COLUMN_COUNT = "Count"
    final static String INDEX_QUERY_RESULTS_COLUMN_TOTAL = "Total"
    final static String INDEX_QUERY_RESULTS_COLUMN_VALUE = "Value"
    final static String INDEX_QUERY_RESULTS_TRANSORMER_ORDERS_COMPANY = "Orders/Company"
    final static String INDEX_QUERY_RESULTS_SORT_BY_SELECT_A_FIELD = "Select a field"
    final static String INDEX_QUERY_RESULTS_SORT_BY_ASCENDING = "Ascending"
    final static String INDEX_QUERY_RESULTS_SORT_BY_DESCENDING = "Descending"
    final static String INDEX_QUERY_RESULTS_SORT_BY_RANGE_ASCENDING = "Range Ascending"

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
        addButton { $("button[title='Add a transformer or sorting option to the query (Alt+A)']") }
        sortByOption { $("a[title='Add a field to sort by (Alt+O)']") }
        transformerOption { $("a[title='Add a transformer to transform the results of the query (Alt+T)']") }
		chooseColumnsButton { $("button[title='Choose columns...']") }
		termsButton { $("a[title='Navigate to index terms']") }
		queryStatsButton { $("a[title='Show Query Stats In Dialog']") }

        // query filters
        selectField { $("span[data-bind='text:searchField()']") }
        selectFieldDropdown { $("a[href='#']") }
        stringFilterButton { $("button#fieldStartsWith") }
        rangeFilterButton { $("button#inRange") }
        inFilterButton { $("button#inCollection") }

        // sort by
        sortByDropdown { $("div[data-bind='visible: sortBys().length > 0'] div.col-md-11 button") }
        columnToSort { $("div[data-bind='visible: sortBys().length > 0'] div.col-md-11 ul li") }

        // transformer
        transformerContainer { $("div.panel-heading") }
        dropdownButtonSelector { "button.dropdown-toggle" }
        transformerOptionSelector { "a[href='#']" }

		// results list
		queryResultsList { $("div#queryResultsGrid div.ko-grid-row") }
        documentsListLinksSelector { "a[href^='#databases/edit']" }
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

    def selectTransformer(CharSequence optionName) {
        def container = transformerContainer
        container.find(dropdownButtonSelector).click()
        def option
        container.find(transformerOptionSelector).each {
            if(it.getAttribute("innerHTML").contains(optionName)) {
                option = it
            }
        }
        assert option
        option.click()
    }

    def selectDropdownAndOptionToSort(CharSequence dropdownName, CharSequence optionName) {
        def dropdownToggle
        sortByDropdown.$("span").each {
            if(it.text().equals(dropdownName)) {
                dropdownToggle = it
            }
        }

        if(dropdownToggle) {
            dropdownToggle.click()

            def dropdownContainer = columnToSort
            def dropdownOption
            dropdownContainer.each {
                if(it.text().equals(optionName)) {
                    dropdownOption = it
                }
            }
            assert dropdownOption
            dropdownOption.click()
        }
    }
}
