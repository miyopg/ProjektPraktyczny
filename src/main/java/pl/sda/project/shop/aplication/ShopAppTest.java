package pl.sda.project.shop.aplication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sda.project.shop.aplication.ShopApp.emailAdressValidation;
import static pl.sda.project.shop.aplication.ShopApp.phoneNumberValidation;



class ShopAppTest {

    @Test
    void emailAdressValidationShouldReturnTrue() {
        //given
        String email = "asd@asd.pl";
        //when
        boolean result = emailAdressValidation(email);
        //then
        assertTrue(result);
    }
    @Test
    void emailAdressValidationShouldReturnFalse() {
        //given
        String email = "asd@asd@asd.pl";
        //when
        boolean result = emailAdressValidation(email);
        //then
        assertFalse(result);
    }

    @Test
    void phoneNumberValidationShouldReturnTrue() {
        //given
        String phoneNumber = "123456789";
        //when
        boolean result = phoneNumberValidation(phoneNumber);
        //then
        assertTrue(result);
    }
    @Test
    void phoneNumberValidationShouldReturnFalse() {
        //given
        String phoneNumber = "1234789";
        //when
        boolean result = phoneNumberValidation(phoneNumber);
        //then
        assertFalse(result);
    }
    @Test
    void phoneNumberValidationShouldReturnFalse2() {
        //given
        String phoneNumber = "12347836549";
        //when
        boolean result = phoneNumberValidation(phoneNumber);
        //then
        assertFalse(result);
    }
}