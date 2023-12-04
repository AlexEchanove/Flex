package com.example.flex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.flex.model.UserAccount;

import org.junit.Test;

public class TestEmail {
    @Test
    public void testContainsAtSymbol() {
        String email_1 = "test@email.com";
        assertTrue(UserAccount.checkEmailForAtSymbol(email_1));

        String email_2 = "testemail.com";
        assertFalse(UserAccount.checkEmailForAtSymbol(email_2));

        String email_3 = "a@email.com";
        assertTrue(UserAccount.checkEmailForAtSymbol(email_3));

        String email_4 = "a@email";
        assertFalse(UserAccount.checkEmailForAtSymbol(email_4));

        String email_5 = "email.";
        assertFalse(UserAccount.checkEmailForAtSymbol(email_5));

        String email_6 = "email.cm@";
        assertFalse(UserAccount.checkEmailForAtSymbol(email_6));

        String email_7 = "email.co";
        assertFalse(UserAccount.checkEmailForAtSymbol(email_7));
    }

}
