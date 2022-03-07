package net.mortalsilence.indierace.webdav

import com.github.sardine.SardineFactory
import net.mortalsilence.indierace.persistence.repositories.track.TrackRepository
import net.mortalsilence.indierace.dto.Credentials
import net.mortalsilence.indierace.gpx.GpxTrackPersistor
import net.mortalsilence.indierace.persistence.repositories.trackpoint.TrackPointRepository
import org.slf4j.LoggerFactory
import java.net.URL
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@Suppress("unused")
@ApplicationScoped
open class WebDavSync (

        @Inject internal var gpxTrackPersistor: GpxTrackPersistor,
        @Inject internal var trackRepository: TrackRepository,
        @Inject internal var trackPointRepository: TrackPointRepository
){

    var LOGGER = LoggerFactory.getLogger(WebDavSync::class.java)

    fun syncWebDavDir(credentials: Credentials, webdavUrl: String) {

        val baseUrl = URL(webdavUrl)
//        val existingEtagsWithName = trackRepository.getExternalIds()
        val existingNames = trackRepository.getNames()

        // TODO compare etags for already existing name and intelligently update the existing track

        getWebDavFilesRecursive(credentials, webdavUrl) {
            it.path.endsWith(".gpx") && !existingNames.contains(getFilenameOnly(it.path)) // TODO handly filename duplicates in different directories
        }.forEach { davResource ->
            val url = URL(baseUrl, davResource.href.toString()).toExternalForm()
            LOGGER.debug("Downloading $url ...")
            // TODO reuse connection
            val sardine = SardineFactory.begin(credentials.username, credentials.password)
            try {
                val gpxStream = sardine.get(url)
                gpxTrackPersistor.persistGpxTrack(gpxStream, getFilenameOnly(davResource.path), davResource.etag)
            } catch (e: Exception) {
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