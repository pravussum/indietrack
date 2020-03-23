package net.mortalsilence.indierace.webdav

import com.github.sardine.DavResource
import com.github.sardine.SardineFactory
import net.mortalsilence.indierace.dto.Credentials
import java.net.URL

fun DavResource.getFilesRecursive(credentials: Credentials,
                                  baseUrl: URL,
                                  fileFilter: (DavResource) -> Boolean): Iterable<DavResource> {

    if(!this.isDirectory) {
        return if(fileFilter.invoke(this)) listOf(this) else emptyList()
    }
    val href = this.href.toString()
    return getWebDavFilesRecursive(credentials, URL(baseUrl, href).toExternalForm(), fileFilter)
}

fun getWebDavFilesRecursive(credentials: Credentials, baseUrl: String, fileFilter: (DavResource) -> Boolean): Iterable<DavResource> {
    val sardine = SardineFactory.begin(credentials.username, credentials.password)
    try {
        return sardine
                .list(baseUrl)
                .filter {
                    !isSameUrl(baseUrl, it.href.toString())
                }
                .flatMap { davResource -> davResource.getFilesRecursive(credentials, URL(baseUrl), fileFilter) }
    } finally {
        sardine.shutdown()
    }
}

private fun isSameUrl(originalBaseUrl: String, urlToCheck: String ) : Boolean {
    val root = if(originalBaseUrl.endsWith("/")) originalBaseUrl else "$originalBaseUrl/"
    return URL(root).path.equals(urlToCheck)
}
