package project;

public class databaseMandatoryTables {

    private String user, company, sr_contact, inventory, batch, customer, orders, invoice, debits, due, company_due, basic;
    private String userDetails, companyDetails, sr_contactDetails, inventoryDetails, batchDetails, customerDetails,
            ordersDetails, invoiceDetails, debitsDetails, dueDetails, company_dueDetails, basicDetails;

    public String[] tableNames(){
        setNames();
        String [] names={user, company, sr_contact, inventory, batch, customer, orders, invoice, debits, due, company_due, basic};
        return names;
    }

    public String [] tabelDetails(){
        String [] tableDetails={getUser(), getCompany(), getSr_contact(), getInventory(), getBatch(), getCustomer(),
                                    getOrders(), getInvoice(), getDebits(), getDue(), getCompany_due(), getBasic()};
        return tableDetails;
    }

    private void setNames(){
        user="user";
        company="company";
        sr_contact="sr_contact";
        inventory="inventory";
        batch="batch";
        customer="customer";
        orders="orders";
        invoice="invoice";
        debits="debits";
        due="due";
        company_due="company_due";
        basic="basic";
    }

    public String getUser() {
        setUserDetails();
        return userDetails;
    }

    private void setUserDetails() {
        userDetails="user_ID INT NOT NULL AUTO_INCREMENT,"+
                "name VARCHAR(45) NOT NULL, "+
                "password VARCHAR(32) NOT NULL, "+
                "phone_number VARCHAR(15) NULL, "+
                "address VARCHAR(100) NULL, "+
                "email VARCHAR(50) NULL, "+
                "backgroundColor VARCHAR(15) NULL, "+
                "inventory_access INT NOT NULL, "+
                "batch_access INT NOT NULL, "+
                "order_access INT NOT NULL, "+
                "debits_access INT NOT NULL, "+
                "admin_access INT NOT NULL, "+
                "user_type VARCHAR(32) NOT NULL, "+
                "PRIMARY KEY (user_ID), "+
                "UNIQUE INDEX name_UNIQUE (name ASC), "+
                "UNIQUE INDEX user_ID_UNIQUE (user_ID ASC)";
    }

    public String getCompany() {
        setCompanyDetails();
        return companyDetails;
    }

    private void setCompanyDetails() {
        companyDetails = "company_ID INT NOT NULL AUTO_INCREMENT, "+
                    "company_name VARCHAR(45) NOT NULL, "+
                    "company_phone_number VARCHAR(15) NULL, "+
                    "company_email VARCHAR(45) NULL, "+
                    "company_address VARCHAR(150) NULL, "+
                    "date DATETIME NOT NULL, "+
                    "user_name VARCHAR(45) NOT NULL, "+
                    "PRIMARY KEY (company_ID), "+
                    "UNIQUE INDEX company_ID_UNIQUE (company_ID ASC), "+
                    "INDEX fk_company_user1_idx (user_name ASC), "+
                    "CONSTRAINT fk_company_user1 "+
                    "FOREIGN KEY (user_name) "+
                    "REFERENCES shop_management_database.user (name) "+
                    "ON DELETE NO ACTION "+
                    "ON UPDATE NO ACTION";
    }

    public String getSr_contact() {
        setSr_contactDetails();
        return sr_contactDetails;
    }

    private void setSr_contactDetails() {
        sr_contactDetails = "sr_ID INT NOT NULL AUTO_INCREMENT, "+
                                "sr_name VARCHAR(45) NOT NULL, "+
                                "sr_company VARCHAR(45) NOT NULL, "+
                                "sr_phone_number  VARCHAR(15) NULL, "+
                                "sr_email VARCHAR(45) NULL, "+
                                "sr_address  VARCHAR(150) NULL, "+
                                "date DATETIME NOT NULL, "+
                                "user_name VARCHAR(45) NOT NULL, "+
                                "PRIMARY KEY (sr_ID), "+
                                "UNIQUE INDEX cr_ID_UNIQUE (sr_ID ASC), "+
                                "INDEX fk_sr_contact_user1_idx (user_name ASC), "+
                                "INDEX fk_sr_contact_company1_idx (sr_company ASC), "+
                                "CONSTRAINT  fk_sr_contact_user1 "+
                                "FOREIGN KEY (user_name) "+
                                "REFERENCES shop_management_database.user (name) "+
                                "ON DELETE NO ACTION "+
                                "ON UPDATE NO ACTION, "+
                                "CONSTRAINT fk_sr_contact_company1 "+
                                "FOREIGN KEY (sr_company) "+
                                "REFERENCES  shop_management_database.company (company_name) "+
                                "ON DELETE NO ACTION "+
                                "ON UPDATE NO ACTION";
    }

    public String getInventory() {
        setInventoryDetails();
        return inventoryDetails;
    }

    private void setInventoryDetails() {
        inventoryDetails = "product_ID INT NOT NULL AUTO_INCREMENT, "+
                                "product_name VARCHAR(45) NOT NULL, "+
                                "company_name VARCHAR(45) NOT NULL, "+
                                "quantity DOUBLE NOT NULL, "+
                                "sell_price DOUBLE NOT NULL, "+
                                "buy_price DOUBLE NOT NULL, "+
                                "max_discount DOUBLE NOT NULL, "+
                                "sr_name VARCHAR(45) NULL, "+
                                "sr_phone_number VARCHAR(15) NULL, "+
                                "starting_inventory DOUBLE NULL, "+
                                "minimum_require DOUBLE NULL, "+
                                "category VARCHAR(30) NULL, "+
                                "product_description VARCHAR(150) NULL, "+
                                "date DATETIME NOT NULL, "+
                                "user_name VARCHAR(45) NULL, "+
                                "PRIMARY KEY (product_ID), "+
                                "UNIQUE INDEX product_ID_UNIQUE (product_ID ASC), "+
                                "UNIQUE INDEX product_name_UNIQUE (product_name ASC), "+
                                "INDEX fk_inventory_user1_idx (user_name ASC), "+
                                "INDEX fk_inventory_company1_idx (company_name ASC), "+
                                "INDEX fk_inventory_sr_contact1_idx (sr_name ASC), "+
                                "INDEX fk_inventory_sr_contact2_idx (sr_phone_number ASC), "+
                                "CONSTRAINT fk_inventory_user1 "+
                                "FOREIGN KEY (user_name) "+
                                "REFERENCES shop_management_database.user (name) "+
                                "ON DELETE NO ACTION "+
                                "ON UPDATE NO ACTION, "+
                                "CONSTRAINT fk_inventory_company1 "+
                                "FOREIGN KEY (company_name) "+
                                "REFERENCES shop_management_database.company (company_name) "+
                                "ON DELETE NO ACTION "+
                                "ON UPDATE NO ACTION, "+
                                "CONSTRAINT fk_inventory_sr_contact1 "+
                                "FOREIGN KEY (sr_name) "+
                                "REFERENCES shop_management_database.sr_contact (sr_name) "+
                                "ON DELETE NO ACTION "+
                                "ON UPDATE NO ACTION, "+
                                "CONSTRAINT fk_inventory_sr_contact2 "+
                                "FOREIGN KEY (sr_phone_number) "+
                                "REFERENCES shop_management_database.sr_contact (sr_phone_number) "+
                                "ON DELETE NO ACTION "+
                                "ON UPDATE NO ACTION";
    }

    public String getBatch() {
        setBatchDetails();
        return batchDetails;
    }

    private void setBatchDetails() {
        batchDetails = "batch_ID VARCHAR(10) NOT NULL, "+
                            "product_ID INT NOT NULL, "+
                            "product_name VARCHAR(45) NOT NULL, "+
                            "buy_price DOUBLE NOT NULL, "+
                            "sell_price DOUBLE NOT NULL, "+
                            "max_discount DOUBLE NOT NULL, "+
                            "quantity DOUBLE NOT NULL, "+
                            "buy_date DATETIME NOT NULL, "+
                            "sr_name VARCHAR(45) NULL, "+
                            "cash_paid DOUBLE NOT NULL, "+
                            "due DOUBLE NOT NULL, "+
                            "full_paid_date DATETIME NULL, "+
                            "user_name VARCHAR(45) NOT NULL, "+
                            "PRIMARY KEY (batch_ID), "+
                            "UNIQUE INDEX batch_ID_UNIQUE (batch_ID ASC), "+
                            "INDEX fk_batch_user1_idx (user_name ASC), "+
                            "INDEX fk_batch_inventory1_idx (product_ID ASC), "+
                            "INDEX fk_batch_inventory2_idx (product_name ASC), "+
                            "INDEX fk_batch_sr_contact1_idx (sr_name ASC), "+
                            "CONSTRAINT fk_batch_user1 "+
                            "FOREIGN KEY (user_name) "+
                            "REFERENCES shop_management_database.user (name) "+
                            "ON DELETE NO ACTION "+
                            "ON UPDATE NO ACTION, "+
                            "CONSTRAINT fk_batch_inventory1 "+
                            "FOREIGN KEY (product_ID) "+
                            "REFERENCES shop_management_database.inventory (product_ID) "+
                            "ON DELETE NO ACTION "+
                            "ON UPDATE NO ACTION, "+
                            "CONSTRAINT fk_batch_inventory2 "+
                            "FOREIGN KEY (product_name) "+
                            "REFERENCES shop_management_database.inventory (product_name) "+
                            "ON DELETE NO ACTION "+
                            "ON UPDATE NO ACTION, "+
                            "CONSTRAINT fk_batch_sr_contact1 "+
                            "FOREIGN KEY (sr_name) "+
                            "REFERENCES shop_management_database.sr_contact (sr_name) "+
                            "ON DELETE NO ACTION "+
                            "ON UPDATE NO ACTION";
    }

    public String getCustomer() {
        setCustomerDetails();
        return customerDetails;
    }

    private void setCustomerDetails() {
        customerDetails = "customer_ID INT NOT NULL AUTO_INCREMENT, " +
                "customer_name VARCHAR(45) NOT NULL, " +
                "phone_number VARCHAR(45) NULL, " +
                "email VARCHAR(45) NULL, " +
                "address VARCHAR(150) NULL, " +
                "due_amount DOUBLE NULL, " +
                "date_enlisted DATETIME NULL, " +
                "user_name VARCHAR(45) NOT NULL, " +
                "PRIMARY KEY (customer_ID), " +
                "UNIQUE INDEX customer_ID_UNIQUE (customer_ID ASC), " +
                "INDEX fk_customer_user_idx (user_name ASC), " +
                "CONSTRAINT fk_customer_user " +
                "FOREIGN KEY (user_name) " +
                "REFERENCES shop_management_database.user (name) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION";
    }

    public String getOrders() {
        setOrdersDetails();
        return ordersDetails;
    }

    private void setOrdersDetails() {
        ordersDetails = "order_ID INT NOT NULL AUTO_INCREMENT, " +
                "total_number_product INT NOT NULL, " +
                "sub_total DOUBLE NOT NULL, " +
                "total_discount DOUBLE NOT NULL, " +
                "grant_total DOUBLE NOT NULL, " +
                "cash_paid DOUBLE NOT NULL, " +
                "cash_back DOUBLE NOT NULL, " +
                "due DOUBLE NOT NULL, " +
                "order_date DATETIME NOT NULL, " +
                "user_name VARCHAR(45) NOT NULL, " +
                "customer_name VARCHAR(45) NULL, " +
                "PRIMARY KEY (order_ID), " +
                "UNIQUE INDEX order_ID_UNIQUE (order_ID ASC), " +
                "INDEX fk_orders_user1_idx (user_name ASC), " +
                "INDEX fk_orders_customer1_idx (customer_name ASC), " +
                "CONSTRAINT fk_orders_user1 " +
                "FOREIGN KEY (user_name) " +
                "REFERENCES shop_management_database.user (name) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION, " +
                "CONSTRAINT fk_orders_customer1 " +
                "FOREIGN KEY (customer_name) " +
                "REFERENCES shop_management_database.customer (customer_name) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION";
    }

    public String getInvoice() {
        setInvoiceDetails();
        return invoiceDetails;
    }

    private void setInvoiceDetails() {
        invoiceDetails = " order_ID INT NOT NULL, " +
                "product_ID INT NOT NULL, " +
                "batch_ID INT NOT NULL, " +
                "product_name VARCHAR(45) NOT NULL, " +
                "quantity DOUBLE NOT NULL, " +
                "unit_price DOUBLE NOT NULL, " +
                "total_price DOUBLE NOT NULL, " +
                "discount DOUBLE NOT NULL, " +
                "sell_price VARCHAR(45) NOT NULL, " +
                "date DATETIME NOT NULL, "+
                "user_name VARCHAR(45) NOT NULL, " +
                "PRIMARY KEY (order_ID, product_ID), " +
                "INDEX fk_invoice_inventory1_idx (product_ID ASC), " +
                "INDEX fk_invoice_batch1_idx (batch_ID ASC), " +
                "INDEX fk_invoice_inventory2_idx (product_name ASC), " +
                "CONSTRAINT fk_invoice_orders1 " +
                "FOREIGN KEY (order_ID) " +
                "REFERENCES shop_management_database.orders (order_ID) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION, " +
                "CONSTRAINT fk_invoice_inventory1 " +
                "FOREIGN KEY (product_ID) " +
                "REFERENCES shop_management_database.inventory (product_ID) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION, " +
                "CONSTRAINT fk_invoice_batch1 " +
                "FOREIGN KEY (batch_ID) " +
                "REFERENCES shop_management_database.batch (batch_ID) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION, " +
                "CONSTRAINT fk_invoice_inventory2 " +
                "FOREIGN KEY (product_name) " +
                "REFERENCES shop_management_database.inventory (product_name) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION";
    }

    public String getDebits() {
        setDebitsDetails();
        return debitsDetails;
    }

    private void setDebitsDetails() {
        debitsDetails = "debit_ID INT NOT NULL AUTO_INCREMENT, " +
                            "order_ID INT NOT NULL, " +
                            "customer_name VARCHAR(45) NULL, " +
                            "product_ID INT NOT NULL, " +
                            "product_name VARCHAR(45) NOT NULL, " +
                            "quantity DOUBLE NOT NULL, " +
                            "cash_returned DOUBLE NOT NULL, " +
                            "return_date DATETIME NOT NULL, " +
                            "user_name VARCHAR(45) NOT NULL, " +
                            "PRIMARY KEY (debit_ID), " +
                            "UNIQUE INDEX debit_ID_UNIQUE (debit_ID ASC), " +
                            "INDEX fk_debits_user1_idx (user_name ASC), " +
                            "INDEX fk_debits_customer1_idx (customer_name ASC), " +
                            "INDEX fk_debits_inventory1_idx (product_ID ASC), " +
                            "INDEX fk_debits_inventory2_idx (product_name ASC), " +
                            "CONSTRAINT fk_debits_user1 " +
                            "FOREIGN KEY (user_name) " +
                            "REFERENCES shop_management_database.user (name) " +
                            "ON DELETE NO ACTION " +
                            "ON UPDATE NO ACTION, " +
                            "CONSTRAINT fk_debits_customer1 " +
                            "FOREIGN KEY (customer_name) " +
                            "REFERENCES shop_management_database.customer (customer_name) " +
                            "ON DELETE NO ACTION " +
                            "ON UPDATE NO ACTION, " +
                            "CONSTRAINT fk_debits_inventory1 " +
                            "FOREIGN KEY (product_ID) " +
                            "REFERENCES shop_management_database.inventory (product_ID) " +
                            "ON DELETE NO ACTION " +
                            "ON UPDATE NO ACTION, " +
                            "CONSTRAINT fk_debits_inventory2 " +
                            "FOREIGN KEY (product_name) " +
                            "REFERENCES shop_management_database.inventory (product_name) " +
                            "ON DELETE NO ACTION " +
                            "ON UPDATE NO ACTION";
    }

    public String getDue() {
        setDueDetails();
        return dueDetails;
    }

    private void setDueDetails() {
        dueDetails = "due_ID INT NOT NULL AUTO_INCREMENT, " +
                        "due_date DATETIME NOT NULL, " +
                        "order_ID INT NOT NULL, " +
                        "customer_name VARCHAR(45) NOT NULL," +
                        "due_amount DOUBLE NOT NULL, " +
                        "order_amount DOUBLE NOT NULL, " +
                        "cash_received DOUBLE NOT NULL, " +
                        "cash_received_date DATETIME NOT NULL, " +
                        "user_name VARCHAR(45) NOT NULL, " +
                        "PRIMARY KEY (due_ID), " +
                        "UNIQUE INDEX due_ID_UNIQUE (due_ID ASC), " +
                        "INDEX fk_due_user1_idx (user_name ASC), " +
                        "INDEX fk_due_orders1_idx (order_ID ASC), " +
                        "INDEX fk_due_customer1_idx (customer_name ASC), " +
                        "CONSTRAINT fk_due_user1 " +
                        "FOREIGN KEY (user_name) " +
                        "REFERENCES shop_management_database.user (name) " +
                        "ON DELETE NO ACTION " +
                        "ON UPDATE NO ACTION, " +
                        "CONSTRAINT fk_due_orders1 " +
                        "FOREIGN KEY (order_ID) " +
                        "REFERENCES shop_management_database.orders (order_ID) " +
                        "ON DELETE NO ACTION " +
                        "ON UPDATE NO ACTION, " +
                        "CONSTRAINT fk_due_customer1 " +
                        "FOREIGN KEY (customer_name) " +
                        "REFERENCES shop_management_database.customer (customer_name) " +
                        "ON DELETE NO ACTION " +
                        "ON UPDATE NO ACTION";
    }

    public String getCompany_due() {
        setCompany_dueDetails();
        return company_dueDetails;
    }

    private void setCompany_dueDetails() {
        company_dueDetails = "due_ID INT NOT NULL AUTO_INCREMENT, " +
                                "due_date DATETIME NOT NULL, " +
                                "company_name VARCHAR(45) NOT NULL, " +
                                "sr_name VARCHAR(45) NOT NULL, " +
                                "due_amount DOUBLE NOT NULL, " +
                                "cash_paid DOUBLE NOT NULL, " +
                                "cash_payment_date DATETIME NULL, " +
                                "user_name VARCHAR(45) NOT NULL, " +
                                "PRIMARY KEY (due_ID), " +
                                "UNIQUE INDEX due_ID_UNIQUE (due_ID ASC), " +
                                "INDEX fk_company_due_user1_idx (user_name ASC), " +
                                "INDEX fk_company_due_company1_idx (company_name ASC), " +
                                "CONSTRAINT fk_company_due_user1 " +
                                "FOREIGN KEY (user_name) " +
                                "REFERENCES shop_management_database.user (name) " +
                                "ON DELETE NO ACTION " +
                                "ON UPDATE NO ACTION, " +
                                "CONSTRAINT fk_company_due_company1 " +
                                "FOREIGN KEY (company_name) " +
                                "REFERENCES shop_management_database.company (company_name) " +
                                "ON DELETE NO ACTION " +
                                "ON UPDATE NO ACTION";
    }

    private String getBasic(){
        setBasicDetails();
        return basicDetails;
    }
    private void setBasicDetails(){
        basicDetails="shop_name varchar(40) not null,"+
                "owner_name varchar(40),"+
                "address varchar(80),"+
                "contact varchar(40),"+
                "picture_location varchar(150),"+
                "PRIMARY KEY (shop_name)";
    }

}
