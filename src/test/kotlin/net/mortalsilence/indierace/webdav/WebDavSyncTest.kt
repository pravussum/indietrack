package net.mortalsilence.indierace.webdav

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import net.mortalsilence.indierace.dao.Track
import net.mortalsilence.indierace.dao.TrackRepository
import net.mortalsilence.indierace.dto.Credentials
import net.mortalsilence.indierace.gpx.GpxTrackPersistor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations

internal class WebDavSyncTest {

    private lateinit var webDavSync : WebDavSync
    val gpxTrackPersistor : GpxTrackPersistor = mock()
    val trackRepository : TrackRepository = mock()

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        webDavSync = WebDavSync(
                gpxTrackPersistor,
                trackRepository
        )
    }

    @Test
    @Disabled
    fun scheduleWebDavSync() {
        whenever(gpxTrackPersistor.persistGpxTrack(any(), any(), any() )).then { inv ->
            Track(name = inv.arguments[1] as String)
        }

        webDavSync.syncWebDavDir(Credentials(System.getProperty("sync.webdav.user"),
                System.getProperty("sync.webdav.password")),
                System.getProperty("sync.webdav.url"))
    }
}