package net.ravendb.pages

import geb.Page


class QueryReportingPage extends Page {

    final static String QUERY_AVAILABLE_FIELDS_OPTION_TAG = "Tag"
    final static String QUERY_AVAILABLE_FIELDS_OPTION_LAST_MODIFIED_TICKS = "LastModifiedTicks"
    final static String QUERY_RESULTS_COLUMN_KEY = "Key"
    final static String QUERY_RESULTS_COLUMN_COUNT_OF_LAST_MODIFIED_TICKS = "Count of LastModifiedTicks"

    static at = {
        runReportButton.displayed
    }

    static content = {
        // tool bar
        runReportButton { $("button[title='Run the report (Alt+R)']") }

        // form
        groupByDropdownButton { $("button[title='Group By (Alt+G)']") }
        availableFieldsDropdown { $("div.btn-group.open ul.dropdown-menu[data-bind='foreach: availableFields']") }
        dropdownOptionSelector { "li a" }
        valuesDropdownButton { $("button[title='Add a value (Alt+V)']") }
        filterDropdownButton { $("button[title='Add a filter (Alt+F)']") }
        filterAceEditor { $("pre[data-bind*='aceEditor'] textarea") }

        // results list
        queryResultsList { $("div#reportResultsGrid div.ko-grid-row") }
        queryResultsListHeaders(required:false) { $("div.ko-grid-column-header span[data-bind='text: header']") }
    }

    def selectOption(CharSequence name) {
        def container = availableFieldsDropdown
        def option
        container.find(dropdownOptionSelector).each {
            if(it.getAttribute("innerHTML").contains(name)) {
                option = it
            }
        }
        assert option
        option.click()
    }

    def addFilter(String filter) {
        filterDropdownButton.click()
        filterAceEditor = filter
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
