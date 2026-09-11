package com.bloodnetwork.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bloodnetwork.dto.DashboardStatsDto;
import com.bloodnetwork.entity.AuditLog;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.RoleType;
import com.bloodnetwork.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto.AdminStats> getStats() {
        return ResponseEntity.ok(adminService.getAdminStats());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) RoleType role) {
        return ResponseEntity.ok(adminService.getAllUsers(role));
    }

    @PutMapping("/users/{id}/verify")
    public ResponseEntity<User> verifyUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.verifyUser(id));
    }

    @PutMapping("/users/{id}/toggle-status")
    public ResponseEntity<User> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.toggleUserStatus(id));
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        return ResponseEntity.ok(adminService.getRecentAuditLogs());
    }
}
