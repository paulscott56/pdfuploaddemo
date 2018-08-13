package pdfupload.controllers


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam
import pdfupload.services.FileSaver


@Controller
class DownloadFileController {

    @Autowired
    lateinit var fileSaver: FileSaver

    /*
     * Download Files
     */
    @GetMapping("/files")
    fun downloadFile(@RequestParam(value = "filename") filename: String, @RequestParam(value = "user", defaultValue = "shared", required = true) user: String): ResponseEntity<Resource> {
        val file = fileSaver.loadFile(filename, user)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}