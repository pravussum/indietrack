package net.mortalsilence.indierace.dto

import org.jboss.resteasy.annotations.providers.multipart.PartType
import java.io.InputStream
import javax.ws.rs.FormParam
import javax.ws.rs.core.MediaType

class MultipartBody {
    @FormParam("file")
    var file: InputStream? = null

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    var fileName: String? = null
}