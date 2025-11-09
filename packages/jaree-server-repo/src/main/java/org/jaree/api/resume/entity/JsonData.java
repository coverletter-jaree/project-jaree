package org.jaree.api.resume.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("User")
public class JsonData {
    @Id
    @GeneratedValue
    private Long id;

    private String s3Url;   // json 데이터 저장한 S3 url

    @LastModifiedDate
    private LocalDateTime updatedAt;    // json 데이터 수정한 마지막 수정일자

    @CreatedDate
    private LocalDateTime createdAt;    // json 데이터 생성일자

}
