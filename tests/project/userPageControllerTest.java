package project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class userPageControllerTest {

    userPageController up;
    database db;
    Button b;

    @Before
    public void setUp() throws Exception {
        up=new userPageController();
        db=Mockito.mock(database.class);
        b=new Button();
    }

    @Test
    public void setUserName() {
        when(db.userTableGetBackgroundColor("ADON")).thenReturn("#ffffff");
        when(new Button()).thenReturn(b);
        assertEquals(true, up.setUserName("ADON"));
    }
}