package org.jaree.api.company.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CompanyCategory {
    // 규모 기반
    MICRO("소기업"),
    SME("중소기업"),
    MID_SIZE("중견기업"),
    LARGE("대기업"),

    // 설립 형태 / 성격
    STARTUP("스타트업"),
    PRIVATE("사기업"),
    PUBLIC("공기업"),
    NGO("비영리 단체"),
    GOVERNMENT("공기업");

    private final String displayText;

    @JsonValue
    public String getDisplayText() {
        return displayText;
    }
}
