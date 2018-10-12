package com.viverselftest.api;
import com.viverselftest.common.ServerResponse;
import com.viverselftest.service.StreamDownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Congwz on 2018/10/11.
 */
@Api(tags = "以网络流下载")
@RestController
@RequestMapping("/api/stream/download")
public class StreamDownloadApi {

    @Autowired
    private StreamDownloadService streamDownloadService;

    @ApiOperation(value = "以流的方式下载文件")
    @GetMapping("/")
    public void getResponse(@RequestParam(name = "path") String path, HttpServletResponse response){
        System.out.println("path: " + path);
        streamDownloadService.getResponse(path,response);
    }




}
