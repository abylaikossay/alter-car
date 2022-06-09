package net.alterapp.altercar.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import net.alterapp.altercar.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/file")
@AllArgsConstructor
public class FileController extends BaseController {

    private FileService fileService;

    @GetMapping("/{type}/{fileName:.+}")
    @ApiOperation("Получить файл файл")
    public ResponseEntity<byte[]> showFile(@PathVariable String fileName,
                                           @PathVariable String type,
                                           HttpServletRequest request) throws IOException {

        Resource resource = fileService.getFile(fileName, type);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("could not determine file");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(Files.readAllBytes(resource.getFile().toPath()));
    }
//
//    @GetMapping("/download/{type}/{fileName:.+}")
//    @ApiOperation("Скачать файл")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @PathVariable String type, HttpServletRequest request) {
//
//        Resource resource = fileServiceV1.getFile(fileName, type);
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            System.out.println("could not determine file");
//        }
//
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }


}
