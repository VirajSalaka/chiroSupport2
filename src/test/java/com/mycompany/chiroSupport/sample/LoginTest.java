package com.mycompany.chiroSupport.sample;

/**
 * Created by Salaka on 5/16/2017.
 */
public class LoginTest {

    
    TextField firstname = find("#firstname");
        firstname.setText("bennet");
    verifyThat("#firstname", hasText("bennet"));

    TextField lastname = find("#lastname");
        lastname.setText("schulz");
    verifyThat("#lastname", hasText("schulz"));

    Button search = find("#search");
    assertFalse(search.disableProperty().get());
}
