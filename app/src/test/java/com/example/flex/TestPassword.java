package com.example.flex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.flex.model.UserAccount;

import org.junit.Test;

public class TestPassword {
    @Test
    public void testContainsAtSymbol() {
        String pw_1 = "test";
        assertFalse(UserAccount.checkPasswordForCapitalLetters(pw_1));

        String pw_2 = "test1";
        assertFalse(UserAccount.checkPasswordForCapitalLetters(pw_2));

        String pw_3 = "Test";
        assertFalse(UserAccount.checkPasswordForCapitalLetters(pw_3));

        String pw_4 = "Test1";
        assertTrue(UserAccount.checkPasswordForCapitalLetters(pw_4));

        String pw_5 = "Test1Cheese";
        assertTrue(UserAccount.checkPasswordForCapitalLetters(pw_5));

        String pw_6 = "test1Food";
        assertTrue(UserAccount.checkPasswordForCapitalLetters(pw_6));


    }

}
