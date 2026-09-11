package com.bloodnetwork.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bloodnetwork.entity.enums.BloodGroup;

public class BloodCompatibilityUtil {

    /**
     * Returns the list of donor blood groups whose red blood cells are compatible
     * for transfusion to the given recipient blood group.
     */
    public static List<BloodGroup> getCompatibleDonorGroups(BloodGroup recipient) {
        if (recipient == null) {
            return Collections.emptyList();
        }
        return switch (recipient) {
            case O_NEG -> List.of(BloodGroup.O_NEG);
            case O_POS -> List.of(BloodGroup.O_POS, BloodGroup.O_NEG);
            case A_NEG -> List.of(BloodGroup.A_NEG, BloodGroup.O_NEG);
            case A_POS -> List.of(BloodGroup.A_POS, BloodGroup.A_NEG, BloodGroup.O_POS, BloodGroup.O_NEG);
            case B_NEG -> List.of(BloodGroup.B_NEG, BloodGroup.O_NEG);
            case B_POS -> List.of(BloodGroup.B_POS, BloodGroup.B_NEG, BloodGroup.O_POS, BloodGroup.O_NEG);
            case AB_NEG -> List.of(BloodGroup.AB_NEG, BloodGroup.A_NEG, BloodGroup.B_NEG, BloodGroup.O_NEG);
            case AB_POS -> Arrays.asList(BloodGroup.values());
        };
    }

    /**
     * Returns the list of recipient blood groups that can safely receive red blood cells
     * from the given donor blood group.
     */
    public static List<BloodGroup> getCompatibleRecipientGroups(BloodGroup donor) {
        if (donor == null) {
            return Collections.emptyList();
        }
        return switch (donor) {
            case O_NEG -> Arrays.asList(BloodGroup.values());
            case O_POS -> List.of(BloodGroup.O_POS, BloodGroup.A_POS, BloodGroup.B_POS, BloodGroup.AB_POS);
            case A_NEG -> List.of(BloodGroup.A_NEG, BloodGroup.A_POS, BloodGroup.AB_NEG, BloodGroup.AB_POS);
            case A_POS -> List.of(BloodGroup.A_POS, BloodGroup.AB_POS);
            case B_NEG -> List.of(BloodGroup.B_NEG, BloodGroup.B_POS, BloodGroup.AB_NEG, BloodGroup.AB_POS);
            case B_POS -> List.of(BloodGroup.B_POS, BloodGroup.AB_POS);
            case AB_NEG -> List.of(BloodGroup.AB_NEG, BloodGroup.AB_POS);
            case AB_POS -> List.of(BloodGroup.AB_POS);
        };
    }

    public static boolean isCompatible(BloodGroup donor, BloodGroup recipient) {
        if (donor == null || recipient == null) {
            return false;
        }
        return getCompatibleDonorGroups(recipient).contains(donor);
    }
}
