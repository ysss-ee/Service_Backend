package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.constant.ExceptionEnum;
import com.work.service.entity.Post;
import com.work.service.entity.Report;
import com.work.service.entity.User;
import com.work.service.exception.ApiException;
import com.work.service.mapper.PostMapper;
import com.work.service.mapper.ReportMapper;
import com.work.service.mapper.UserMapper;
import com.work.service.service.ReportService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    @Resource
    private MessageServiceImpl messageService;

    private void checkUserType3(Integer userId) {
        User user = userMapper.selectById(userId);
        Integer type = user.getUserType();
        if (type != 3) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }
    }

    private void checkUserType(Integer userId) {
        User user = userMapper.selectById(userId);
        Integer type = user.getUserType();
        if (type < 2) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }
    }

    @Override
    public void markReport(Integer userId, Integer postId, String reason){
        checkUserType(userId);
        LambdaQueryWrapper<Report> reportQueryWrapper = new LambdaQueryWrapper<>();
        reportQueryWrapper.eq(Report::getPostId, postId);
        Report report=reportMapper.selectOne(reportQueryWrapper);
        if(report ==null){
            Post post =postMapper.selectById(postId);
            report= Report.builder().postId(postId).reason(reason).content(post.getContent()).build();
            reportMapper.insert(report);
        }
    }

    @Override
    public List<Report> getAllReports(Integer userId){
        checkUserType3(userId);
        LambdaQueryWrapper<Report> reportQueryWrapper = new LambdaQueryWrapper<>();
        reportQueryWrapper.orderByDesc(Report::getReportId);
        return reportMapper.selectList(reportQueryWrapper);
    }

    @Override
    public void reviewReport(Integer userId, Integer reportId, Integer approval){
        checkUserType3(userId);
        Report report=reportMapper.selectById(reportId);
        if(report ==null){
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        if(approval==1) {
            Post post = postMapper.selectById(report.getPostId());
            post.setLevel(0);
            postMapper.updateById(post);
        }
    }

    @Override
    public void deletePost(Integer userId, Integer postId){
        checkUserType3(userId);
        Post post=postMapper.selectById(postId);
        post.setLevel(0);
        postMapper.updateById(post);
        Integer acceptUserId=post.getAcceptUserId();
        messageService.reportMessage(acceptUserId,postId);
    }
}
