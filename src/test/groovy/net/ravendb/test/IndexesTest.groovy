package net.ravendb.test

import net.ravendb.modules.QueryStatsModalDialog
import net.ravendb.pages.DetailsIndexPage
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.EditIndexPage
import net.ravendb.pages.IndexMergeSuggestionsPage
import net.ravendb.pages.IndexTermsPage
import net.ravendb.pages.IndexesPage
import net.ravendb.pages.NewIndexPage

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

        getIndexLink(indexName).click()
        waitFor { NewIndexPage }
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
        sleep(1000)

        expandAllButton.click()
        waitFor { collapseAllButton.displayed }
        sleep(1000)

        // view merge suggestions
        indexMergeSuggestionsButton.click()
        waitFor { at IndexMergeSuggestionsPage }
        waitFor { header.displayed }

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
     * @Step User can change index`s status: idle, disable, abandon, normal.
     * @Step User can lock and unlock index: Lock (Error), Unlock, Lock (side-by-side).
     * @Step User can copy index.
     * @Step User can delete index.
     * @verification Index: idle, disabled, abandoned, normal, locked (error), unlocked, locked (side-by-side),
     * copied, deleted.
     */
    @Test(groups="Smoke")
    void canUseDropdownMenuOptions() {
        at DocumentsPage

        topNavigation.indexesLink.click()
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

        // lock and unlock index
        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_LOCKED_ERROR)
        waitFor { lockErrorIcon.displayed }

        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_UNLOCKED)

        changeLockOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_LOCKED_SIDE_BY_SIDE)

        // copy index
        copyIndex(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
        waitFor { at IndexesPage }
        sleep(1000)

        // delete index
        deleteIndex(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
    }

    /**
     * User can go to index details page.
     * @Step Navigate to Indexes page.
     * @Step Click on the index name.
     * @Step User can run the query and review loaded index`s documents.
     * @Step User can view query stats.
     * @verification Query results list and Query Stats modal dialog displayed.
     */
    @Test(groups="Smoke")
    void canManageIndexDetails() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        getIndexLink(URLEncoder.encode(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, "UTF-8")).click()
        waitFor { at DetailsIndexPage }

        // load index`s documents
        runQueryButton.click()
        waitFor { queryResultsList.size() > 0 }
        waitFor { getRowsCount() > 0 }

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
}
