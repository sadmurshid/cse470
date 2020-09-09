package project;

import java.sql.*;
import java.sql.Timestamp;
import java.util.Date;

public class database {

    private static String databaseName="shop_management_database";
    private static String [] [] tableNameAndParameter=null;
    private static Connection conn = null;
    private static Statement statement;
    private static String sql;
    private static ResultSet resultSet, rs, rs1;

    private Date dt;
    private java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String currentTime;

    private int tempInt;
    private String tempString;

    public database(String address, String user, String password)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(address, user, password);

            System.out.println("conn built");
            databaseExist(databaseName);
            statement = conn.createStatement();
            sql = "use " + databaseName;
            statement.executeUpdate(sql);
            createTableNameAndParameter();
            for (int i = 0; i < tableNameAndParameter.length; i++) {
                if (!tableExist(databaseName, tableNameAndParameter[i][0]))
                    createTable(databaseName, tableNameAndParameter[i][0], tableNameAndParameter[i][1]);
            }
            if(!nameExist("SYSTEM_ADMIN"))
                addUser("SYSTEM_ADMIN", "admin", "01676542521", "51/1 Gohailkandi",
                        "sadmurshid@gmail.com","#ffffff", 1, 1,1,1, 1,
                        "system admin");


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public database()
    {

    }

/*  Method Name: createTableNameAndParameter
    Parameter  : null
    Return     : void
    Purpose    : Initiate tableNameAndParameter String array which consists [tableName][parameters]
*/
    private static void createTableNameAndParameter()
    {
        databaseMandatoryTables dmt=new databaseMandatoryTables();
        String [] tablesName=dmt.tableNames(), tableDetails=dmt.tabelDetails();

        tableNameAndParameter=new String[tablesName.length][2];

        for(int i=0; i<tablesName.length; i++){
            tableNameAndParameter[i][0]=tablesName[i];
            tableNameAndParameter[i][1]=tableDetails[i];
        }



    }

/*  Method Name: databaseExist
    Parameter  : String databaseName
    Return     : void
    Purpose    : Check a database using the given name. If don't exist then call createDatabase to create one.
*/
    private static void databaseExist(String databaseName)
    {
        String getDatabaseName;
        ResultSet resultSet=null;
        boolean requiredDatabase=false;
        try {
            resultSet = conn.getMetaData().getCatalogs();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try{
            while(resultSet.next())
            {
                getDatabaseName=resultSet.getString(1);
                if(getDatabaseName.equals(databaseName))
                {
                    requiredDatabase=true;
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        if(requiredDatabase==false){
            createDatabase(databaseName);
        }
    }

/*  Method Name: createDatabase
    Parameter  : String database
    Purpose    : Create a database using the given name
*/
    private static void createDatabase(String database)
    {
        String sql="create database "+database+";";
        System.out.println(sql);
        Statement stmt;
        try{
            stmt=conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully");

        }
        catch(Exception e)
        {
            System.out.println("Database creation failed\n");
            System.out.println(e);
            e.printStackTrace();
        }
    }

/*  Method Name: tableExist
    Parameter  : String databaseName, String tableName
    Purpose    : Check a table whether exist by the given name
    Return     : Boolean true if table exist false if don't exist
*/
    private static boolean tableExist(String databaseName, String tableName) throws Exception
    {
        boolean isTableExist=false;
        try{
            DatabaseMetaData metaData=conn.getMetaData();
            resultSet=metaData.getTables( databaseName, null,"%", null);
            String str;
            while(resultSet.next())
            {
                str=resultSet.getString("TABLE_NAME");
                if(str.equals(tableName)){
                    isTableExist=true;
                    break;
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
//        System.out.println("Table Name : "+tableName+" Existence : "+isTableExist);
        return isTableExist;
    }

/*  Method Name: createTable
    Parameter  : String databaseName, String tableName, String nameParameter
                    for nameParameter the whole query inside "(" ")" must be provided
    Purpose    : Create a table by the given name
    Return     : Boolean true if table creation successful false if failed
*/
    private static boolean createTable(String databaseName, String tableName, String nameParameter)
    {
        boolean creationSuccessful=false;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="create table "+tableName+" ("+nameParameter+");";
            System.out.println(sql);
            statement.executeUpdate(sql);
            creationSuccessful=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return creationSuccessful;
    }

    protected boolean loginAllowed(String userName, String password)
    {
        boolean allowed=false;
        String tempUser=null, tempPassword=null;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select name, password from user order by user_ID;";
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                tempUser=resultSet.getString("name");
                tempPassword=resultSet.getString("password");
 //               System.out.println(tempUser+" "+tempPassword);
                if(tempUser.equals(userName) && tempPassword.equals(password))
                    return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allowed;
    }

/*  Method Name: checkAccess
    Parameter  : String userName, String accessTable <only inventoryAccess, batchAccess, orderAccess, debitsAccess, adminAccess allowed>
    Purpose    : Check the specified user has the access of the desired table or level.
    Return     : Boolean true if access allowed, false if not allowed or any exception created
*/
    protected boolean checkAccess(String userName, String accessTable){
        boolean boo=false;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT NAME, "+accessTable+" FROM USER WHERE NAME ='"+userName+"';";
            resultSet=statement.executeQuery(sql);
            resultSet.next();
            int access=resultSet.getInt(""+accessTable);
            if(access==1){
                return true;
            }else{
//                System.out.println("Access for "+accessTable+" = "+access);
                boo=false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return boo;
    }

    private String userTableGetUserName(String userID)
    {
        String userName=null;
        String ID=null;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select name, userID from user order by userID;";
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                userName=resultSet.getString("name");
                ID=resultSet.getString("user_ID");
                if(userID.equals(ID))
                {
                    return userName;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return userName;
    }

    private String userTableGetPassword(String userID)
    {
        String password=null;
        String ID=null;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select password, userID from user order by userID;";
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                password=resultSet.getString("password");
                ID=resultSet.getString("user_ID");
                if(userID.equals(ID))
                {
                    return password;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return password;
    }

    protected String userTableGetBackgroundColor(String userName)
    {
        String color=null;
        String tempName;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select backgroundColor, name from user order by name;";
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                color=resultSet.getString("backgroundColor");
                tempName=resultSet.getString("name");
                if(tempName.equals(userName))
                {
                    return color;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return color;
    }

    public boolean addUser(String name, String password, String phone_number, String address, String email
                                , String b_color, int inventory, int batch, int order, int debits, int admin, String userType){
//        boolean boo=false;
        try{
            sql="use "+databaseName;
//            System.out.println(sql);
            statement.executeUpdate(sql);
            sql="select max(user_ID) from user";
            rs = statement.executeQuery(sql);
            rs.next();
            int max=rs.getInt("max(user_ID)");
            if(!accessIntCorrect(inventory)||!accessIntCorrect(batch)||!accessIntCorrect(order)
                            ||!accessIntCorrect(debits)||!accessIntCorrect(admin)){
                return false;
            }
            sql="insert into user values ("+(max+1)+", '"+name+"', '"+password+"', '"+ phone_number+"', '"+address+"', '"
                    +email+"', '"+b_color+"', "+ inventory+", "+batch+", "+order+", "+debits+", "+admin+", '"+userType+"');";
//            System.out.println(sql);
            statement.executeUpdate(sql);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private boolean accessIntCorrect(int integer){
        boolean boo=false;
        try{
            if(integer==0||integer==1)
                boo= true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  boo;
    }


/*  Method Name: nameExist
    Parameter  : String Name
    Purpose    : Check names from the user table whether exist by the given name
    Return     : Boolean true if name exist false if don't exist
*/
    protected boolean nameExist(String name){
        boolean boo=false;
        try{
            String tempUser;
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select name from user order by user_ID;";
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                tempUser=resultSet.getString("name");
//                System.out.println(tempUser+" "+tempPassword);
                if(tempUser.equals(name))
                    return true;
            }

        }catch(NullPointerException npe){
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return boo;
    }

/*
    Method Name: getShopName
    Parameter  : null
    Purpose    : Return Shop name from basic table
    Return     : String shopName
*/
    protected String getShopName() {
        String shopName="";
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select shop_name from basic ;";
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()) {
                shopName = resultSet.getString("shop_name");
            }
            return shopName;

        }catch (SQLException sqlE){
            sqlE.printStackTrace();
            return "Shop Name Wasn't Set.";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return shopName;
    }

    protected String getOwnerName() {
        String ownerName="";
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select owner_name from basic ;";
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()) {
                ownerName = resultSet.getString("owner_name");
            }
            return ownerName;

        }catch (SQLException sqlE){
            sqlE.printStackTrace();
            return "Owner Name Wasn't Set.";
        }catch (Exception e){
            e.printStackTrace();
        }
        return ownerName;
    }

    protected String getAddress() {
        String address="";
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select address from basic ;";
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()) {
                address = resultSet.getString("address");
            }
            return address;

        }catch (SQLException sqlE){
            sqlE.printStackTrace();
            return "Address Wasn't Set.";
        }catch (Exception e){
            e.printStackTrace();
        }
        return address;
    }

    protected String getContact() {
        String contact="";
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="select contact from basic ;";
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()) {
                contact = resultSet.getString("contact");
            }
            return contact;

        }catch (SQLException sqlE){
            sqlE.printStackTrace();
            return "Contact Wasn't Set.";
        }catch (Exception e){
            e.printStackTrace();
        }
        return contact;
    }

    protected boolean setBasicInformation(String shopName, String ownerName, String address, String contact){
        boolean boo=false;
        try{
            sql="use "+databaseName;
            System.out.println(sql);
            statement.executeUpdate(sql);

            sql="TRUNCATE TABLE  basic";
            statement.executeUpdate(sql);

            sql="insert into basic values ( '"+shopName+"', '"+ownerName+"', '"+ address+"', '"+contact+"' , '');";
            System.out.println(sql);
            statement.executeUpdate(sql);


            return boo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return boo;
    }



    protected boolean addNewBatch(int productID, String productName, double buyPrice, double sellPrice, double maxDiscount,
                               double quantity, String buyDateTime, String srName, double cashPaid, double due,
                               String fullPaidDate, String userName){
        try{
            if(nameExist(userName) && checkAccess(userName, "batch_access")){
                sql="use "+databaseName;
                statement.executeUpdate(sql);
                sql="SELECT MAX(batch_ID) FROM batch";
                rs = statement.executeQuery(sql);
                rs.next();
                int max=rs.getInt("MAX(batch_ID)");
                sql="INSERT INTO batch VALUES ( "+(max+1)+", "+productID+", '"+productName+"', "+buyPrice+", "+sellPrice+", "+
                        maxDiscount+", "+quantity+", '"+buyDateTime+"', '"+srName+"', "+cashPaid+", "+due+", '"+fullPaidDate+"'," +
                        " '"+userName+"');";
                System.out.println(sql);
                statement.executeUpdate(sql);
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


        return false;
    }

    protected boolean addNewCompany( String companyName, String companyPhoneNumber, String companyEmail, String companyAddress,
                                  String userName){
        try{
            if(nameExist(userName) ){
                sql="use "+databaseName;
                statement.executeUpdate(sql);
                sql="SELECT MAX(company_ID) FROM company";
                rs = statement.executeQuery(sql);
                rs.next();
                int max=rs.getInt("MAX(company_ID)");

                dt=new Date();
                currentTime=sdf.format(dt);

                sql="INSERT INTO company VALUES ( "+(max+1)+", '"+companyName+"', '"+companyPhoneNumber+"', '"+companyEmail+
                        "', '"+companyAddress+"', '"+currentTime+"', '"+userName+"');";
                System.out.println(sql);
                statement.executeUpdate(sql);
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


        return false;
    }

/*
    Method Name: productNameExist
    Parameter  : String productName
    Purpose    : check whether the inventory contain same product name or not
    Return     : boolean true [if contain] or false [if don't contain]
*/
    protected boolean productNameExist(String productName){
        boolean boo=false;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT product_name FROM inventory WHERE product_name= '"+productName+"' ;";
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occur.");
            return boo;
        }
    }

/*
        Method Name: companyNameExist
        Parameter  : String companyName
        Purpose    : check whether the company table contain same company name or not
        Return     : boolean true [if contain] or false [if don't contain]
    */
    protected boolean companyNameExist(String companyName){
        boolean boo=false;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT company_name FROM company WHERE company_name= '"+companyName+"' ;";
            resultSet=statement.executeQuery(sql);

            if(resultSet.next()){
                return true;
            }else{
                return false;
            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occur.");
            return boo;
        }
    }

    /*
    Method Name: uniqueProductAndCompanyName
    Parameter  : String productName, String companyName
    Purpose    : check whether the inventory contain same product name & company name or not
    Return     : boolean true [if contain] or false [if don't contain]
*/
    protected boolean uniqueProductAndCompanyName(String productName, String companyName){
        boolean boo=false;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT product_name, company_name FROM inventory WHERE product_name= '"+productName+"' AND company_name='"+companyName+"';";
//            System.out.println(sql);
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occur.");
            return boo;
        }
    }

    /*
    Method Name: companyNamesSortedByString
    Parameter  : String companyName (str)
    Purpose    : check whether the given company name exists and if common found return those name in a String array
    Return     : String array containing the common names like provided
*/
    protected String[] companyNamesSortedByString(String str){
        String [] companyNames;
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT company_name FROM  company WHERE  company_name LIKE '%"+str+"%'";
            resultSet=statement.executeQuery(sql);
            resultSet.last();
            int size=resultSet.getRow();
            resultSet.beforeFirst();
            if(size>0) {
                companyNames = new String[size];
                int i=0;
                while(resultSet.next()){
                    
                    companyNames[i]=resultSet.getString("company_name");
                    i++;
                }
            }
            else
                companyNames=new String[0];
            return companyNames;
        }catch (Exception e){
            e.printStackTrace();
            String [] companyName={"null","null"};
            return companyName;
        }
    }

    /*
    Method Name: companyNames
    Parameter  : N/A
    Purpose    : check whether the given company name exists and if common found return those name in a String array
    Return     : String array containing the company names
*/
    protected String[] companyNames(){
        String [] companyName;
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT company_name FROM  company;";
            resultSet=statement.executeQuery(sql);
            resultSet.last();
            int length=resultSet.getRow();
            resultSet.beforeFirst();
            if(length>0) {
                companyName= new String[length];
                int i=0;
                while(resultSet.next()){
                    companyName[i]=resultSet.getString("company_name");
                    i++;
                }
            }
            else
                companyName=new String[0];
            return companyName;
        }catch (Exception e){
            e.printStackTrace();
            String [] companyNames={"null","null"};
            return companyNames;
        }
    }

    /*
        Method Name: srNameExist
        Parameter  : String companyName
        Purpose    : check whether the company table contain same company name or not
        Return     : boolean true [if contain] or false [if don't contain]
    */
    protected boolean srNameExist(String srName){
        boolean boo=false;
        try{
            sql="use "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT sr_name FROM sr_contact WHERE sr_name= '"+srName+"' ;";
            resultSet=statement.executeQuery(sql);

            if(resultSet.next()){
//                System.out.println(resultSet.getString("sr_name"));
                return true;
            }else{
                return false;
            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occur.");
            return boo;
        }
    }

    protected boolean addNewSrContact(String srName, String companyName, String phoneNumber, String email, String address, String userName)
    {
        try{
            if(nameExist(userName) ){
                sql="use "+databaseName;
                statement.executeUpdate(sql);
                sql="SELECT MAX(sr_ID) FROM sr_contact;";
                rs = statement.executeQuery(sql);
                rs.next();

                dt=new Date();
                currentTime=sdf.format(dt);

                int max=rs.getInt("MAX(sr_ID)");
                sql="INSERT INTO sr_contact VALUES ( "+(max+1)+", '"+srName+"', '"+companyName+"', '"+phoneNumber+"', '"+email+
                        "', '"+address+"', '"+currentTime+"','"+userName+"');";
                statement.executeUpdate(sql);
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


        return false;
    }

    /*
    Method Name: srNamesSortedByString
    Parameter  : String srName (str)
    Purpose    : check whether the given sr name exists and if common found return those name in a String array
    Return     : String array containing the common names like provided
*/
    protected String[] srNamesSortedByString(String str){
        String [] srNames;
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT sr_name FROM  sr_contact WHERE  sr_name LIKE '%"+str+"%';";
            resultSet=statement.executeQuery(sql);
            resultSet.last();
            int size=resultSet.getRow();
            resultSet.beforeFirst();
            if(size>0) {
                srNames = new String[size];
                int i=0;
                while(resultSet.next()){

                    srNames[i]=resultSet.getString("sr_name");
                    i++;
                }
            }
            else
                srNames=new String[0];
            return srNames;
        }catch (Exception e){
            e.printStackTrace();
            String [] srName={"null","null"};
            return srName;
        }
    }

    protected String getSrPhoneNumber(String srName){
        try{
            String phoneNumber;
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT sr_phone_number FROM  sr_contact WHERE  sr_name = '"+srName+"';";
            resultSet=statement.executeQuery(sql);

            if(resultSet.next()){
                phoneNumber=resultSet.getString("sr_phone_number");
            }else{
                phoneNumber="null";
            }
            return phoneNumber;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    protected boolean addNewInventory(String productName, String companyName, double quantity, double sellPrice, double buyPrice,
                                      double maxDiscount, String srName, String srPhoneNumber, double startingInventory,
                                      double minimumRequire, String category, String productDescription, String userName){
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT MAX(product_ID) FROM inventory;";
            rs = statement.executeQuery(sql);
            rs.next();
            int max=rs.getInt("MAX(product_ID)");

            dt=new Date();
            currentTime=sdf.format(dt);

            sql="INSERT INTO inventory VALUES ("+(max+1)+", '"+productName+"', '"+companyName+"', "+quantity+", "+sellPrice+", "+
                    buyPrice+", "+maxDiscount+", '"+srName+"', '"+srPhoneNumber+"', "+startingInventory+", "+minimumRequire+
                    ", '"+category+"', '"+productDescription+"', '"+currentTime+"', '"+userName+"');";
            statement.executeUpdate(sql);

            tempInt=max+1;
            tempString=currentTime;

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    protected boolean addNewProductAndBatch(String productName, String companyName, double quantity, double sellPrice, double buyPrice,
                                            double maxDiscount, String srName, String srPhoneNumber, double startingInventory,
                                            double minimumRequire, String category, String productDescription, String userName){
        try{
            if(addNewInventory(productName, companyName, quantity, sellPrice, buyPrice, maxDiscount, srName, srPhoneNumber,
                    startingInventory, minimumRequire, category, productDescription, userName)){
                double cashPaid=startingInventory*buyPrice;
                if(addNewBatch(tempInt, productName, buyPrice, sellPrice, maxDiscount, startingInventory, currentTime,srName,cashPaid,
                        0, currentTime, userName)){
                    return true;
                }else{
                    return false;
                }
            }else
                return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    protected String[] getProductNamesSortedByString(String productName){
        String [] productNames;
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT product_name FROM  inventory WHERE  product_name LIKE '%"+productName+"%';";
            resultSet=statement.executeQuery(sql);
            resultSet.last();
            int size=resultSet.getRow();
            resultSet.beforeFirst();
            if(size>0) {
                productNames = new String[size];
                int i=0;
                while(resultSet.next()){

                    productNames[i]=resultSet.getString("product_name");
                    i++;
                }
            }
            else
                productNames=new String[0];
            return productNames;
        }catch (Exception e){
            e.printStackTrace();
            String [] name={"null","null"};
            return name;
        }
    }


    protected String[][] getProductAndCompanyName(String productName, String companyName){
        String [] [] names;
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            if(companyNameExist(companyName) && productNameExist(productName)){
                sql="select product_name, company_name from inventory where product_name='"+productName+"' and  company_name='"+
                        companyName+"';";
            }else if(companyNameExist(companyName)){
                sql="select product_name, company_name from inventory where product_name LIKE '%"+productName+"%' and " +
                        " company_name='"+ companyName+"';";
            }else if(productNameExist(productName)){
                sql="select product_name, company_name from inventory where product_name='"+productName+"' and  company_name LIKE '%"+
                        companyName+"%';";
            }else{
                sql="select product_name, company_name from inventory where product_name LIKE '%"+productName+"%' and" +
                        "  company_name LIKE '%"+ companyName+"%';";
            }

            resultSet=statement.executeQuery(sql);
            resultSet.last();
            int size=resultSet.getRow();
            resultSet.beforeFirst();
            if(size>0){
                names=new String[size][2];
                int i=0;
                while(resultSet.next()){
                    names[i][0]=resultSet.getString("product_name");
                    names[i][1]=resultSet.getString("company_name");
//                    System.out.println(names[i][0]+" "+names[i][1]);
                    i++;
                }
                return names;
            }else{
                names=new String[0][0];
                return names;
            }


        }catch (Exception e){
            e.printStackTrace();
            names= new String[][]{{null, null}, {null, null}};
            return names;
        }
    }



    protected String[][] productDetails(String productName, String companyName){
        try{
            String [] [] details=new String[1][4];

            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="select sell_price, quantity, max_discount, category, product_description from inventory where product_name='"+
                    productName+"' and company_name='"+companyName+"';";

//            System.out.println(sql);

            resultSet=statement.executeQuery(sql);

            resultSet.next();
            details[0][0]=resultSet.getString("sell_price");
            details[0][1]=resultSet.getString("quantity");
            details[0][2]=resultSet.getString("max_discount");
            details[0][3]="Stock : "+details[0][1]+"   Category : "+resultSet.getString("category");
            tempString=resultSet.getString("product_description");
            if(!tempString.equals("")){
                details[0][3]=details[0][3]+" Product details : "+resultSet.getString("product_description");
            }

            return details;
        }catch (Exception e){
            e.printStackTrace();
            String [][]names= new String[][]{{null, null}, {null, null}};
            return names;
        }
    }

/*
    private boolean updateProductPrice(int productId, String productName ){
        try{

            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            double inventoryBuyPrice;


            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
*/

    protected int getNextOrderID(){
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);

            sql="SELECT MAX(order_ID) from orders;";
            resultSet=statement.executeQuery(sql);

            int orderID;
            if(resultSet.next()) {
                orderID = resultSet.getInt("MAX(order_ID)");
                System.out.println(orderID);
                return (orderID+1);
            }
            return -1;
        }catch (NullPointerException nP){
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    protected boolean addNewOrder(int id, int totalProduct, double subTotal, double totalDiscount, double grantTotal,
                                  double cashPaid, double cashBack, double due, String date, String user, String customer){
        try{

            int orderId=getNextOrderID();
            if (orderId == id) {
                sql="USE "+databaseName;
                statement.executeUpdate(sql);

                sql="INSERT INTO orders VALUES ("+id+", "+totalProduct+", "+subTotal+", "+totalDiscount+", "+grantTotal
                        +", "+cashPaid+", "+cashBack+", "+due+", '"+date+"', '"+user+"', '"+customer+"');";
//                System.out.println(sql);
                statement.executeUpdate(sql);
                return true;

            }else return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private int  getProductID(String productName, String companyName){
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);

            sql="SELECT product_ID FROM inventory WHERE product_name='"+productName+"' AND company_name='"+companyName+"';";
            resultSet=statement.executeQuery(sql);
            int productID;
            if(resultSet.next()) {
                productID = resultSet.getInt("product_ID");
                return productID;
            }
            return -1;
        }catch (NullPointerException nP){
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    private int [] getBatchIDsUsingProductID(int productID){
        try{
            sql="USE "+databaseName;
            statement.executeUpdate(sql);
            sql="SELECT batch_ID FROM batch WHERE product_ID="+productID+";";
            resultSet=statement.executeQuery(sql);
            resultSet.last();
            int size=resultSet.getRow();
            resultSet.beforeFirst();
            int [] batchID;
            if(size>0) {
                batchID = new int[size];
                int i=0;
                while(resultSet.next()){

                    batchID[i]=Integer.parseInt(resultSet.getString("batch_ID"));
                    i++;
                }
            }
            else {
                batchID = new int[1];
                batchID[0]=-1;
            }
            return batchID;
        }catch (NullPointerException nP){
            System.out.println("getBatchIDsUsingProductID : null point exception");
            int [] batchID=new int [1];
            batchID[0]=-1;
            return batchID;
        }catch (Exception e){
            e.printStackTrace();
            int [] batchID=new int [1];
            batchID[0]=-1;
            return batchID;
        }
    }

    private boolean reduceProductFromBatch(int productID, int [] batchID, double quantity){
        try{
            double tempQuantity;
            boolean boo=false;
            sql="USE "+databaseName;
            statement.executeUpdate(sql);

            for(int i=0; i<batchID.length; i++){
                sql="SELECT quantity FROM batch WHERE product_ID="+productID+" AND batch_ID="+batchID[i]+";";
                System.out.println("reduceProductFromBatch : "+sql);
                resultSet=statement.executeQuery(sql);
                resultSet.next();
                tempQuantity=Integer.parseInt(resultSet.getString("quantity"));
                System.out.println("Product ID : "+productID+" Quantity : "+tempQuantity);
                if(quantity>=tempQuantity){
                    quantity=quantity-tempQuantity;
                    sql="UPDATE batch SET quantity=0.0 WHERE product_ID="+productID+" AND batch_ID="+batchID[i]+";";
                    System.out.println(sql);
                    statement.executeUpdate(sql);
                    boo=true;
                }else if(quantity<tempQuantity){
                    tempQuantity=tempQuantity-quantity;
                    quantity=0;
                    sql="UPDATE batch SET quantity="+tempQuantity+" WHERE product_ID="+productID+" AND batch_ID="+batchID[i]+";";
                    System.out.println(sql);
                    statement.executeUpdate(sql);
                    boo=true;
                }
            }
            if(boo && quantity==0)
                return true;
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean reduceProductFromInventory(int productID, double quantity){
        try{
            double tempQuantity;
            sql="USE "+databaseName;
            statement.executeUpdate(sql);

            sql="SELECT quantity FROM inventory WHERE product_ID="+productID+";";
            resultSet=statement.executeQuery(sql);
            resultSet.next();

            tempQuantity=Integer.parseInt(resultSet.getString("quantity"));
//            System.out.println("Product ID : "+productID+" Quantity : "+tempQuantity+" reduce : "+ quantity);
            if(tempQuantity>quantity){
                tempQuantity=tempQuantity-quantity;
                sql="UPDATE inventory SET quantity="+tempQuantity+" WHERE product_ID="+productID+";";
//                System.out.println("reduceProductFromInventory : "+sql);
                statement.executeUpdate(sql);
                return true;
            }else return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    protected boolean addNewInvoice(int orderID, String productName, String companyName, double quantity, double unitPrice,
                                  double totalPrice, double discount, String sellPrice, String date, String user){
        try{
            int productID=getProductID(productName, companyName);
            if(productID!=-1) {
                int[] batchID = getBatchIDsUsingProductID(productID);
                if(reduceProductFromBatch(productID, batchID, quantity) && reduceProductFromInventory(productID, quantity)){
                    sql="USE "+databaseName;
                    statement.executeUpdate(sql);

                    sql="INSERT INTO invoice VALUES ("+orderID+", "+productID+", "+batchID[batchID.length-1]+", '"+productName+
                            "', "+quantity+ ", "+unitPrice+", "+totalPrice+", "+discount+", '"+sellPrice+"', '"+date+"', '"+user+"');";
//                    System.out.println("add new invoice : "+sql);
                    statement.executeUpdate(sql);
                    return true;
                }else return false;
            }else return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}