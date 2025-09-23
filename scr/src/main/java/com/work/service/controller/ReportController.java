package com.work.service.controller;

import com.work.service.dto.request.ReviewRequest;
import com.work.service.dto.request.DeletePostRequest;
import com.work.service.dto.request.MarkRequest;
import com.work.service.entity.Report;
import com.work.service.dto.Result;
import com.work.service.service.ReportService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/admin")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;

    @PutMapping("/report")
    public Result<String> markReport(@Valid @RequestBody MarkRequest request){
        reportService.markReport(request.getUserId(), request.getPostId(),request.getReason());
        return Result.success("标记成功");
    }

    @GetMapping("/report")
    public Result<List<Report>> getAllReports(@RequestParam Integer userId){
        List<Report> reports =reportService.getAllReports(userId);
        return Result.success(reports);
    }

    @DeleteMapping("/report")
    public Result<String>  reviewReport(@Valid @RequestBody ReviewRequest request){
        reportService.reviewReport(request.getUserId(),request.getReportId(),request.getApproval());
        return Result.success("审核成功");
    }

    @DeleteMapping("/delete")
    public Result<String> deletePost(@Valid @RequestBody DeletePostRequest request){
        reportService.deletePost(request.getUserId(),request.getPostId());
        return Result.success("删除成功");
    }
}
