package com.work.service.service;

import com.work.service.entity.Report;

import java.util.List;

public interface ReportService {
    void markReport(Integer userId, Integer postId, String reason);

    List<Report> getAllReports(Integer userId);

    void reviewReport(Integer userId, Integer reportId, Integer approval);

    void deletePost(Integer userId, Integer postId);
}
