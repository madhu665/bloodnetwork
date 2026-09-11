package com.bloodnetwork.dto;

import java.time.LocalDateTime;

import com.bloodnetwork.entity.enums.NotificationType;

public class NotificationDto {

    public static class Response {
        private Long id;
        private String title;
        private String message;
        private NotificationType type;
        private Long relatedRequestId;
        private Boolean isRead;
        private LocalDateTime createdAt;

        public Response() {}

        public Response(Long id, String title, String message, NotificationType type,
                        Long relatedRequestId, Boolean isRead, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.message = message;
            this.type = type;
            this.relatedRequestId = relatedRequestId;
            this.isRead = isRead;
            this.createdAt = createdAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public NotificationType getType() { return type; }
        public void setType(NotificationType type) { this.type = type; }
        public Long getRelatedRequestId() { return relatedRequestId; }
        public void setRelatedRequestId(Long relatedRequestId) { this.relatedRequestId = relatedRequestId; }
        public Boolean getIsRead() { return isRead; }
        public void setIsRead(Boolean isRead) { this.isRead = isRead; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public static ResponseBuilder builder() { return new ResponseBuilder(); }

        public static class ResponseBuilder {
            private Long id;
            private String title;
            private String message;
            private NotificationType type;
            private Long relatedRequestId;
            private Boolean isRead;
            private LocalDateTime createdAt;

            public ResponseBuilder id(Long id) { this.id = id; return this; }
            public ResponseBuilder title(String title) { this.title = title; return this; }
            public ResponseBuilder message(String message) { this.message = message; return this; }
            public ResponseBuilder type(NotificationType type) { this.type = type; return this; }
            public ResponseBuilder relatedRequestId(Long relatedRequestId) { this.relatedRequestId = relatedRequestId; return this; }
            public ResponseBuilder isRead(Boolean isRead) { this.isRead = isRead; return this; }
            public ResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

            public Response build() {
                return new Response(id, title, message, type, relatedRequestId, isRead, createdAt);
            }
        }
    }
}
