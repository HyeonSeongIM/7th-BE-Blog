package com.leets.blog.repository;

import com.leets.blog.entity.Report;
import com.leets.blog.entity.enums.ReportTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporter_IdAndTargetTypeAndTargetId(Long reporterId, ReportTargetType targetType, Long targetId);
}