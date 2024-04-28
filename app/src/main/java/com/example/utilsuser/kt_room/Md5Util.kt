package com.example.utilsuser.kt_room

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * MD5工具类
 */
object Md5Util {
    @JvmStatic
    fun md5(string: String): String {
        val hash: ByteArray

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.toByteArray(charset("UTF-8")))
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Huh, MD5 should be supported?", e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("Huh, UTF-8 should be supported?", e)
        }

        val hex = StringBuilder(hash.size * 2)

        for (b in hash) {
            if ((b.toInt() and 0xFF) < 0x10) hex.append("0")

            hex.append(Integer.toHexString(b.toInt() and 0xFF))
        }
        'A'
        return hex.toString()
    }
}
