package net.mortalsilence.indierace.webdav

import io.quarkus.scheduler.Scheduled
import net.mortalsilence.indierace.dto.Credentials
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@Suppress("unused")
@ApplicationScoped
class WebDavSyncScheduler(
        @ConfigProperty(name = "sync.webdav.url") internal val syncBaseUrl: String?,
        @ConfigProperty(name = "sync.webdav.user") internal val syncUserName: String?,
        @ConfigProperty(name = "sync.webdav.password") internal val syncPassword: String?,
        @Inject internal var webDavSync: WebDavSync
) {
    var LOGGER = LoggerFactory.getLogger(WebDavSyncScheduler::class.java)

    @Scheduled(every = "5m")
    fun scheduleWebDavSync() {
        if(syncBaseUrl == null || syncPassword == null || syncUserName == null) {
            LOGGER.debug("WebDAV sync not fully configured (url/user/password) - skipping sync.")
            return
        }
        LOGGER.info("Starting WebDAV synchronization from $syncBaseUrl...")
        if(syncBaseUrl!!.isBlank()) {
            return
        }
        webDavSync.syncWebDavDir(Credentials(syncUserName!!, syncPassword!!), syncBaseUrl!!)
    }
}