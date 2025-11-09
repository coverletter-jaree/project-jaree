package org.jaree.api.application.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplicationStatus {
    NOT_STARTED("시작 전"),
    WORK_IN_PROGRESS("작성 중"),
    SUBMITTED("제출 완료"),
    APPROVED("합격"),
    REJECTED("불합격");

    private final String displayText;

    @JsonValue
    public String getDisplayText() {
        return displayText;
    }
}
