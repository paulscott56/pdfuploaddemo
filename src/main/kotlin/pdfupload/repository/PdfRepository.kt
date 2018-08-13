package pdfupload.repository

import org.springframework.data.repository.CrudRepository
import pdfupload.models.Pdf

interface PdfRepository : CrudRepository<Pdf, String> {
    fun findByUser(user: String): List<Pdf>
}