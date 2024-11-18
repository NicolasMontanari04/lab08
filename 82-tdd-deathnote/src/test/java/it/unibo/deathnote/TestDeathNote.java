package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static java.lang.Thread.sleep;

class TestDeathNote {
    private DeathNote deathNote;
    private long SIX_SECONDS = 100;
    private long DETAILS_TIME = 6000 + SIX_SECONDS;

    @BeforeEach
    void init(){
        deathNote = new DeathNoteImplementation();
    }

    @Test
    void testRuleZeroThrowsException() {
        try {
            deathNote.getRule(0);
            fail("Expected an IllegalArgumentException for rule number zero, but none was thrown.");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException, "Exception is not of type IllegalArgumentException");
            String message = e.getMessage();
            assertNotNull(message, "Exception message should not be null");
            assertFalse(message.isEmpty(), "Exception message should not be empty");
            assertFalse(message.isBlank(), "Exception message should not be blank");
            String expectedMessage = "Number zero or smaller than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void testRuleIsEmpty(){
        for(int i = 1; i <= DeathNote.RULES.size(); i++){
            String rule = deathNote.getRule(i);
            assertNotNull(rule, "Rule at position " + i + " should not be null");
            assertFalse(rule.isEmpty(), "Rule at position " + i + " should not be empty");
            assertFalse(rule.isBlank(), "Rule at position " + i + " should not be blank");
        }
    }

    @Test
    void testNameWritten(){
        assertFalse(deathNote.isNameWritten("Nicolas Montanari"));
        deathNote.writeName("Nicolas Montanari");
        assertTrue(deathNote.isNameWritten("Nicolas Montanari"));
        assertFalse(deathNote.isNameWritten("Mattia Mularoni"));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test 
    void testDeathCause() throws InterruptedException{
        try {
            deathNote.writeDeathCause("Karting accident");
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("cause is null", e.getMessage());
        }
        deathNote.writeName("Nicolas Montanari");
        assertTrue(deathNote.writeDeathCause("Heart attack"));
        assertEquals("Heart attack", deathNote.getDeathCause("Nicolas Montanari"));
        deathNote.writeName("Mattia Mularoni");
        assertTrue(deathNote.writeDeathCause("Karting accident"));
        
        assertEquals("Karting accident", deathNote.getDeathCause("Mattia Mularoni"));

        sleep(SIX_SECONDS);
        assertFalse(deathNote.writeDeathCause("wrote many tests before dying"));
        assertEquals("Karting accident", deathNote.getDeathCause("Mattia Mularoni"));
    }

    @Test
    void testDeathDetails() throws InterruptedException{
        try{
            deathNote.writeDetails("Killed by accident");
            fail("Expected IllegalStateException to be thrown");
        }catch(IllegalStateException e){
            assertEquals("Details is null", e.getMessage());
        }
        deathNote.writeName("Nicolas Montanari");
        assertEquals("", deathNote.getDeathDetails("Nicolas Montanari"));
        assertTrue(deathNote.writeDetails("ran for too long"));
        assertEquals("ran for too long", deathNote.getDeathDetails("Nicolas Montanari"));
        deathNote.writeName("Mattia Mularoni");
        assertTrue(deathNote.writeDetails("Karting accident"));
        
        assertEquals("Karting accident", deathNote.getDeathDetails("Mattia Mularoni"));

        sleep(DETAILS_TIME);
        assertFalse(deathNote.writeDetails("wrote many tests before dying"));
        assertEquals("Karting accident", deathNote.getDeathDetails("Mattia Mularoni"));
    }




}