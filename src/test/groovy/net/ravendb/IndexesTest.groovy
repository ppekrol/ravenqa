package net.ravendb

import net.ravendb.pages.DocumentsPage
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
}
