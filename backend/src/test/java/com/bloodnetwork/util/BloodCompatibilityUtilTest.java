package com.bloodnetwork.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.bloodnetwork.entity.enums.BloodGroup;

public class BloodCompatibilityUtilTest {

    @Test
    public void testUniversalDonorONegativeCanDonateToAll() {
        List<BloodGroup> recipients = BloodCompatibilityUtil.getCompatibleRecipientGroups(BloodGroup.O_NEG);
        assertEquals(8, recipients.size(), "O- red blood cells can be donated to all 8 blood groups");

        for (BloodGroup bg : BloodGroup.values()) {
            assertTrue(BloodCompatibilityUtil.isCompatible(BloodGroup.O_NEG, bg));
        }
    }

    @Test
    public void testUniversalRecipientABPositiveCanReceiveFromAll() {
        List<BloodGroup> donors = BloodCompatibilityUtil.getCompatibleDonorGroups(BloodGroup.AB_POS);
        assertEquals(8, donors.size(), "AB+ can receive red blood cells from all 8 blood groups");

        for (BloodGroup bg : BloodGroup.values()) {
            assertTrue(BloodCompatibilityUtil.isCompatible(bg, BloodGroup.AB_POS));
        }
    }

    @Test
    public void testONegativeRecipientCanOnlyReceiveFromONegative() {
        List<BloodGroup> donors = BloodCompatibilityUtil.getCompatibleDonorGroups(BloodGroup.O_NEG);
        assertEquals(1, donors.size());
        assertEquals(BloodGroup.O_NEG, donors.get(0));

        assertFalse(BloodCompatibilityUtil.isCompatible(BloodGroup.O_POS, BloodGroup.O_NEG));
        assertFalse(BloodCompatibilityUtil.isCompatible(BloodGroup.A_POS, BloodGroup.O_NEG));
    }

    @Test
    public void testAPositiveCompatibility() {
        // A+ can receive from A+, A-, O+, O-
        List<BloodGroup> donors = BloodCompatibilityUtil.getCompatibleDonorGroups(BloodGroup.A_POS);
        assertTrue(donors.contains(BloodGroup.A_POS));
        assertTrue(donors.contains(BloodGroup.A_NEG));
        assertTrue(donors.contains(BloodGroup.O_POS));
        assertTrue(donors.contains(BloodGroup.O_NEG));
        assertFalse(donors.contains(BloodGroup.B_POS));

        // A+ can donate only to A+ and AB+
        List<BloodGroup> recipients = BloodCompatibilityUtil.getCompatibleRecipientGroups(BloodGroup.A_POS);
        assertEquals(2, recipients.size());
        assertTrue(recipients.contains(BloodGroup.A_POS));
        assertTrue(recipients.contains(BloodGroup.AB_POS));
    }
}
