package pdfupload.models

import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "files")
class Pdf(val id: String? = null, val user: String = "", val filename: String = "", val created: Instant)

