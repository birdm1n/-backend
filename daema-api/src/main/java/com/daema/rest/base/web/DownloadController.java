package com.daema.rest.base.web;

import com.daema.rest.base.service.ExcelDownloadService;
import com.daema.rest.common.Constants;
import com.daema.rest.common.handler.ResponseHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/download")
public class DownloadController {

    private final ExcelDownloadService excelDownloadService;

    private final ResponseHandler responseHandler;

    public DownloadController(ExcelDownloadService excelDownloadService, ResponseHandler responseHandler) {
        this.excelDownloadService = excelDownloadService;
        this.responseHandler = responseHandler;
    }

    /**
     * file download sample
     */
    @GetMapping("/file/sample")
    public ModelAndView fileDownloadSample(HttpServletRequest request){

        ModelAndView mv = new ModelAndView();

        try {
            String filePath = "path/path/path/";
            String fileName = "sample.zip";

            File downloadFile = new File(filePath + fileName);

            mv.setViewName(Constants.FILE_DOWNLOAD_VIEW);
            mv.addObject("fileName", fileName);
            mv.addObject("downloadFile", downloadFile);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }

    /**
     * excel download sample
     */
    @PostMapping(value = "/excel/sample")
    public ModelAndView excelDownloadSample(@RequestBody ModelMap modelMap) {

        ModelAndView mv = new ModelAndView();

        try {
            mv.setViewName(Constants.EXCEL_DOWNLOAD_VIEW);
            mv.addObject("xlsMap", excelDownloadService.init(modelMap, "sample"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }

    /**
     * excel download
     */
    @PostMapping(value = "/excel/{pageType}")
    public ModelAndView excelDownload(@RequestBody ModelMap modelMap, @PathVariable String pageType) {

        ModelAndView mv = new ModelAndView();

        try {
            mv.setViewName(Constants.EXCEL_DOWNLOAD_VIEW);
            mv.addObject("xlsMap", excelDownloadService.init(modelMap, pageType));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }
}


























