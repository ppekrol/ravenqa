package net.ravendb.test

import net.ravendb.pages.DetailsIndexPage
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.IndexMergeSuggestionsPage
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
            select new {
                order.Date,
                order.Amount,
                RegionId = order.Region.Id
            }
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
	 * User can delete simple index.
	 * @Step Navigate to Indexes page.
	 * @Step Delete index.
	 * @verification Index deleted.
	 */
	@Test(groups="Smoke")
	void canDeleteSimpleIndex() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		clickDropdownOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DELETE)
		waitFor { deleteIndexModalDialog.confirmButton.displayed }

		deleteIndexModalDialog.confirmButton.click()
		alert.waitForMessage(IndexesPage.INDEX_DELETE_SUCCESS + IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
		waitFor { !getIndexLink(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY) }
	}

	/**
	 * User can delete all indexes.
	 * @Step Navigate to Indexes page.
	 * @Step Click on trash icon and delete all indexes.
	 * @verification All indexes deleted.
	 */
	@Test(groups="Smoke")
	void canDeleteAllIndexes() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		getTrashDropdownOption(IndexesPage.TRASH_DROPDOWN_OPTION_DELETE_ALL_INDEXES).click()
		waitFor { deleteIndexModalDialog.confirmButton.displayed }

		deleteIndexModalDialog.confirmButton.click()
		alert.waitForMessage(IndexesPage.DELETE_ALL_INDEXES_SUCCESS)
		waitFor { !getIndexLink(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY) }
		waitFor { !getIndexLink(IndexesPage.INDEX_NAME_ORDERS_TOTALS) }
		waitFor { !getIndexLink(IndexesPage.INDEX_NAME_PRODUCT_SALES) }
	}

	/**
	 * User can disable index.
	 * @Step Navigate to Indexes page.
	 * @Step Disable index.
	 * @verification Index disabled.
	 */
	@Test(groups="Smoke")
	void canDisableIndex() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		clickDropdownOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DISABLED)
		alert.waitForMessage(IndexesPage.INDEX_SAVE_SUCCESS + IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
	}

	/**
	 * User can delete disabled index.
	 * @Step Navigate to Indexes page.
	 * @Step Disable index.
	 * @Step Click on trash icon and delete disabled index.
	 * @verification Disabled index deleted.
	 */
	@Test(groups="Smoke")
	void canDeleteDisabledIndex() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		clickDropdownOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_DISABLED)
		alert.waitForMessage(IndexesPage.INDEX_SAVE_SUCCESS + IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)

		getTrashDropdownOption(IndexesPage.TRASH_DROPDOWN_OPTION_DELETE_DISABLED_INDEXES).click()
		waitFor { deleteIndexModalDialog.confirmButton.displayed }

		deleteIndexModalDialog.confirmButton.click()
		alert.waitForMessage(IndexesPage.INDEX_DELETE_SUCCESS + IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY)
		waitFor { !getIndexLink(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY) }
	}

	/**
	 * User can collapse/expand all indexes list.
	 * @Step Navigate to Indexes page.
	 * @Step Collapse all indexes.
	 * @Step Expand all indexes.
	 * @verification All indexes expanded.
	 */
	@Test(groups="Smoke")
	void canCollapseExpandAllIndexesList() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		collapseAllButton.click()
		waitFor { expandAllButton.displayed }

		expandAllButton.click()
		waitFor { collapseAllButton.displayed }
	}

	/**
	 * User can view query stats.
	 * @Step Navigate to Indexes page.
	 * @Step Navigate to Index`s Details page.
	 * @Step Click Query Stats button.
	 * @verification Query Stats modal dialog displayed and user can close the modal dialog.
	 */
	@Test(groups="Smoke")
	void canViewQueryStats() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		getIndexLink(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY_LINK).click()
		waitFor { at DetailsIndexPage }

		queryStatsButton.click()
		waitFor { queryStatsModalDialog.header.displayed }
		waitFor { queryStatsModalDialog.okButton.displayed }

		queryStatsModalDialog.okButton.click()
		waitFor { at DetailsIndexPage }
	}

	/**
	 * User can view merge suggestions.
	 * @Step Navigate to Indexes page.
	 * @Step Click Index merge suggestions button.
	 * @Step Navigate to Merge Suggestions page.
	 * @verification Merge Suggestions page displayed.
	 */
	@Test(groups="Smoke")
	void canViewMergeSuggestions() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		indexMergeSuggestionsButton.click()
        waitFor { at IndexMergeSuggestionsPage }
		waitFor { header.displayed }
	}

	/**
	 * User can copy index.
	 * @Step Navigate to Indexes page.
	 * @Step Copy index.
	 * @verification Copy Index modal dialog displayed and user can close the modal dialog.
	 */
	@Test(groups="Smoke")
	void canCopyIndex() {
		at DocumentsPage

		topNavigation.indexesLink.click()
		waitFor { at IndexesPage }

		clickDropdownOption(IndexesPage.INDEX_NAME_ORDERS_BY_COMPANY, IndexesPage.INDEX_TOGGLE_OPTION_COPY)
		waitFor { copyIndexModalDialog.header.displayed }
		waitFor { copyIndexModalDialog.closeButton.displayed }

		copyIndexModalDialog.closeButton.click()
		waitFor { at IndexesPage }
	}
}
