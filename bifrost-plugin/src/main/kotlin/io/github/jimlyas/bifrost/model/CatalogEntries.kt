package io.github.jimlyas.bifrost.model

import kotlinx.serialization.Serializable

@Serializable
data class CatalogEntries(val bundles: BundleEntries)


@Serializable
data class BundleEntries(val active: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BundleEntries

        return active.contentEquals(other.active)
    }

    override fun hashCode(): Int {
        return active.contentHashCode()
    }
}