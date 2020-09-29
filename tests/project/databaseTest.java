package project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class databaseTest {

    database db;

    @Before
    public void setUp() throws Exception{
        db=new database("jdbc:mysql://localhost:3311", "root", "root");
    }

    @Test
    public void testLoginAllowed() {
        System.out.println(" testLoginAllowed() Checking");
        assertEquals(true, db.loginAllowed("ADON", "01676542521"));
    }

    @Test
    public void testCheckAccess() {
        assertEquals(true, db.checkAccess("ADON", "inventory_access"));
    }

    @Test
    public void testUserTableGetBackgroundColor() {
        assertEquals("#ffffff", db.userTableGetBackgroundColor("ADON"));
    }


    @Test
    public void testNameExist() {
        assertEquals(true, db.nameExist("ADON"));
    }

    @Test
    public void testGetShopName() {
        System.out.println("Shop Name Wasn't Set.");
//        assertEquals("Shop Name Wasn't Set.", db.getShopName());
        assertEquals("", db.getShopName());
    }

    @Test
    public void testGetOwnerName() {
        assertEquals("", db.getOwnerName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("", db.getAddress());
    }

    @Test
    public void testGetContact() {
        assertEquals("", db.getContact());
    }

    @Test
    public void testProductNameExist() {
        assertEquals(true, db.productNameExist("Denim Black After Shave 100ml"));
    }

    @Test
    public void testCompanyNameExist() {
        assertEquals(true, db.companyNameExist("UNILEVER"));
    }

    @Test
    public void testUniqueProductAndCompanyName() {
        assertEquals(true, db.uniqueProductAndCompanyName("Oxy Deep Wash", "ROHTO"));
    }


    @Test
    public void testSrNameExist() {
        assertEquals(true, db.srNameExist("Abul Hasan"));
    }


    @Test
    public void testGetSrPhoneNumber() {
        assertEquals("01711224466", db.getSrPhoneNumber("Abul Hasan"));
    }


    @Test
    public void testGetNextOrderID() {
        assertEquals(2, db.getNextOrderID());
    }
}