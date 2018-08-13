package pdfupload.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import pdfupload.models.TraceInfo

@Component
class UploadTracer(
        private val objectMapper: ObjectMapper,
        private val environment: Environment) {

    private val logger = LoggerFactory.getLogger("trace-log")

    fun trace(file: String, directory: String?) = environment.acceptsProfiles("trace-enable")
            .takeIf { it }
            ?.let { logger.info(objectMapper.writeValueAsString(TraceInfo(file, directory))) }
}