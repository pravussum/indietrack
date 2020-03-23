package net.mortalsilence.indierace.webdav

import com.github.sardine.SardineFactory
import net.mortalsilence.indierace.dao.TrackRepository
import net.mortalsilence.indierace.dto.Credentials
import net.mortalsilence.indierace.gpx.GpxTrackPersistor
import org.slf4j.LoggerFactory
import java.io.InvalidObjectException
import java.net.URL
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional


@Suppress("unused")
@ApplicationScoped
open class WebDavSync (

        @Inject internal val gpxTrackPersistor: GpxTrackPersistor,
        @Inject internal val trackRepository: TrackRepository
){

    var LOGGER = LoggerFactory.getLogger(WebDavSync::class.java)

    fun syncWebDavDir(credentials: Credentials, webdavUrl: String) {

        val baseUrl = URL(webdavUrl)
        val existingEtags = trackRepository.getExternalIds()

        getWebDavFilesRecursive(credentials, webdavUrl) {
            it.path.endsWith(".gpx") && !existingEtags.contains(it.etag)
        }.forEach { davResource ->
            val url = URL(baseUrl, davResource.href.toString()).toExternalForm()
            LOGGER.debug("Downloading $url ...")
            val sardine = SardineFactory.begin(credentials.username, credentials.password)
            try {
                val gpxStream = sardine.get(url)
                gpxTrackPersistor.persistGpxTrack(gpxStream, getFilenameOnly(davResource.path), davResource.etag)
            } catch (e: InvalidObjectException) {
                LOGGER.info("Skipping invalid file ${davResource.path}: ${e.message}")
            } finally {
                sardine.shutdown()
            }
        }
    }

    private fun getFilenameOnly(fileName: String) : String {
        return fileName.subSequence(fileName.lastIndexOf("/"), fileName.lastIndexOf(".")).toString()
    }

}