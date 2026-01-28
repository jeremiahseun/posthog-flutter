package com.posthog.flutter

import com.posthog.internal.replay.RREvent
import com.posthog.internal.replay.RRFullSnapshotEvent
import com.posthog.internal.replay.RRMetaEvent
import com.posthog.internal.replay.RRStyle
import com.posthog.internal.replay.RRWireframe
import com.posthog.internal.replay.capture

class SnapshotSender(private val imageUtils: ImageUtils = AndroidImageUtils()) {
    fun sendFullSnapshot(
        imageBytes: ByteArray,
        id: Int,
        x: Int,
        y: Int,
    ) {
        val (width, height) = imageUtils.getDimensions(imageBytes)
        val base64String = imageUtils.encodeToBase64(imageBytes)

        val wireframe =
            RRWireframe(
                id = id,
                x = x,
                y = y,
                width = width,
                height = height,
                type = "screenshot",
                base64 = base64String,
                style = RRStyle(),
            )

        val snapshotEvent =
            RRFullSnapshotEvent(
                listOf(wireframe),
                initialOffsetTop = 0,
                initialOffsetLeft = 0,
                timestamp = System.currentTimeMillis(),
            )

        listOf(snapshotEvent).capture()
    }

    fun sendMetaEvent(
        width: Int,
        height: Int,
        screen: String,
    ) {
        val metaEvent =
            RRMetaEvent(
                href = screen,
                width = width,
                height = height,
                timestamp = System.currentTimeMillis(),
            )

        val events = mutableListOf<RREvent>()
        events.add(metaEvent)

        events.capture()
    }
}
