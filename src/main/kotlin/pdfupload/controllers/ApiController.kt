package pdfupload.controllers

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pdfupload.models.Pdf
import pdfupload.services.FileSaver
import pdfupload.services.PdfServiceImpl

@RestController
@RequestMapping(value = "/api")
class ApiController(private val fileSaver: FileSaver) {

    @Autowired
    lateinit var pdfService: PdfServiceImpl

    private val logger = LoggerFactory.getLogger(ApiController::class.java)

    @PostMapping("/upload", consumes = [(MediaType.MULTIPART_FORM_DATA_VALUE)])
    fun save(@RequestParam("file") file: MultipartFile, @RequestParam("user", required = true, defaultValue = "shared") user: String) {
        fileSaver.save(file, user)
    }

    @GetMapping(value = "/list")
    fun getFiles(@RequestParam(value = "user", required = true, defaultValue = "shared") user: String): List<Pdf> = pdfService.fileList(user)

}
