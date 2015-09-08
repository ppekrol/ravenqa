package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.ChooseColumnsModalDialog
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class DocumentsPage extends Page {

    static at = {
        newDocumentButton.displayed
    }

    static content = {
        //modules
        topNavigation { module TopNavigationBar }
        alertText { module AlertTextModule }
        deleteResourceModalDialog { module DeleteResourceModalDialog }
        chooseColumnsModalDialog { module ChooseColumnsModalDialog }

        //left panel
        collectionsList { $("ul.document-collections li") }
        collectionsListNameSelector { "span.collection-name-part" }

        //tool bar
        selectAllDocumentsCheckbox(required:false) { $("button[title='Select all or none']") }
        selectAllDocumentsInfoAllDocumentsCountContainer(required:false) { $("strong[data-bind='text: documentCount']") }
        newDocumentButton { $("button[title='Create new document (Alt+N)']") }
        saveButton { $("button[title='Save (Alt+S)']") }
        deleteDocumentButton(required:false) { $("button[data-bind='click: deleteSelectedDocs']") }
        chooseColumnsButton(required:false) { $("button[title='Choose columns...']") }

        // documents list
        documentsList(required:false) { $("div#documentsGrid div.ko-grid-row") }
        documentsListLinksSelector { "a[href^='#databases/edit']" }
        documentsListcheckboxSelector { "img" }
        documentsListHeaders(required:false) { $("div.ko-grid-column-header span[data-bind='text: header']") }
    }

    def deleteDocument(CharSequence name) {
        def checkbox
        documentsList.each {
            def link = it.find(documentsListLinksSelector)
            if(link) {
                if(it.find(documentsListLinksSelector).@href.contains(name)) {
                    checkbox = it.find(documentsListcheckboxSelector)
                }
            }
        }
        assert checkbox

        checkbox.click()
        waitFor { deleteDocumentButton.displayed }
        waitFor { alertText.alert.children().size() == 0 }

        deleteDocumentButton.click()
        waitFor { deleteResourceModalDialog.confirmButton.displayed }
        deleteResourceModalDialog.confirmButton.click()

        // wait until document is not on the list anymore
        waitFor {
            def document
            documentsList.each {
                if(it.displayed) {
                    def link = it.find(documentsListLinksSelector)
                    if(link) {
                        if(it.find(documentsListLinksSelector).@href.contains(name)) {
                            document = it
                        }
                    }
                }
            }
            !document
        }
    }

    def selectCollection(String name) {
        def elementToClick
        collectionsList.each {
            if(it.$(collectionsListNameSelector).text() == name) {
                elementToClick = it
            }
        }

        if(elementToClick) {
            elementToClick.click()
        }
    }

    int getRowsCount() {
        int rowsCount = 0
        documentsList.each {
            if(!it.@style.contains("display: none")) {
                rowsCount += 1
            }
        }

        return rowsCount
    }

    boolean isHeaderPresent(String title) {
        boolean present = false
        documentsListHeaders.each {
            if(it.text().equals(title)) {
                present = true
            }
        }

        return present
    }
}
