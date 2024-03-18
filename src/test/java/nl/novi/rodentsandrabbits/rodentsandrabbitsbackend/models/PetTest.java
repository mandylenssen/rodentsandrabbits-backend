package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PetTest {

    @Test
    @DisplayName("Should keep petname as Fluffy when creating a new pet with name Fluffy.")
    public void testPetNameRetrieval() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        User owner = new User();

        Pet pet = new Pet(1L, "Fluffy", birthday, "rat", "male", "Loves to play with toys and enjoys being handled", "", "pellets, nuts, grains, vegetables", owner);
        String result = pet.getName();
        assertEquals("Fluffy", result);
    }

    @Test
    @DisplayName("Should correctly retrieve the owner's username.")
    public void testUsernameRetrieval() throws ParseException {
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("user@test.com");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");

        Pet pet = new Pet(1L, "Fluffy", birthday, "rat", "male", "Loves to play with toys and enjoys being handled", "", "pellets, nuts, grains, vegetables", mockUser);

        String result = pet.getOwner().getUsername();
        assertEquals("user@test.com", result);
    }

}