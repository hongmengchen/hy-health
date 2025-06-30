package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.service.FileUploadService;
import cn.rj.hyhealthbackend.util.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

/**
 * @author 陈亮
 *
 * FileUploadController
 * - 实现从前端接收文件并下载到本地，通过调用service层方法将文件存入本地并返回封装后的文件url
 */
@Api(tags = "文件上传控制器")
@RestController
@RequestMapping("/api/base/upload")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @RolesAllowed({"1","2"})
    @PostMapping(value = "")
    public Msg fileUpload(MultipartFile file) {
        Msg msg = fileUploadService.upload(file);
        return msg;
    }
}
