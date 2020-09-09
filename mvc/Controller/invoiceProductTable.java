package project;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class invoiceProductTable {

    private SimpleIntegerProperty no;
    private SimpleStringProperty productName;
    private SimpleStringProperty companyName;
    private SimpleDoubleProperty unitPrice;
    private SimpleDoubleProperty quantity;
    private SimpleDoubleProperty amount;
    private SimpleDoubleProperty discount;
    private Button remove;

    private InvoiceViewController invoice;

    protected invoiceProductTable(int no, String productName, String companyName, double unitPrice, double quantity,
                                  double amount, double discount, Button remove, InvoiceViewController invoice){

        this.no=new SimpleIntegerProperty(no);
        this.productName=new SimpleStringProperty(productName);
        this.companyName=new SimpleStringProperty(companyName);
        this.unitPrice=new SimpleDoubleProperty(unitPrice);
        this.quantity=new SimpleDoubleProperty(quantity);
        this.amount=new SimpleDoubleProperty(amount);
        this.discount=new SimpleDoubleProperty(discount);

        this.remove=remove;

        this.invoice=invoice;

        remove.setOnAction(event -> {
            invoice.removeProductFromTableView(no);
        });
    }



    protected int getNo() {
        return no.get();
    }

    protected String getProductName() {
        return productName.get();
    }

    protected SimpleStringProperty productNameProperty() {
        return productName;
    }

    protected void setProductName(String productName) {
        this.productName.set(productName);
    }

    protected String getCompanyName() {
        return companyName.get();
    }

    protected SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    protected void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    protected double getUnitPrice() {
        return unitPrice.get();
    }

    protected SimpleDoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    protected void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    protected double getQuantity() {
        return quantity.get();
    }

    protected SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    protected void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    protected double getAmount() {
        return amount.get();
    }

    protected SimpleDoubleProperty amountProperty() {
        return amount;
    }

    protected void setAmount(double amount) {
        this.amount.set(amount);
    }

    protected double getDiscount() {
        return discount.get();
    }

    protected SimpleDoubleProperty discountProperty() {
        return discount;
    }

    protected void setDiscount(double discount) {
        this.discount.set(discount);
    }
    protected Button getRemove() {
        return remove;
    }

    protected void setRemove(Button remove) {
        this.remove = remove;
    }

    protected SimpleIntegerProperty noProperty() {
        return no;
    }

    protected void setNo(int no) {
        this.no.set(no);
    }

    protected void setDisable(){
        remove.setDisable(true);
    }
}
