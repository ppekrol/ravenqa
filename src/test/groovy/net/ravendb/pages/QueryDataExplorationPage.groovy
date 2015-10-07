package net.ravendb.pages

import geb.Page


class QueryDataExplorationPage extends Page {

    final static String QUERY_COLLECTION_OPTION_CATEGORIES = "Categories"
    final static String QUERY_RESULTS_COLUMN_ID = "Id"
    final static String QUERY_RESULTS_COLUMN_NAME = "Name"
    final static String QUERY_RESULTS_COLUMN_DESCRIPTION = "Description"

    static at = {
        runButton.displayed
    }

    static content = {
        // tool bar
        runButton { $("button[title='Run']") }
        selectCollectionButton { $("button[title='Collection']") }
        collectionDropdown { $("ul.dropdown-menu[data-bind='foreach: collections']") }

        // form
        timeoutInput { $("input[data-bind*='explorationRequest.timeoutSeconds']") }
        maxDocumentsToScanInput { $("input[data-bind*='value: explorationRequest.pageSize']") }
        linqAceEditor { $("pre[data-bind*='aceEditor'] textarea") }

        // results list
        queryResultsList { $("div#explorationResultsGrid div.ko-grid-row") }
        queryResultsListHeaders(required:false) { $("div.ko-grid-column-header span[data-bind='text: header']") }
    }

    def clickCollectionOption(CharSequence name) {
        selectCollectionButton.click()
        def container = collectionDropdown
        def option
        container.$("a").each {
            if(it.getAttribute("innerHTML").contains(name)) {
                option = it
            }
        }
        assert option
        option.click()
    }

    def createAndRunDataExploration(CharSequence collectionName, String linq, int timeout, int maxDocumentsToScan) {
        clickCollectionOption(collectionName)
        timeoutInput = timeout
        maxDocumentsToScanInput = maxDocumentsToScan
        linqAceEditor = linq

        waitFor { !(runButton.@disabled == 'true') }
        runButton.click()
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