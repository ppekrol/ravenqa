package net.ravendb.test

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.NewDocumentPage
import net.ravendb.pages.TasksCreateSampleDataPage;

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

    /**
     * User can select collections on documents list.
     * @Step Navigate to documents page.
     * @Step Click collections
     * @verification Documents properly filtered.
     */
    @Test(groups="Smoke")
    void canSelectCollectionsOnDocumentsPage() {
        at DocumentsPage

        selectCollection(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES)
        assert getRowsCount() == TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES_COUNT
        selectCollection(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_EMPLOYEES)
        assert getRowsCount() == TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_EMPLOYEES_COUNT
        selectCollection(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_REGIONS)
        assert getRowsCount() == TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_REGIONS_COUNT
        selectCollection(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_SHIPPERS)
        assert getRowsCount() == TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_SHIPPERS_COUNT
    }

    /**
     * User can choose columns on documents list.
     * @Step Navigate to documents page.
     * @Step Choose columns
     * @verification Columns filtered properly.
     */
    @Test(groups="Smoke")
    void canChooseColumnsOnDocumentsPage() {
        at DocumentsPage

        selectCollection(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES)

        chooseColumnsButton.click()
        waitFor { chooseColumnsModalDialog.header.displayed }

        chooseColumnsModalDialog.columnsButton.click()
        chooseColumnsModalDialog.columnsCustom.click()
        chooseColumnsModalDialog.deleteColumn(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_NAME)
        chooseColumnsModalDialog.okButton.click()
        waitFor { chooseColumnsButton.displayed }
        assert !isHeaderPresent(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_NAME)

        chooseColumnsButton.click()
        waitFor { chooseColumnsModalDialog.header.displayed }

        chooseColumnsModalDialog.addColumn(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_NAME, TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_NAME)
        chooseColumnsModalDialog.okButton.click()
        waitFor { chooseColumnsButton.displayed }
        assert isHeaderPresent(TasksCreateSampleDataPage.DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_NAME)
    }
}
