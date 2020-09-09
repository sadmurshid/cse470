package project;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;

public class newProductGridPaneController {

    @FXML GridPane newProductGridPane=new GridPane();
    @FXML TextField productNameTextField=new TextField(), quantityTextField=new TextField(), sellPriceTextField=new TextField(),
            buyPriceTextField=new TextField(), maxDiscountTextField=new TextField(), minimumRequireTextField=new TextField(),
            srNameTextField=new TextField(), srPhoneNumberTextfield=new TextField(), categoryTextField=new TextField(),
            productDescriptionTextField=new TextField(), companyNameTextField=new TextField();
    @FXML Label warningLabel=new Label("* items are mandatory"), productNameWarningLabel=new Label(""),
                companyNameWarningLabel=new Label(""), quantityWarningLabel=new Label(""),
                sellPriceWarningLabel=new Label(), buyPriceWarningLabel=new Label(), maxDiscountWarningLabel=new Label();
    @FXML Button saveButton=new Button();

    private String productname, comppanyName, srName, phoneNumber, category, description;
    private double quantity, buyPrice, sellPrice, maxDiscount, minRequire;

    protected database db;
    private String username;

    public newProductGridPaneController(){
        warningLabel.setText("* items are mandatory");
        productNameWarningLabel.setText("");
        companyNameWarningLabel.setText("");
        quantityWarningLabel.setText("");

    }

    protected newProductGridPaneController(database dt){
        db=dt;

    }

    protected void setDefault(){
        warningLabel.setText("* items are mandatory");

        productNameWarningLabel.setText("");
        companyNameWarningLabel.setText("");
        quantityWarningLabel.setText("");
        sellPriceWarningLabel.setText("");
        buyPriceWarningLabel.setText("");
        maxDiscountWarningLabel.setText("");

        productNameTextField.setText("");
        companyNameTextField.setText("");
        quantityTextField.setText("");
        buyPriceTextField.setText("");
        sellPriceTextField.setText("");
        maxDiscountTextField.setText("");
        minimumRequireTextField.setText("");
        srNameTextField.setText("");
        srPhoneNumberTextfield.setText("");
        categoryTextField.setText("");
        productDescriptionTextField.setText("");
    }

    @FXML
    public void initialize()
    {
        db=new database();
    }

    protected void setDatabase(database dt){
        try{
            db=dt;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void setUserName(String username){
        try{
            this.username=username;
            System.out.println(username);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected database getDatabase(){
        return db;
    }

    @FXML
    protected boolean checkProductNameExistence(){
        try{
            System.out.println(productNameTextField.getText());
            if(db.productNameExist(productNameTextField.getText())){
                productNameWarningLabel.setText("Name Not Allowed");
                productNameWarningLabel.setTextFill(Color.RED);
                return false;
            }else{
                productNameWarningLabel.setText("NAME ALLOWED");
                productNameWarningLabel.setTextFill(Color.GREEN);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    protected boolean checkUniqueProductAndCompanyName(){
        try{
            String companyName=companyNameTextField.getText();
            String productName=productNameTextField.getText();
            System.out.println(companyName);
            if(db.companyNameExist(companyName)){
                if(!db.uniqueProductAndCompanyName(productName, companyName)){
                    companyNameWarningLabel.setText("NAME ALLOWED");
                    companyNameWarningLabel.setTextFill(Color.GREEN);
                    productNameWarningLabel.setText("NAME ALLOWED");
                    productNameWarningLabel.setTextFill(Color.GREEN);
                    this.comppanyName=companyName;
                    this.productname=productName;
                    return true;
                }else{
                    companyNameWarningLabel.setText("Duplicate Product Name");
                    companyNameWarningLabel.setTextFill(Color.RED);
                    return false;
                }
            }else
                return false;



        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void checkCompanyName(){
        try{
            String companyName=companyNameTextField.getText();
            if(db.companyNameExist(companyName)==false){
                companyNameWarningLabel.setText("Company name doesn't exist");
                companyNameWarningLabel.setTextFill(Color.RED);
            }else{
                companyNameWarningLabel.setText("Available");
                companyNameWarningLabel.setTextFill(Color.RED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void getCompanyNames(){
        try{
            String companyName=companyNameTextField.getText();
            String [] companyNames=db.companyNamesSortedByString(companyName);
//            System.out.println(companyNames[0]);
            TextFields.bindAutoCompletion(companyNameTextField, companyNames);
            checkUniqueProductAndCompanyName();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected boolean checkQuantity(){
        try{
            String quan=quantityTextField.getText();
            if(!quan.equals(null)) {
                double quantity = Double.parseDouble(quan);
                if (quantity < 0) {
                    quantityWarningLabel.setText("Quantity can't be less then 0.");
                    quantityWarningLabel.setTextFill(Color.RED);
                    return false;
                } else {
                    quantityWarningLabel.setText("");
                    this.quantity = quantity;
                    return true;
                }
            }else{
                this.quantity=0;
                quantityWarningLabel.setText("");
                return true;
            }
        }catch (Exception e){
            quantityWarningLabel.setText("ERROR: Contact admin");
            quantityWarningLabel.setTextFill(Color.PURPLE);
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected boolean checkBuyPrice(){
        try{
            String price=buyPriceTextField.getText();
            double buyPrice=Double.parseDouble(price);
            if(buyPrice>0 && buyPrice<100000){
                buyPriceWarningLabel.setText("");
                this.buyPrice=buyPrice;
                return true;
            }else if(buyPrice<0){
                buyPriceWarningLabel.setText("Purchasing price can't be negative");
                buyPriceWarningLabel.setTextFill(Color.RED);
                return false;
            }else if(buyPrice>10000){
                buyPriceWarningLabel.setText("Purchasing price must be less than 99999");
                buyPriceWarningLabel.setTextFill(Color.RED);
                return false;
            }else{
                buyPriceWarningLabel.setText("Buy price not accepted");
                buyPriceWarningLabel.setTextFill(Color.RED);
                return false;
            }

        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
            buyPriceWarningLabel.setText("Please enter a number.");
            buyPriceWarningLabel.setTextFill(Color.RED);
            return false;
        }catch(Exception e){
            e.printStackTrace();
            sellPriceWarningLabel.setText("Please contact admin.");
            sellPriceWarningLabel.setTextFill(Color.RED);
            return false;
        }
    }

    @FXML
    protected boolean checkSellPrice(){
 //       boolean check=false;
        try{
            String price=sellPriceTextField.getText();
            double sellPrice=Double.parseDouble(price);
            price=buyPriceTextField.getText();
            double buyPrice=Double.parseDouble(price);

            if(sellPrice>buyPrice) {
                if (sellPrice > 0 && sellPrice < 10000) {
                    sellPriceWarningLabel.setText("");
                    this.sellPrice=sellPrice;
                    return true;
                } else if (sellPrice > 10000) {
                    sellPriceWarningLabel.setText("Highest sell price 9999");
                    sellPriceWarningLabel.setTextFill(Color.RED);
                    return false;
                } else if (sellPrice < 0) {
                    sellPriceWarningLabel.setText("Sell price can't be negative.");
                    sellPriceWarningLabel.setTextFill(Color.RED);
                    return false;
                } else {
                    sellPriceWarningLabel.setText("Sell prize not accepted.");
                    sellPriceWarningLabel.setTextFill(Color.RED);
                    return false;
                }
            }else{
                sellPriceWarningLabel.setText("Selling price can't be less than buy price");
                sellPriceWarningLabel.setTextFill(Color.RED);
                return false;
            }

        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
            sellPriceWarningLabel.setText("Please enter a number.");
            sellPriceWarningLabel.setTextFill(Color.RED);
            return false;
        } catch(Exception e){
            e.printStackTrace();
            sellPriceWarningLabel.setText("Please contact admin.");
            sellPriceWarningLabel.setTextFill(Color.RED);
            return false;
        }
    }

    @FXML
    protected boolean checkMaxDiscount(){
        try{
            String discount=maxDiscountTextField.getText();
            double maxDiscount=Double.parseDouble(discount);
            String sell=sellPriceTextField.getText();
            double sellPrice=Double.parseDouble(sell);
            if(sellPrice>maxDiscount){
                maxDiscountWarningLabel.setText("");
                this.maxDiscount=maxDiscount;
                return true;
            }else{
                maxDiscountWarningLabel.setText("Discount can't be more than sell price");
                maxDiscountWarningLabel.setTextFill(Color.RED);
                return false;
            }

        }catch (NumberFormatException nfe){
            maxDiscountWarningLabel.setText("Please enter a number");
            maxDiscountWarningLabel.setTextFill(Color.RED);
            return false;
        }catch (Exception e){
            maxDiscountWarningLabel.setText("Please contact admin.");
            maxDiscountWarningLabel.setTextFill(Color.RED);
            return false;
        }
    }

    private boolean checkMinimumRequire(){
        try{
            String minimum=minimumRequireTextField.getText();
            if(minimum.equals("")) {
                return true;
            }else{
                double minimumRequire = Double.parseDouble(minimum);
                if(minimumRequire<0){
                    warningLabel.setText("Minimum require can't be negative");
                    warningLabel.setTextFill(Color.RED);
                    return false;
                }else {
                    this.minRequire=minimumRequire;
                    return true;
                }
            }

        }catch (NumberFormatException nfe){
            warningLabel.setText("Minimum number: Only number allowed");
            warningLabel.setTextFill(Color.RED);
            return false;
        }catch (Exception e){
            warningLabel.setText("Minimum number: Please contact admin.");
            warningLabel.setTextFill(Color.RED);
            return false;
        }
    }

    @FXML
    protected void getSrNames(){
        try{
            String srName=srNameTextField.getText();
            String [] srNames=db.srNamesSortedByString(srName);
            System.out.println(srNames[0]);
            TextFields.bindAutoCompletion(srNameTextField, srNames);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean setSrName(){
        try{
            String name=srNameTextField.getText();
            System.out.println(name);

            if(!name.equals("") && db.srNameExist(name)){
                this.srName=name;
                setPhoneNumber();
                return true;
            }else if(name.equals("")){
                srName="";
                phoneNumber="";
                return true;
            } else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void setPhoneNumber(){
        try{
            String srName=srNameTextField.getText();
            String phoneNumber=db.getSrPhoneNumber(srName);
            if(phoneNumber.equals("")){
                srPhoneNumberTextfield.setText("");
            }else{
                this.phoneNumber=phoneNumber;
                srPhoneNumberTextfield.setText(phoneNumber);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean setCategory(){
        try{
            String category=categoryTextField.getText();
            if(category.equals("")){
                this.category="";
                return true;
            }else if(category.length()>30){
                warningLabel.setText("Category max length 30 characters");
                warningLabel.setTextFill(Color.RED);
                return false;
            }else{
                this.category=category;
                if(warningLabel.getText().equals("Category max length 30 characters"))
                    warningLabel.setText("");
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean setProductDescription(){
        try{
            String description=productDescriptionTextField.getText();
            if(description.equals("")){
                this.description="";
                return true;
            }else if(description.length()>150){
                warningLabel.setText("Product description max length 150 characters");
                warningLabel.setTextFill(Color.RED);
                return false;
            }else{
                this.description=description;
                if(warningLabel.getText().equals("Product description max length 150 characters"))
                    warningLabel.setText("");
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void setSaveButton(){
        try{
//            System.out.println("Save button clicked");
            if(checkUniqueProductAndCompanyName() && checkQuantity() && checkBuyPrice() && checkSellPrice() && checkMaxDiscount()){
//                System.out.println("Mandatory satisfied");
                if(checkMinimumRequire() && setSrName() && setCategory() && setProductDescription()){
//                    System.out.println("no block error");
                    if(quantity==0){
                        if(db.addNewInventory(productname, comppanyName, quantity, sellPrice, buyPrice, maxDiscount,
                                srName, phoneNumber, quantity, minRequire, category, description, username)){
                            warningLabel.setText("Product saved in inventory successfully");
                            warningLabel.setTextFill(Color.GREEN);
                            Thread.sleep(1000);
                            setDefault();
                        }else{
                            warningLabel.setText("Couldn't save in database, contact admin");
                            warningLabel.setTextFill(Color.RED);
                        }
                    }else{
                        if(db.addNewProductAndBatch(productname, comppanyName, quantity, sellPrice, buyPrice, maxDiscount,
                                srName, phoneNumber, quantity, minRequire, category, description, username)){
                            warningLabel.setText("Product saved in inventory successfully");
                            warningLabel.setTextFill(Color.GREEN);
                            Thread.sleep(1000);
                            setDefault();
                        }else{
                            warningLabel.setText("Couldn't save in database, contact admin");
                            warningLabel.setTextFill(Color.RED);
                        }
                    }
                }
            }else{
                warningLabel.setText("One or more mandatory field has warning.");
                warningLabel.setTextFill(Color.RED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
