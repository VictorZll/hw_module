package com.suree.hw_module.controller.versionController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/version")
public class VersionUpload {

    @RequestMapping("/uploadWar")
    public String uploadWar(HttpServletRequest request){
        try {
            String realPath = request.getSession().getServletContext().getRealPath("");
            File parentFile = new File(realPath).getParentFile();
            realPath=parentFile.toString();
            System.out.println(realPath);
            Part war = request.getPart("war");
            war.write(realPath+"/"+"hw_module.war");
//            File file=new File(realPath+"/"+"hw");
//            if(file.isDirectory()){
//                file.delete();
//            }
            System.out.println(realPath+"/"+"hw_module.war");
        }catch (Exception e){
            e.printStackTrace();
            return "上传失败";
        }

        return "上传成功";
    }

}
