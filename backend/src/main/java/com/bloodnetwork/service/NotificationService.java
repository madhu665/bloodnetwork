package com.bloodnetwork.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloodnetwork.dto.NotificationDto;
import com.bloodnetwork.entity.Notification;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.NotificationType;
import com.bloodnetwork.exception.ResourceNotFoundException;
import com.bloodnetwork.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(User recipient, String title, String message, NotificationType type, Long relatedRequestId) {
        Notification notification = Notification.builder()
                .recipient(recipient)
                .title(title)
                .message(message)
                .type(type)
                .relatedRequestId(relatedRequestId)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    public List<NotificationDto.Response> getUserNotifications(Long userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByRecipientIdAndIsReadFalse(userId);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));

        if (!notification.getRecipient().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized to update this notification");
        }

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId);
    }

    private NotificationDto.Response mapToDto(Notification n) {
        return NotificationDto.Response.builder()
                .id(n.getId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .relatedRequestId(n.getRelatedRequestId())
                .isRead(n.getIsRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
