package com.viverselftest.service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Congwz on 2018/10/11.
 */
public interface StreamDownloadService {

    void getResponse(String path, HttpServletResponse response);
}
