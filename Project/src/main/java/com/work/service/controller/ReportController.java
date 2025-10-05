package com.work.service.controller;

import com.work.service.Result.AjaxResult;
import com.work.service.dto.request.ReviewRequest;
import com.work.service.dto.request.MarkRequest;
import com.work.service.entity.Report;
import com.work.service.service.ReportService;
import com.work.service.util.CurrentUserId;
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
    public AjaxResult<String> markReport(@Valid @RequestBody MarkRequest request,@CurrentUserId Integer userId){
        reportService.markReport(userId, request.getPostId(),request.getReason());
        return AjaxResult.success("标记成功");
    }

    @GetMapping("/report")
    public AjaxResult<List<Report>> getAllReports(@CurrentUserId Integer userId){
        List<Report> reports =reportService.getAllReports(userId);
        return AjaxResult.success(reports);
    }

    @DeleteMapping("/report")
    public AjaxResult<String>  reviewReport(@Valid @RequestBody ReviewRequest request,@CurrentUserId Integer userId){
        reportService.reviewReport(userId,request.getReportId(),request.getApproval());
        return AjaxResult.success("审核成功");
    }

    @DeleteMapping("/delete")
    public AjaxResult<String> deletePost(@Valid @RequestParam Integer postId,@CurrentUserId Integer userId){
        reportService.deletePost(userId,postId);
        return AjaxResult.success("删除成功");
    }
}
