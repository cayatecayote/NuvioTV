package com.nuvio.tv.updater

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VersionUtilsTest {
    @Test
    fun `stable release is newer than its prerelease`() {
        assertTrue(VersionUtils.isRemoteNewer("1.1.0", "1.1.0-rc.2"))
    }

    @Test
    fun `beta identifiers use numeric ordering`() {
        assertTrue(VersionUtils.isRemoteNewer("1.1.0-beta.10", "1.1.0-beta.9"))
    }

    @Test
    fun `prerelease for a later minor is newer than current stable`() {
        assertTrue(VersionUtils.isRemoteNewer("1.1.0-beta.1", "1.0.3"))
    }

    @Test
    fun `stable channel patch is not newer than later minor beta`() {
        assertFalse(VersionUtils.isRemoteNewer("1.0.4", "1.1.0-beta.2"))
    }

    @Test
    fun `version prefix and build metadata do not affect precedence`() {
        assertFalse(VersionUtils.isRemoteNewer("v1.0.0+18", "1.0.0+17"))
    }

    @Test
    fun `invalid remote version is not offered`() {
        assertFalse(VersionUtils.isRemoteNewer("latest", "1.0.0"))
    }

    @Test
    fun `current beta naming is recognized as prerelease`() {
        assertTrue(VersionUtils.isPrerelease("0.8.12-beta"))
    }

    @Test
    fun `nuvio plus marker does not affect precedence`() {
        // Same upstream version: the "-plus" release tag equals the "-juan" installed build.
        assertFalse(VersionUtils.isRemoteNewer("1.1.0-beta.9-plus", "1.1.0-beta.9-juan"))
        assertFalse(VersionUtils.isRemoteNewer("1.1.0-beta.9-juan", "1.1.0-beta.9-plus"))
        // Newer upstream version is offered even though "10-plus" sorts below "9-juan" as text.
        assertTrue(VersionUtils.isRemoteNewer("1.1.0-beta.10-plus", "1.1.0-beta.9-juan"))
        assertTrue(VersionUtils.isRemoteNewer("1.1.0-plus", "1.1.0-beta.9-juan"))
        assertFalse(VersionUtils.isRemoteNewer("1.1.0-beta.8-plus", "1.1.0-beta.9-juan"))
    }

    @Test
    fun `nuvio plus marker keeps prerelease detection`() {
        assertTrue(VersionUtils.isPrerelease("1.1.0-beta.9-juan"))
        assertFalse(VersionUtils.isPrerelease("1.1.0-plus"))
    }
}
