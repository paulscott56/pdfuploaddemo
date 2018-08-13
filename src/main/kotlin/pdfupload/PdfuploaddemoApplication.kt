package pdfupload

import com.samskivert.mustache.Mustache
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan
open class PdfuploaddemoApplication {


    @Bean
    fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
            Mustache.compiler().escapeHTML(false).withLoader(loader)

}

fun main(args: Array<String>) {
    runApplication<PdfuploaddemoApplication>(*args)
}



