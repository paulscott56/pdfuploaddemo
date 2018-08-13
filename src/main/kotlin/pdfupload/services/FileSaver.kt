package pdfupload.services

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Paths
import java.time.Instant
import javax.annotation.PostConstruct

/**
 * @author Paul Scott based on code by Nejc Korasa
 */

@Component
class FileSaver(
        @Value("\${files.path}") private val filesBasePath: String,
        private val uploadTracer: UploadTracer) {

    @Autowired
    lateinit var pdfService: PdfServiceImpl

    private val logger = LoggerFactory.getLogger(FileSaver::class.java)

    @PostConstruct
    fun init() = logger.info("Files base path is set to: $filesBasePath")

    fun save(file: MultipartFile, user: String?) {

        logger.debug("Saving file: NAME=${file.originalFilename}, CONTENT_TYPE=${file.contentType}, SIZE=${file.size}")

        // create directory ( full path ) if it does not exist
        val dirName = user?.let { "$filesBasePath/$it" } ?: filesBasePath
        File(dirName).apply { if (!exists()) mkdirs() }

        // create new file if it does not exist
        val convertedFile = File("$dirName/${file.originalFilename}")
        convertedFile.createNewFile()

        // save file
        logger.debug("Saving file to ${convertedFile.absolutePath}")
        FileOutputStream(convertedFile).apply { write(file.bytes); close() }

        // trace uploads
        uploadTracer.trace(file.originalFilename!!, user)
        pdfService.save(file.originalFilename!!, user ?: "shared", Instant.now())
    }


    fun loadFile(filename: String, user: String?): UrlResource {
        val dirName = user?.let { "$filesBasePath/$it" } ?: filesBasePath
        val rootLocation = Paths.get("$dirName/${filename}")
        val resource = UrlResource(rootLocation.toUri())
        if (resource.exists() || resource.isReadable()) {
            return resource
        } else {
            throw RuntimeException("FAIL!")
        }
    }

}