package com.cpda.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @auther: Zealon
 * @Date: 2018-09-26 13:45
 */
@Controller
@RequestMapping("system/file")
public class FileUpLoadController {

    @RequestMapping("/show")
    public String show(){
        return "/system/file/add";
    }

    @ResponseBody
    @RequestMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String filePath = request.getSession().getServletContext().getRealPath("upload/");
        try {
            uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "ok";
    }

    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
