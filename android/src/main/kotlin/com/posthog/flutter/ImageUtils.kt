package com.posthog.flutter

import android.graphics.BitmapFactory
import android.util.Base64

interface ImageUtils {
    fun getDimensions(imageBytes: ByteArray): Pair<Int, Int>
    fun encodeToBase64(imageBytes: ByteArray): String
}

class AndroidImageUtils : ImageUtils {
    override fun getDimensions(imageBytes: ByteArray): Pair<Int, Int> {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, options)
        return Pair(options.outWidth, options.outHeight)
    }

    override fun encodeToBase64(imageBytes: ByteArray): String {
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP)
    }
}
