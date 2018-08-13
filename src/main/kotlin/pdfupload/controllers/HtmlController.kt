package pdfupload.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import pdfupload.services.FileSaver
import pdfupload.services.PdfServiceImpl

@Controller
class HtmlController {

    @Autowired
    lateinit var pdfService: PdfServiceImpl

    @Autowired
    lateinit var fileSaver: FileSaver

    @GetMapping("/")
    fun viewFiles(@RequestParam(value = "user", defaultValue = "shared", required = true) user: String,
                  model: Model): String {
        val files = pdfService.fileList(user)
        model["title"] = "PDF uploads"
        model["user"] = user
        model.addAttribute("files", files)

        return "pdf"
    }

    @GetMapping("/uploadform")
    fun uploadFile(@RequestParam(value = "user", defaultValue = "shared", required = true) user: String,
                   model: Model): String {
        model["title"] = "PDF uploads"
        model["user"] = user

        return "upload"
    }

    @PostMapping(value = "/uploadform")
    fun save(@RequestParam("file") file: MultipartFile, @RequestParam("user", required = true, defaultValue = "shared") user: String, model: Model): String {
        fileSaver.save(file, user)
        val files = pdfService.fileList(user)
        model["title"] = "PDF uploads"
        model["user"] = user
        model.addAttribute("files", files)

        return "pdf"
    }

}