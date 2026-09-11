package com.bloodnetwork.dto;

import java.util.Map;

public class DashboardStatsDto {

    public static class AdminStats {
        private long totalUsers;
        private long totalDonors;
        private long totalHospitals;
        private long totalBloodBanks;
        private long activeRequests;
        private long emergencyRequests;
        private long totalUnitsAvailable;
        private long totalDonations;
        private long fulfilledRequests;
        private Map<String, Long> bloodGroupInventory;

        public AdminStats() {}

        public AdminStats(long totalUsers, long totalDonors, long totalHospitals, long totalBloodBanks,
                          long activeRequests, long emergencyRequests, long totalUnitsAvailable,
                          long totalDonations, long fulfilledRequests, Map<String, Long> bloodGroupInventory) {
            this.totalUsers = totalUsers;
            this.totalDonors = totalDonors;
            this.totalHospitals = totalHospitals;
            this.totalBloodBanks = totalBloodBanks;
            this.activeRequests = activeRequests;
            this.emergencyRequests = emergencyRequests;
            this.totalUnitsAvailable = totalUnitsAvailable;
            this.totalDonations = totalDonations;
            this.fulfilledRequests = fulfilledRequests;
            this.bloodGroupInventory = bloodGroupInventory;
        }

        public long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
        public long getTotalDonors() { return totalDonors; }
        public void setTotalDonors(long totalDonors) { this.totalDonors = totalDonors; }
        public long getTotalHospitals() { return totalHospitals; }
        public void setTotalHospitals(long totalHospitals) { this.totalHospitals = totalHospitals; }
        public long getTotalBloodBanks() { return totalBloodBanks; }
        public void setTotalBloodBanks(long totalBloodBanks) { this.totalBloodBanks = totalBloodBanks; }
        public long getActiveRequests() { return activeRequests; }
        public void setActiveRequests(long activeRequests) { this.activeRequests = activeRequests; }
        public long getEmergencyRequests() { return emergencyRequests; }
        public void setEmergencyRequests(long emergencyRequests) { this.emergencyRequests = emergencyRequests; }
        public long getTotalUnitsAvailable() { return totalUnitsAvailable; }
        public void setTotalUnitsAvailable(long totalUnitsAvailable) { this.totalUnitsAvailable = totalUnitsAvailable; }
        public long getTotalDonations() { return totalDonations; }
        public void setTotalDonations(long totalDonations) { this.totalDonations = totalDonations; }
        public long getFulfilledRequests() { return fulfilledRequests; }
        public void setFulfilledRequests(long fulfilledRequests) { this.fulfilledRequests = fulfilledRequests; }
        public Map<String, Long> getBloodGroupInventory() { return bloodGroupInventory; }
        public void setBloodGroupInventory(Map<String, Long> bloodGroupInventory) { this.bloodGroupInventory = bloodGroupInventory; }

        public static AdminStatsBuilder builder() { return new AdminStatsBuilder(); }

        public static class AdminStatsBuilder {
            private long totalUsers;
            private long totalDonors;
            private long totalHospitals;
            private long totalBloodBanks;
            private long activeRequests;
            private long emergencyRequests;
            private long totalUnitsAvailable;
            private long totalDonations;
            private long fulfilledRequests;
            private Map<String, Long> bloodGroupInventory;

            public AdminStatsBuilder totalUsers(long totalUsers) { this.totalUsers = totalUsers; return this; }
            public AdminStatsBuilder totalDonors(long totalDonors) { this.totalDonors = totalDonors; return this; }
            public AdminStatsBuilder totalHospitals(long totalHospitals) { this.totalHospitals = totalHospitals; return this; }
            public AdminStatsBuilder totalBloodBanks(long totalBloodBanks) { this.totalBloodBanks = totalBloodBanks; return this; }
            public AdminStatsBuilder activeRequests(long activeRequests) { this.activeRequests = activeRequests; return this; }
            public AdminStatsBuilder emergencyRequests(long emergencyRequests) { this.emergencyRequests = emergencyRequests; return this; }
            public AdminStatsBuilder totalUnitsAvailable(long totalUnitsAvailable) { this.totalUnitsAvailable = totalUnitsAvailable; return this; }
            public AdminStatsBuilder totalDonations(long totalDonations) { this.totalDonations = totalDonations; return this; }
            public AdminStatsBuilder fulfilledRequests(long fulfilledRequests) { this.fulfilledRequests = fulfilledRequests; return this; }
            public AdminStatsBuilder bloodGroupInventory(Map<String, Long> bloodGroupInventory) { this.bloodGroupInventory = bloodGroupInventory; return this; }

            public AdminStats build() {
                return new AdminStats(totalUsers, totalDonors, totalHospitals, totalBloodBanks, activeRequests, emergencyRequests, totalUnitsAvailable, totalDonations, fulfilledRequests, bloodGroupInventory);
            }
        }
    }

    public static class DonorStats {
        private String bloodGroup;
        private boolean eligible;
        private String lastDonationDate;
        private String nextEligibleDate;
        private int totalDonations;
        private int livesSaved;
        private boolean isAvailable;
        private long unreadNotifications;

        public DonorStats() {}

        public DonorStats(String bloodGroup, boolean eligible, String lastDonationDate, String nextEligibleDate,
                          int totalDonations, int livesSaved, boolean isAvailable, long unreadNotifications) {
            this.bloodGroup = bloodGroup;
            this.eligible = eligible;
            this.lastDonationDate = lastDonationDate;
            this.nextEligibleDate = nextEligibleDate;
            this.totalDonations = totalDonations;
            this.livesSaved = livesSaved;
            this.isAvailable = isAvailable;
            this.unreadNotifications = unreadNotifications;
        }

        public String getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
        public boolean isEligible() { return eligible; }
        public void setEligible(boolean eligible) { this.eligible = eligible; }
        public String getLastDonationDate() { return lastDonationDate; }
        public void setLastDonationDate(String lastDonationDate) { this.lastDonationDate = lastDonationDate; }
        public String getNextEligibleDate() { return nextEligibleDate; }
        public void setNextEligibleDate(String nextEligibleDate) { this.nextEligibleDate = nextEligibleDate; }
        public int getTotalDonations() { return totalDonations; }
        public void setTotalDonations(int totalDonations) { this.totalDonations = totalDonations; }
        public int getLivesSaved() { return livesSaved; }
        public void setLivesSaved(int livesSaved) { this.livesSaved = livesSaved; }
        public boolean isIsAvailable() { return isAvailable; }
        public void setIsAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
        public long getUnreadNotifications() { return unreadNotifications; }
        public void setUnreadNotifications(long unreadNotifications) { this.unreadNotifications = unreadNotifications; }

        public static DonorStatsBuilder builder() { return new DonorStatsBuilder(); }

        public static class DonorStatsBuilder {
            private String bloodGroup;
            private boolean eligible;
            private String lastDonationDate;
            private String nextEligibleDate;
            private int totalDonations;
            private int livesSaved;
            private boolean isAvailable;
            private long unreadNotifications;

            public DonorStatsBuilder bloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; return this; }
            public DonorStatsBuilder eligible(boolean eligible) { this.eligible = eligible; return this; }
            public DonorStatsBuilder lastDonationDate(String lastDonationDate) { this.lastDonationDate = lastDonationDate; return this; }
            public DonorStatsBuilder nextEligibleDate(String nextEligibleDate) { this.nextEligibleDate = nextEligibleDate; return this; }
            public DonorStatsBuilder totalDonations(int totalDonations) { this.totalDonations = totalDonations; return this; }
            public DonorStatsBuilder livesSaved(int livesSaved) { this.livesSaved = livesSaved; return this; }
            public DonorStatsBuilder isAvailable(boolean isAvailable) { this.isAvailable = isAvailable; return this; }
            public DonorStatsBuilder unreadNotifications(long unreadNotifications) { this.unreadNotifications = unreadNotifications; return this; }

            public DonorStats build() {
                return new DonorStats(bloodGroup, eligible, lastDonationDate, nextEligibleDate, totalDonations, livesSaved, isAvailable, unreadNotifications);
            }
        }
    }
}
