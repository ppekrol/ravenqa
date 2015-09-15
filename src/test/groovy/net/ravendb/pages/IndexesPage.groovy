package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.CopyIndexModalDialog
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class IndexesPage extends Page {

	final static String INDEX_NAME_ORDERS_BY_COMPANY = "Orders/ByCompany"
	final static String INDEX_NAME_ORDERS_TOTALS = "Orders/Totals"
	final static String INDEX_NAME_PRODUCT_SALES = "Product/Sales"
    final static String INDEX_NAME_TEST_ORDERS_BY_COMPANY = "Test/Orders/ByCompany"

	final static String INDEX_TOGGLE_OPTION_COPY = "Copy index"
	final static String INDEX_TOGGLE_OPTION_DELETE = "Delete Index"
	final static String INDEX_TOGGLE_OPTION_UNLOCKED = "Unlocked"
	final static String INDEX_TOGGLE_OPTION_LOCKED_SIDE_BY_SIDE = "Locked (side-by-side)"
	final static String INDEX_TOGGLE_OPTION_LOCKED_ERROR = "Locked (Error)"
	final static String INDEX_TOGGLE_OPTION_NORMAL = "Normal"
	final static String INDEX_TOGGLE_OPTION_IDLE = "Idle"
	final static String INDEX_TOGGLE_OPTION_DISABLED = "Disabled"
	final static String INDEX_TOGGLE_OPTION_ABANDONED = "Abandoned"

	final static String INDEX_DELETE_SUCCESS = "Deleted "
	final static String INDEX_SAVE_SUCCESS = "Saved "

	final static String TRASH_DROPDOWN_OPTION_DELETE_ALL_INDEXES = "Delete All Indexes"
	final static String TRASH_DROPDOWN_OPTION_DELETE_DISABLED_INDEXES = "Delete Disabled Indexes"
	final static String DELETE_ALL_INDEXES_SUCCESS = "Successfully deleted 2 indexes!"

    static at = {
        newIndexButton
    }

    static content = {
		// modules
        topNavigation { module TopNavigationBar }
		deleteIndexModalDialog { module DeleteResourceModalDialog }
		copyIndexModalDialog { module CopyIndexModalDialog }
		alert { module AlertTextModule }

		// tool bar
		newIndexButton { $("a[title='Add a new index (Alt+N)']") }
		menuToolbar { $('.btn-toolbar') }
		trashButton { "i.fa-trash-o" }
		deleteDropdownMenu { "ul.dropdown-menu li" }
		collapseAllButton { $("a[title='Collapse all']") }
		expandAllButton { $("a[title='Expand all']") }
		indexMergeSuggestionsButton { $("a[title='Index merge suggestions']") }

        indexesLinks(required:false) { $("a[href^='#databases/query/index/']") }

		// index panel
		indexRowContainer { $('.index-panel.panel.panel-default') }
        indexEditButton { "a.indexes-controls[data-bind='attr: { href: editUrl }']" }
		indexRowButtonSelector { "button" }
		indexRowLinkSelector { "li[role='presentation'] a" }
		indexStatusContainer { "small" }

		// lock icon
		lockErrorIcon { $('i.fa-unlock.text-danger') }
    }

    def getIndexLink(CharSequence name) {
        def link
        indexesLinks.each {
            if(it.@href.contains(name)) {
                link = it
            }
        }
        link
    }

	def getIndexContainer(CharSequence name) {
		def container
		indexRowContainer.each {
			if(it.getAttribute("innerHTML").contains(name)) {
				container = it
			}
		}
		container
	}

	def clickDropdownOption(CharSequence indexName, CharSequence optionName) {
		def container = getIndexContainer(indexName)
		container.find(indexRowButtonSelector).click()
		def link
		container.find(indexRowLinkSelector).each {
			if(it.getAttribute("innerHTML").contains(optionName)) {
				link = it
			}
		}
		assert link
		link.click()
	}

    def clickEditButton(CharSequence indexName) {
        def container = getIndexContainer(indexName)
        container.find(indexEditButton).click()
    }

	def getTrashDropdownOption(CharSequence optionName) {
		def container = menuToolbar
		container.find(trashButton).click()
		def link
		container.find(deleteDropdownMenu).each {
			if(it.getAttribute("innerHTML").contains(optionName)) {
				link = it
			}
		}
		link
	}

	def getIndexStatusContainer(CharSequence indexName, CharSequence statusName) {
		def container = getIndexContainer(indexName)
		def status
		container.find(indexStatusContainer).each {
			if(it.getAttribute("innerHTML").contains(statusName)) {
				status = it
			}
		}
		status
	}

	def clickTrashDropdownOption(String name) {
		getTrashDropdownOption(name).click()
		waitFor { deleteIndexModalDialog.confirmButton.displayed }
		deleteIndexModalDialog.confirmButton.click()
	}

	def copyIndex(String name) {
		clickDropdownOption(name, INDEX_TOGGLE_OPTION_COPY)
		waitFor { copyIndexModalDialog.header.displayed }
		waitFor { copyIndexModalDialog.closeButton.displayed }

		copyIndexModalDialog.closeButton.click()
	}

	def deleteIndex(String name) {
		clickDropdownOption(name, INDEX_TOGGLE_OPTION_DELETE)
		waitFor { deleteIndexModalDialog.confirmButton.displayed }

		deleteIndexModalDialog.confirmButton.click()
		alert.waitForMessage(INDEX_DELETE_SUCCESS + name)
		waitFor { !getIndexLink(name) }
	}

	def changeStatus(String indexName, String indexStatus) {
		clickDropdownOption(indexName, indexStatus)
		alert.waitForMessage(INDEX_SAVE_SUCCESS + indexName)
	}

	def changeLockOption(String indexName, String lockName) {
		clickDropdownOption(indexName, lockName)
		alert.waitForMessage(INDEX_SAVE_SUCCESS + indexName)
	}
}
