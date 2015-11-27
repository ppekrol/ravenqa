package net.ravendb.test

import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.pages.DetailsIndexPage
import net.ravendb.pages.DocumentPage
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.EditIndexPage
import net.ravendb.pages.IndexMergeSuggestionsPage
import net.ravendb.pages.IndexTermsPage
import net.ravendb.pages.IndexesPage
import net.ravendb.pages.NewIndexPage
import net.ravendb.pages.QueryDataExplorationPage
import net.ravendb.pages.QueryReportingPage

import org.testng.annotations.Test


class IndexesTest extends DatabaseWithSampleDataTestBase {

    /**
     * User can create and delete simple index.
     * @Step Navigate to Indexes page.
     * @Step Create and save new index.
     * @Step Delete index.
     * @verification Index created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteSimpleIndex() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        newIndexButton.click()
        waitFor { at NewIndexPage }

        String indexName = "i" + rand.nextInt()
        def maps = [
            """
            from order in docs.Orders
            where order.IsShipped
            select new {order.Date,order.Amount,RegionId = order.Region.Id}
            """.toString()
        ]
        createAndSaveIndex(indexName, maps)

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }
        waitFor { getIndexLink(indexName) }
        deleteIndex(indexName)
    }

    /**
     * User can use tool bar menu options.
     * @Step Navigate to Indexes page.
     * @Step User can collapse/expand all indexes list.
     * @Step User can view merge suggestions.
     * @Step User can delete disabled index.
     * @Step User can delete all indexes.
     * @verification All indexes collaps/expanded, Merge Suggestions page displayed
     * disabled index deleted, all indexes deleted.
     */
    @Test(groups="Smoke")
    void canUseToolbarMenuOptions() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        // collapse/expand all indexes list
        collapseAllButton.click()
        waitFor { expandAllButton.displayed }
        waitFor { !indexesLinks.displayed }

        expandAllButton.click()
        waitFor { collapseAllButton.displayed }
        waitFor { indexesLinks.displayed }

        // view merge suggestions
        indexMergeSuggestionsButton.click()
        waitFor { at IndexMergeSuggestionsPage }
        waitFor { header.displayed }

        collapseUnmergableLink.click()
        waitFor { unmergablePanel.displayed }

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        // delete disabled index
        changeStatus(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DISABLED)

        clickTrashDropdownOption(IndexesPage.TRASH_DROPDOWN_OPTION_DELETE_DISABLED_INDEXES)
        alert.waitForMessage(IndexesPage.INDEX_DELETE_SUCCESS + IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
        waitFor { !getIndexLink(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY) }

        // delete all indexes
        clickTrashDropdownOption(IndexesPage.TRASH_DROPDOWN_OPTION_DELETE_ALL_INDEXES)
        alert.waitForMessage(IndexesPage.DELETE_ALL_INDEXES_SUCCESS)
        waitFor { !getIndexLink(IndexesPage.INDEX_NAME_ORDERS_TOTALS) }
        waitFor { !getIndexLink(IndexesPage.INDEX_NAME_PRODUCT_SALES) }
    }

    /**
     * User can use drop down menu actions.
     * @Step Navigate to Indexes page.
     * @Step User can delete index.
     * @Step User can change index`s status: idle, disable, abandon, normal.
     * @Step User can lock and unlock index: Lock (side-by-side), Lock, Lock (Error), Unlock.
     * @Step User can copy index.
     * @verification Index: deleted, idle, disabled, abandoned, normal, locked (error), unlocked, locked (side-by-side),
     * copied.
     */
    @Test(groups="Smoke")
    void canUseDropdownMenuOptions() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        // delete index
        deleteIndex(IndexesPage.INDEX_NAME_ORDERS_TOTALS)
        waitFor { at IndexesPage }

        // change index's status
        changeStatus(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_IDLE)
        waitFor { getIndexStatusContainer(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_IDLE).displayed }

        changeStatus(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DISABLED)
        waitFor { getIndexStatusContainer(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DISABLED).displayed }

        changeStatus(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_ABANDONED)
        waitFor { getIndexStatusContainer(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_ABANDONED).displayed }

        changeStatus(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_NORMAL)
        waitFor { !getIndexStatusContainer(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_IDLE).displayed }
        waitFor { !getIndexStatusContainer(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DISABLED).displayed }
        waitFor { !getIndexStatusContainer(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_ABANDONED).displayed }

        // lock (side-by-side), lock, lock (Error), unlock index
        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_LOCKED_SIDE_BY_SIDE)
        waitFor { getLockIcon(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY).displayed }

        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_LOCKED)
        waitFor { getLockIcon(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY).displayed }

        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_LOCKED_ERROR)
        waitFor { getLockErrorIcon(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY).displayed }

        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_UNLOCKED)
        waitFor { getUnlockIcon(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY).displayed }

        // copy index
        copyIndex(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
        waitFor { at IndexesPage }
    }

    /**
     * User can go to index details page and view query stats.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step User can view query stats.
     * @verification Query Stats modal dialog displayed.
     */
    @Test(groups="Smoke")
    void canViewIndexQueryStats() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        // view query stats
        queryStatsButton.click()
        waitFor { queryStatsModalDialog.header.displayed }
        waitFor { queryStatsModalDialog.getQueryStatsData(QueryStatsModalDialog.QUERY_STATS_TOTAL_RESULTS).displayed }
        waitFor { queryStatsModalDialog.getQueryStatsData(QueryStatsModalDialog.QUERY_STATS_STATUS).displayed }
        waitFor { queryStatsModalDialog.okButton.displayed }

        queryStatsModalDialog.okButton.click()
        waitFor { at DetailsIndexPage }
    }

    /**
     * User can view document by clicking on it on query results list.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step Click on the document link.
     * @verification Document page displayed.
     */
    @Test(groups="Smoke")
    void canViewDocumentByClickingOnItOnQueryResultsList() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        // load index`s documents
        runQueryButton.click()
        waitFor { queryResultsList.size() > 0 }
        waitFor { getRowsCount() > 0 }

        // view document by clicking on it
        clickDocument(DetailsIndexPage.INDEX_QUERY_RESULTS_COLLECTION_COMPANIES_DOCUMENT)
        waitFor { at DocumentPage }

        assert documentNameInput.value() == DetailsIndexPage.INDEX_QUERY_RESULTS_COLLECTION_COMPANIES_DOCUMENT
    }

    /**
     * User can change columns displayed in index query results panel.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step Choose columns.
     * @Step Delete column.
     * @Step Add new column.
     * @verification Columns filtered properly.
     */
    @Test(groups="Smoke")
    void canChooseColumnsOnIndexDetailsPage() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        chooseColumnsButton.click()
        waitFor { chooseColumnsModalDialog.header.displayed }

        chooseColumnsModalDialog.columnsButton.click()
        chooseColumnsModalDialog.columnsCustom.click()
        chooseColumnsModalDialog.deleteColumn(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)
        chooseColumnsModalDialog.okButton.click()
        waitFor { chooseColumnsButton.displayed }
        assert !isHeaderPresent(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)

        runQueryButton.click()
        waitFor { queryResultsList.size() > 0 }

        chooseColumnsButton.click()
        waitFor { chooseColumnsModalDialog.header.displayed }

        chooseColumnsModalDialog.addColumn(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY, DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)
        chooseColumnsModalDialog.okButton.click()
        waitFor { chooseColumnsButton.displayed }
        waitFor { queryResultsListHeaders.displayed }
        assert isHeaderPresent(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)
    }

    /**
     * User can display index's terms.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step Click on the Terms button.
     * @Step Expand all terms.
     * @verification Index`s terms displayed properly.
     */
    @Test(groups="Smoke")
    void canDisplayIndexTerms() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        termsButton.click()
        waitFor { at IndexTermsPage }

        clickTermsLink(IndexTermsPage.TERMS_NAME_COMPANY)
        assert termsItems.size() > 0
    }

    /**
     * User can use Try Index option.
     * @Step Navigate to Indexes page.
     * @Step Go to Edit index page.
     * @Step Try index.
     * @Step Navigate to Indexes page and edit test index.
     * @Step Make permanent.
     * @verification Try index and Make permanent options work properly.
     */
    @Test(groups="Smoke")
    void canUseTryIndexOption() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        clickEditButton(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
        waitFor { at EditIndexPage }

        clickEditIndexOption(EditIndexPage.TRY_INDEX)
        alert.waitForMessage(IndexesPage.INDEX_SAVE_SUCCESS + IndexesPage.INDEX_NAME_TEST_ORDERS_BY_COMPANY)
        waitFor { warningContainer.displayed }

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        clickEditButton(IndexesPage.INDEX_NAME_TEST_ORDERS_BY_COMPANY)
        waitFor { at EditIndexPage }

        clickEditIndexOption(EditIndexPage.MAKE_PERMANENT)
        alert.waitForMessage(IndexesPage.INDEX_DELETE_SUCCESS + IndexesPage.INDEX_NAME_TEST_ORDERS_BY_COMPANY)
        waitFor { !warningContainer.displayed }
    }

    /**
     * User can switch index using breadcrumbs link on index details page.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step Click breadcrumbs link.
     * @Step Click on the index name.
     * @verification Switching indexes using breadcrumbs link works properly.
     */
    @Test(groups="Smoke")
    void canSwitchIndexUsingBreadcrumbsLinkOnIndexDetailsPage() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        indexesBreadcrumbs.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_TOTALS, "UTF-8")).click()
        waitFor { at DetailsIndexPage }
    }

    /**
     * User can filter index query results.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step Use string filter.
     * @Step Use range filter.
     * @Step Use in filter.
     * @verification Index`s query results filtered properly.
     */
    @Test(groups="Smoke")
    void canFilterIndexQueryResults() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        // string filter
        selectColumnToFilter(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)
        waitFor { !(stringFilterButton.@disabled == 'true') }
        stringFilterButton.click()
        waitFor { stringFieldFilterModalHeader.displayed }
        fieldSubTextInput = "companies"
        waitFor { !(applyButton.@disabled == 'true') }
        applyButton.click()
        waitFor { runQueryButton.click() }

        // range filter
        selectColumnToFilter(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COUNT)
        rangeFilterButton.click()
        waitFor { rangeFilterModalHeader.displayed }
        fromInput = "1"
        toInput = "2"
        applyButton.click()
        waitFor { runQueryButton.click() }

        // in filter
        selectColumnToFilter(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_TOTAL)
        inFilterButton.click()
        waitFor { addInFilterModalHeader.displayed }
        searchValueInput = "100.8"
        applyButton.click()
        waitFor { runQueryButton.click() }

        assert getRowsCount() == 1
    }

    /**
     * User can run simple query on index.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step Add multiple Sort By statements.
     * @Step Add transformer.
     * @Step Run the query.
     * @verification Results are valid.
     */
    @Test(groups="Smoke")
    void canRunSimpleQueryOnIndex() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        addButton.click()
        sortByOption.click()
        selectDropdownAndOptionToSort(DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_SELECT_A_FIELD, DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)

        addButton.click()
        sortByOption.click()
        selectDropdownAndOptionToSort(DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_SELECT_A_FIELD, DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COUNT)
        selectDropdownAndOptionToSort(DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_ASCENDING, DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_DESCENDING)

        addButton.click()
        sortByOption.click()
        selectDropdownAndOptionToSort(DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_SELECT_A_FIELD, DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_TOTAL)
        selectDropdownAndOptionToSort(DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_ASCENDING, DetailsIndexPage.INDEX_QUERY_RESULTS_SORT_BY_RANGE_ASCENDING)

        addButton.click()
        transformerOption.click()
        selectTransformer(DetailsIndexPage.INDEX_QUERY_RESULTS_TRANSORMER_ORDERS_COMPANY)

        runQueryButton.click()
        waitFor { queryResultsList.size() > 0 }
        waitFor { getRowsCount() > 0 }
        assert isHeaderPresent(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_VALUE)
        assert !isHeaderPresent(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COMPANY)
        assert !isHeaderPresent(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_COUNT)
        assert !isHeaderPresent(DetailsIndexPage.INDEX_QUERY_RESULTS_COLUMN_TOTAL)
    }

    /**
     * User can generate C# index definition.
     * @Step Navigate to Indexes page.
     * @Step Click on the edit index button.
     * @Step Click on the C# button.
     * @verification C# modal window displayed properly.
     */
    @Test(groups="Smoke")
    void canGenerateCsharpIndexDefinition() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        clickEditButton(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
        waitFor { at EditIndexPage }

        csharpButton.click()
        waitFor { csharpIndexDefinitionModalHeader.displayed }

        assert csharpIndexDefinitionCodeContainer.value().replaceAll("\\s","") == EditIndexPage.CSHARP_INDEX_DEFINITION_DOCUMENT_CODE
    }

    /**
     * User can create complex index.
     * @Step Navigate to Indexes page.
     * @Step Create and save new index with all available options.
     * @verification Index created.
     */
    @Test(groups="Smoke")
    void canCreateComplexIndex() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        newIndexButton.click()
        waitFor { at NewIndexPage }

        String indexName = "i" + rand.nextInt()
        def maps = [
            """
            from order in docs.Orders
            where order.IsShipped
            select new {Count=1,order.Amount,RegionId = order.Region.Id}
            """.toString()
        ]
        def reduce = [
            """
            from result in results
            group result by result.RegionId into g
            select new {Count = g.Sum(x => x),RegionId = g.Key,Amount = g.Sum(x => x.Amount)}
            """.toString()
        ]
        String field = "Count"
        String spatialField = "Amount"
        String maxIndexOutputs = "3"

        clickAddButtonAndSelectOption(NewIndexPage.NEW_INDEX_ADD_OPTION_BUTTON, NewIndexPage.NEW_INDEX_MAP_OPTION)
        clickAddButtonAndSelectOption(NewIndexPage.NEW_INDEX_ADD_OPTION_BUTTON, NewIndexPage.NEW_INDEX_REDUCE_OPTION)
        clickAddButtonAndSelectOption(NewIndexPage.NEW_INDEX_ADD_OPTION_BUTTON, NewIndexPage.NEW_INDEX_FIELD_OPTION)
        clickAddButtonAndSelectOption(NewIndexPage.NEW_INDEX_ADD_OPTION_BUTTON, NewIndexPage.NEW_INDEX_SPATIAL_FIELD_OPTION)
        clickAddButtonAndSelectOption(NewIndexPage.NEW_INDEX_ADD_OPTION_BUTTON, NewIndexPage.NEW_INDEX_MAX_INDEX_OUTPUTS_OPTION)
        clickAddButtonAndSelectOption(NewIndexPage.NEW_INDEX_ADD_OPTION_BUTTON, NewIndexPage.NEW_INDEX_STORE_ALL_FIELDS_OPTION)

        createAndSaveComplexIndex(indexName, maps, reduce, field, spatialField, maxIndexOutputs)
        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }
        waitFor { getIndexLink(indexName) }

        getIndexLink(indexName).click()
        waitFor { NewIndexPage }
    }

    /**
     * User can reset index.
     * @Step Navigate to Indexes page.
     * @Step User can reset index.
     * @verification Index is reset properly.
     */
    @Test(groups="Smoke")
    void canResetIndex() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        resetIndex(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
        waitFor { at DetailsIndexPage }
    }

    /**
     * User can use query data exploration tool.
     * @Step Navigate to Query page.
     * @Step Navigate to the data exploration tool.
     * @Step Create and run the data exploration tool.
     * @verification Query exploration results displayed properly.
     */
    @Test(groups="Smoke")
    void canUseQueryDataExplorationTool() {
        at DocumentsPage

        topNavigation.queryLink.click()
        waitFor { at DetailsIndexPage }

        topNavigation.switchToDataExplorationTool()
        waitFor { at QueryDataExplorationPage }

        int timeout = 100
        int maxDocumentsToScan = 4000
        def linq = """
            from result in results
            select new { result.Name, result.Id, result.Description }
            """.toString()

        createAndRunDataExploration(QueryDataExplorationPage.QUERY_COLLECTION_OPTION_CATEGORIES, linq, timeout, maxDocumentsToScan)
        waitFor { queryResultsList.size() > 0 }

        assert getRowsCount() == 8
        assert isHeaderPresent(QueryDataExplorationPage.QUERY_RESULTS_COLUMN_ID)
        assert isHeaderPresent(QueryDataExplorationPage.QUERY_RESULTS_COLUMN_NAME)
        assert isHeaderPresent(QueryDataExplorationPage.QUERY_RESULTS_COLUMN_DESCRIPTION)
    }

    /**
     * User can use query reporting tool.
     * @Step Navigate to Query page.
     * @Step Navigate to the reporting tool.
     * @Step Run the reporting tool.
     * @verification Query report results displayed properly.
     */
    @Test(groups="Smoke")
    void canUseQueryReportingTool() {
        at DocumentsPage

        topNavigation.queryLink.click()
        waitFor { at DetailsIndexPage  }

        topNavigation.switchToReportingTool()
        waitFor { at QueryReportingPage }

        groupByDropdownButton.click()
        selectOption(QueryReportingPage.QUERY_AVAILABLE_FIELDS_OPTION_TAG)
        valuesDropdownButton.click()
        selectOption(QueryReportingPage.QUERY_AVAILABLE_FIELDS_OPTION_LAST_MODIFIED_TICKS)
        String filter = "Tag:Categories"
        addFilter(filter)
        runReportButton.click()
        waitFor { queryResultsList.size() > 0 }

        assert getRowsCount() == 1
        assert isHeaderPresent(QueryReportingPage.QUERY_RESULTS_COLUMN_KEY)
        assert isHeaderPresent(QueryReportingPage.QUERY_RESULTS_COLUMN_COUNT_OF_LAST_MODIFIED_TICKS)
    }
}
