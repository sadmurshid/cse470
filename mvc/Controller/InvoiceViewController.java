package project;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;

public class InvoiceViewController {

    @FXML
    AnchorPane invoiceAnchorPane=new AnchorPane();

    @FXML
    Label customerAddressLabel, customerContactLabel, invoiceNumberLabel, dateLabel, unitPriceLabel, amountLabel,
            productDetailsLabel, subTotalLabel, grantTotalLabel, cashBackLabel, dueLabel, warningLabel, userLabel;

    @FXML
    TextField customerNameTextfield, productNameTextField, companyNameTextField, quantityTextField, productDiscountTextField, discountTextField,
                cashPaidTextField;

    @FXML
    Button nextButton, invoiceSearchAddButton, checkOutButton, cancelButton;
    @FXML
    JFXButton printButton;

    @FXML
    protected TableView <invoiceProductTable> invoiceTableView;

    @FXML
    private TableColumn<invoiceProductTable, String> noColumn, productNameColumn, companyNameColumn, unitPriceColumn,
                quantityColumn, amountColumn,discountColumn;

    @FXML
    private TableColumn<invoiceProductTable, Button> removeColumn;

    ObservableList<invoiceProductTable> productTables;

    private Date dt;
    private java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String currentTime;

    private double  subTotal, grandTotal, cashPaid, cashBack, due;
    private int totalTableRow=0, invoiceNumber;
    private double unitPrice, amount, stock, maxDiscount, discount;

    protected database db;
    private String username;

    private void setDefault() {
        customerAddressLabel.setText("CUSTOMER ADDRESS");
        customerContactLabel.setText("CUSTOMER CONTACT INFORMATION");

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = format.format( new Date()   );


        dateLabel.setText(dateString);
        unitPriceLabel.setText("Unit Price");
        amountLabel.setText("Amount");
        productDetailsLabel.setText("");
        subTotalLabel.setText("0");
        grantTotalLabel.setText("0");
        cashBackLabel.setText("0");
        dueLabel.setText("0");
        warningLabel.setText("");

        customerNameTextfield.setPromptText("CUSTOMER NAME");
        productNameTextField.setPromptText("PRODUCT NAME");
        companyNameTextField.setPromptText("COMPANY NAME");
        quantityTextField.setPromptText("QUANTITY");
        productDiscountTextField.setPromptText("Discount");
        discountTextField.setPromptText("DISCOUNT");
        cashPaidTextField.setPromptText("CASH PAID");

        nextButton=new Button();
        invoiceSearchAddButton=new Button();
        checkOutButton=new Button();
        cancelButton=new Button();
        printButton=new JFXButton();

        setDefaultTableView();
    }

    private void setDefaultTableView(){
        try{

            /*
            TableColumn no=new TableColumn("NO");
            TableColumn productName=new TableColumn("PRODUCT NAME");
            TableColumn companyName=new TableColumn("COMPANY NAME");
            TableColumn unitPrice=new TableColumn("UNIT PRICE");
            TableColumn quantity=new TableColumn("QUANTITY");
            TableColumn amount=new TableColumn("AMOUNT");
            TableColumn discount=new TableColumn("DISCOUNT");
            TableColumn remove=new TableColumn("REMOVE");
            invoiceTableView.getColumns().addAll(no, productName, companyName, unitPrice, quantity, amount, discount, remove);
*/
            productTables= FXCollections.observableArrayList();


            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
            removeColumn.setCellValueFactory(new PropertyValueFactory<>("remove"));
            
            invoiceTableView.setItems(productTables);

            setTotalTableRow();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        try{
            setDefault();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void setDatabase(database dt){
        try{
            db=dt;
            invoiceNumber=db.getNextOrderID();
            invoiceNumberLabel.setText(""+invoiceNumber);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void setUserName(String username){
        try{
            this.username=username;
            userLabel.setText("USER NAME : "+username);
//            System.out.println(username);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected database getDatabase(){
        return db;
    }

    @FXML
    protected boolean getProductNames(){
        try {
            String companyName=companyNameTextField.getText();
            String productName=productNameTextField.getText();
            if(companyName.equals("")) {
                String [] productNames=db.getProductNamesSortedByString(productName);
                TextFields.bindAutoCompletion(productNameTextField, productNames);
                return true;
            }else{
                String[][] names=db.getProductAndCompanyName(productName, companyName);
                String [] productNames=new String[names.length];
                for(int i=0; i<productNames.length; i++){
                    productNames[i]=names[i][0];
                }
                TextFields.bindAutoCompletion(productNameTextField, productNames);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected boolean getCompanyNames(){
        try {
            String companyName=companyNameTextField.getText();
            String productName=productNameTextField.getText();
            if(productName.equals("")) {
                String [] companyNames=db.companyNamesSortedByString(companyName);
                TextFields.bindAutoCompletion(companyNameTextField, companyNames);
                return true;
            }else{
                String[][] names=db.getProductAndCompanyName(productName, companyName);
                String [] companyNames=new String[names.length];
                for(int i=0; i<companyNames.length; i++){
                    companyNames[i]=names[i][1];
                }
                TextFields.bindAutoCompletion(companyNameTextField, companyNames);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    protected boolean getProductDetails(){
        try{
            String productName=productNameTextField.getText();
            String companyName=companyNameTextField.getText();


            if(db.uniqueProductAndCompanyName(productName, companyName)){
                String [] [] productDetails=db.productDetails(productName, companyName);

//                System.out.println("Product Name : "+productName+" Company Name : "+companyName);

                unitPrice=Double.parseDouble(productDetails[0][0]);
                unitPriceLabel.setText(""+unitPrice);
                stock=Double.parseDouble(productDetails[0][1]);
                maxDiscount=Double.parseDouble(productDetails[0][2]);
                productDetailsLabel.setText(productDetails[0][3]);
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected boolean setAmountLabel(){
        try{
            String quantityString=quantityTextField.getText();
            String discountString=productDiscountTextField.getText();
            double quantity;
            if(!quantityString.equals("") && discountString.equals("")) {
                quantity = Double.parseDouble(quantityString);
                if (stock >= quantity) {
                    amount = unitPrice * quantity;
                    amountLabel.setText("" + amount);
                    warningLabel.setText("");
                    return true;
                } else {
                    amountLabel.setText("");
                    warningLabel.setText("You do not have " + quantity + " product in stock");
                    warningLabel.setTextFill(Color.RED);
                    return false;
                }
            }else if(!quantityString.equals("") && !discountString.equals("")){
                quantity = Double.parseDouble(quantityString);
                double discount=Double.parseDouble(discountString);
                double maxDiscount=quantity*this.maxDiscount;
//                System.out.println(" enter ");
                if(stock >= quantity && discount<=maxDiscount){
                    amount = unitPrice * quantity - discount;
                    amountLabel.setText("" + amount);
                    warningLabel.setText("");
                    return true;
                } else if(warningLabel.getText().equals("")){
                    amountLabel.setText("");
                    warningLabel.setText("You can not give " + discount + " discount on this product");
                    warningLabel.setTextFill(Color.RED);
                    return false;
                }else
                    return false;
            }
            else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected boolean addButtonClicked(){
        try{
            if(amount>0){
                String productName=productNameTextField.getText();
                String companyName=companyNameTextField.getText();
                double quantity=Double.parseDouble(quantityTextField.getText());
                double discount;
                if(!productDiscountTextField.getText().equals(""))
                    discount=Double.parseDouble(productDiscountTextField.getText());
                else
                    discount=0.0;

                addProductInTableView(productName, companyName, unitPrice, quantity, amount, discount);
                setSearchDefault();
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void setSearchDefault(){
        unitPriceLabel.setText("Unit Price");
        amountLabel.setText("Amount");
        productDetailsLabel.setText("");

        productNameTextField.clear();
        companyNameTextField.clear();
        quantityTextField.clear();
        productDiscountTextField.clear();

        productNameTextField.setPromptText("PRODUCT NAME");
        companyNameTextField.setPromptText("COMPANY NAME");
        quantityTextField.setPromptText("QUANTITY");
        productDiscountTextField.setPromptText("Discount");
    }

    private void setTotalTableRow(){
        try{
//            productTables
            int i = 0;
//            invoiceProductTable currentRow;
            for(ListIterator<invoiceProductTable> iterator=productTables.listIterator(); iterator.hasNext();
                iterator.next()){
                i++;
            }
            totalTableRow=i;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addProductInTableView(String productName, String companyName, double unitPrice, double quantity,
                                       double amount, double discount){
        try{
            productTables.add(new invoiceProductTable(totalTableRow+1, productName, companyName, unitPrice, quantity,
                    amount, discount, new Button("REMOVE"), this));
            totalTableRow++;
            invoiceTableView.setItems(productTables);
            setSubTotalLabel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void printButtonClicked(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected boolean removeProductFromTableView(int no){
        try{
            Predicate<invoiceProductTable> removeItem= p-> p.getNo()==no;
            productTables.removeIf(removeItem);
            setTotalTableRow();

            int i = 1;
            for(Iterator<invoiceProductTable> iterator=productTables.iterator(); iterator.hasNext(); ) {

                invoiceProductTable edit=iterator.next();
                edit.setNo(i);
                i++;
            }

            invoiceTableView.setItems(productTables);
            setSubTotalLabel();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    private boolean setSubTotalLabel(){
        try{

            int i = 1;
            double amount=0;
//            invoiceProductTable currentRow;
            for(Iterator<invoiceProductTable> iterator=productTables.iterator(); iterator.hasNext(); ) {

                invoiceProductTable edit=iterator.next();
                amount=amount+edit.getAmount();
                i++;
            }
            subTotal=amount;
            subTotalLabel.setText(""+amount);
            setGrandTotalLabel();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private boolean setGrandTotalLabel(){
        try{
            String subTotalStr=subTotalLabel.getText();
            double subTotal=0;
            if(!subTotalStr.equals(""))
                subTotal=Double.parseDouble(subTotalStr);

            String discountString=discountTextField.getText();
            double discount=0;
            if(!discountString.equals(""))
                discount=Double.parseDouble(discountString);
            double grandTotal=0;
            String discountGreaterThanWarning="Discount can't be greater than sub total.";

            if(this.subTotal!=subTotal){
                setSubTotalLabel();
                if(subTotal!=this.subTotal){
                    return false;
                }
            }
            if(subTotal>discount){
                grandTotal=subTotal-discount;
                this.grandTotal=grandTotal;
                this.discount=discount;
                grantTotalLabel.setText(""+grandTotal);
                String warning=warningLabel.getText();
                if(warning.equals(discountGreaterThanWarning)) {
                    warningLabel.setText("");
                }
                setCashBackLabel();
                return true;
            }else{
                warningLabel.setText(discountGreaterThanWarning);
                warningLabel.setTextFill(Color.RED);
                grantTotalLabel.setText("");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    private boolean setCashBackLabel(){
        try{
            String cashPaidString=cashPaidTextField.getText();
            double cashPaid=0;
            if(!cashPaidString.equals("")){
                cashPaid=Double.parseDouble(cashPaidString);
            }else {
                cashBackLabel.setText(""+0.0);
                dueLabel.setText(""+0.0);
                return false;
            }

            String grandTotalString=grantTotalLabel.getText();
            double grandTotal=0;
            if(!grandTotalString.equals("")){
                grandTotal=Double.parseDouble(grandTotalString);
            }

            double cashBack=0, due=0;

            if(this.grandTotal!=grandTotal){
                setGrandTotalLabel();
                if(this.grandTotal!=grandTotal){
                    return false;
                }
            }
            if(grandTotal<cashPaid){
                cashBack=cashPaid-grandTotal;
                this.cashBack=cashBack;
                this.cashPaid=cashPaid;
                this.due=0;
                due=0;
                cashBackLabel.setText(""+cashBack);
                dueLabel.setText(""+due);
                return true;
            }else if(grandTotal>cashPaid){
                due=grandTotal-cashPaid;
                this.due=due;
                this.cashBack=0;
                this.cashPaid=cashPaid;
                cashBack=0;
                cashBackLabel.setText(""+cashBack);
                dueLabel.setText(""+due);
                return true;
            }else{
                this.cashBack=0;
                cashBack=0;
                this.due=0;
                due=0;
                this.cashPaid=cashPaid;
                cashBackLabel.setText(""+cashBack);
                dueLabel.setText(""+due);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean setDisable(){
        try {
            customerNameTextfield.setDisable(true);
            productNameTextField.setDisable(true);
            companyNameTextField.setDisable(true);
            quantityTextField.setDisable(true);
            discountTextField.setDisable(true);
            cashPaidTextField.setDisable(true);
            nextButton.setDisable(true);
            invoiceSearchAddButton.setDisable(true);

            for(Iterator<invoiceProductTable> iterator=productTables.iterator(); iterator.hasNext(); ) {

                invoiceProductTable edit=iterator.next();
                edit.setDisable();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private boolean checkOutButton(){
        try{
            setTotalTableRow();

            dt=new Date();
            currentTime=sdf.format(dt);

            discount=Double.parseDouble(discountTextField.getText());

            if(db.addNewOrder(invoiceNumber, totalTableRow, subTotal, discount, grandTotal, cashPaid, cashBack, due,
                     currentTime, username, "")){

                int i=1;
                String productName, companyName, sellPrice, coustomerName="";
                int orderID=invoiceNumber;
                double quantity, unitPrice, totalPrice, discount;

                for(Iterator<invoiceProductTable> iterator=productTables.iterator(); iterator.hasNext(); ) {

                    invoiceProductTable edit=iterator.next();
                    productName=edit.getProductName();
                    companyName=edit.getCompanyName();
                    sellPrice=""+edit.getAmount();
                    quantity=edit.getQuantity();
                    unitPrice=edit.getUnitPrice();
                    totalPrice=quantity*unitPrice;
                    discount=edit.getDiscount();
                    if(db.addNewInvoice(orderID, productName, companyName, quantity, unitPrice, totalPrice, discount, sellPrice
                            , currentTime, username)){
//                        System.out.println("adding "+productName+" Successful");
                        i++;
                    }

                }
                return true;
            }

            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
