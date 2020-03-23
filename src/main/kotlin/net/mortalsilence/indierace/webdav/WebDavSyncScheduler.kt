package net.mortalsilence.indierace.webdav

import io.quarkus.scheduler.Scheduled
import net.mortalsilence.indierace.dto.Credentials
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class WebDavSyncScheduler(
        @ConfigProperty(name = "sync.webdav.url") internal val syncBaseUrl: String,
        @ConfigProperty(name = "sync.webdav.user") internal val syncUserName: String,
        @ConfigProperty(name = "sync.webdav.password") internal val syncPassword: String,
        @Inject internal val webDavSync: WebDavSync
) {
    var LOGGER = LoggerFactory.getLogger(WebDavSyncScheduler::class.java)

    @Scheduled(every = "5m")
    fun scheduleWebDavSync() {
        LOGGER.info("Starting WebDAV synchronization from $syncBaseUrl...")
        if(syncBaseUrl.isBlank()) {
            return
        }
        webDavSync.syncWebDavDir(Credentials(syncUserName, syncPassword), syncBaseUrl)
    }
}