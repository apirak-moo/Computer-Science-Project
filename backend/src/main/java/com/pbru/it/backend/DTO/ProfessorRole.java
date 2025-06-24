package com.pbru.it.backend.DTO;

public enum ProfessorRole {
    
        DEAN("คณบดี"),
        ASSOCIATE_DEAN("รองคณบดี"),
        HEAD_OF_DEPARTMENT("ประธานสาขาวิชา"),
        FACULTY_MEMBER("อาจารย์ประจำสาขาวิชา");

        private final String displayName;

        ProfessorRole(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
