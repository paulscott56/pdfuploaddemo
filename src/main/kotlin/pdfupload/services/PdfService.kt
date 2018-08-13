package pdfupload.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pdfupload.models.Pdf
import pdfupload.repository.PdfRepository
import java.time.Instant

interface PdfService {
    fun fileList(user: String): List<Pdf>
    fun save(filename: String, user: String, created: Instant)
}

@Service
class PdfServiceImpl : PdfService {

    @Autowired
    lateinit var pdfRepository: PdfRepository

    override fun fileList(user: String): List<Pdf> =
            pdfRepository.findByUser(user)

    override fun save(filename: String, user: String, created: Instant) {
        val pdf = Pdf(null, user, filename, created)
        pdfRepository.save(pdf)
    }
}