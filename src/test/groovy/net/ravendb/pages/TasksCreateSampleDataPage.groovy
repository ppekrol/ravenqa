package net.ravendb.pages

import net.ravendb.modules.TasksMenu;
import net.ravendb.modules.TopNavigationBar;
import geb.Page


class TasksCreateSampleDataPage extends Page {

    public final static String DOCUMENTS_NUMBER = "1059"

    public final static String DOCUMENTS_COLLECTION_CATEGORIES = "Categories"
    public final static int DOCUMENTS_COLLECTION_CATEGORIES_COUNT = 8
    public final static String DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_DESCRIPTION = "Description"
    public final static String DOCUMENTS_COLLECTION_CATEGORIES_COLUMN_NAME = "Name"
    public final static String DOCUMENTS_COLLECTION_CATEGORIES_DOCUMENT = "categories/1"

    public final static String DOCUMENTS_COLLECTION_COMPANIES = "Companies"
    public final static int DOCUMENTS_COLLECTION_COMPANIES_COUNT = 91

    public final static String DOCUMENTS_COLLECTION_EMPLOYEES = "Employees"
    public final static int DOCUMENTS_COLLECTION_EMPLOYEES_COUNT = 9
    public final static String DOCUMENTS_COLLECTION_EMPLOYEES_DOCUMENT = "employees/1"

    public final static String DOCUMENTS_COLLECTION_ORDERS = "Orders"
    public final static int DOCUMENTS_COLLECTION_ORDERS_COUNT = 830

    public final static String DOCUMENTS_COLLECTION_PRODUCTS = "Products"
    public final static int DOCUMENTS_COLLECTION_PRODUCTS_COUNT = 77

    public final static String DOCUMENTS_COLLECTION_REGIONS = "Regions"
    public final static int DOCUMENTS_COLLECTION_REGIONS_COUNT = 4

    public final static String DOCUMENTS_COLLECTION_SHIPPERS = "Shippers"
    public final static int DOCUMENTS_COLLECTION_SHIPPERS_COUNT = 3

    public final static String DOCUMENTS_COLLECTION_SUPPLIERS = "Suppliers"
    public final static int DOCUMENTS_COLLECTION_SUPPLIERS_COUNT = 29


    static at = {
        createSampleDataButton.displayed
    }

    static content = {
        topNavigation { module TopNavigationBar }

        menu { module TasksMenu }

        createSampleDataButton { $("button[data-bind='click: generateSampleData']").parent() }
        progressBar { $("div.progress") }
    }

    def createSampleData() {
        createSampleDataButton.click()
        waitFor(10, 0.1) { progressBar.displayed }
        waitFor(10, 0.1) { !progressBar.displayed }
    }
}
