package org.jaree.api.resume.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResumeRelationshipType {
    CONTAINS_EDUCATION,         // 학력
    CONTAINS_WORK_EXPERIENCE,   // 경력
    CONTAINS_PROJECTS,          // 프로젝트
    CONTAINS_OTHER_EXPERIENCE,  // 대외활동
    CONTAINS_LECTURES,          // 수업
    CONTAINS_CERTIFICATES;      // 자격증
}
