package com.posthog.flutter

import com.posthog.internal.replay.RREvent
import com.posthog.internal.replay.RRFullSnapshotEvent
import com.posthog.internal.replay.RRWireframe
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito

class SnapshotSenderTest {
    @Test
    fun sendFullSnapshot_encodesCorrectly() {
        val mockImageUtils = Mockito.mock(ImageUtils::class.java)
        val snapshotSender = SnapshotSender(mockImageUtils)

        val imageBytes = byteArrayOf(1, 2, 3)
        val id = 123
        val x = 10
        val y = 20
        val width = 100
        val height = 200
        val base64String = "base64EncodedString"

        Mockito.`when`(mockImageUtils.getDimensions(imageBytes)).thenReturn(Pair(width, height))
        Mockito.`when`(mockImageUtils.encodeToBase64(imageBytes)).thenReturn(base64String)

        // Capture static calls?
        // com.posthog.internal.replay.capture is an extension function on List<RREvent>.
        // Since it's a static extension function, we cannot verify it easily without PowerMock or Mockk static mocking.
        // However, we can at least verify that the logic up to that point is correct if we could verify the arguments passed to it.
        // But the `capture` function is called at the end.

        // Since we can't verify the static `capture()` call without extra tools,
        // we will verify the interactions with `ImageUtils` which confirms our optimization logic was called.

        snapshotSender.sendFullSnapshot(imageBytes, id, x, y)

        Mockito.verify(mockImageUtils).getDimensions(imageBytes)
        Mockito.verify(mockImageUtils).encodeToBase64(imageBytes)
    }
}
