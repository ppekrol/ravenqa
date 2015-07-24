package net.ravendb.test

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.NewDocumentPage

import org.testng.annotations.Test


class DocumentsTest extends DatabaseWithSampleDataTestBase {

    /**
     * User can create new document with default configuration.
     * @Step Navigate to resources page
     * @Step Click Create New Resource Button.
     * @Step Fill database name input and click Create button.
     * @Step Click on created database.
     * @Step Delete created database.
     * @verification Database created and user can navigate to it, database deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteDocumentWithDefaultData() {
        at DocumentsPage

        newDocumentButton.click()
        waitFor { at NewDocumentPage }

        CharSequence documentName = "doc" + rand.nextInt()
        createAndSaveDocument(documentName)

        topNavigation.documentsLink.click()
        waitFor { at DocumentsPage }

        // wait for documents list to load
        waitFor { documentsList.size() > 0 }

        // search for our document
        def documentLink
        documentsList.each {
            def link = it.find(documentsListLinksSelector)
            if(link) {
                if(it.find(documentsListLinksSelector).@href.contains(documentName)) {
                    documentLink = it
                }
            }
        }
        assert documentLink

        deleteDocument(documentName)
    }
}
