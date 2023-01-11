package com.project.easy_travel.ViewModel

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SHA256 {

    fun convertToHex(data: ByteArray): String {
        val buf = StringBuffer()
        for (i in data.indices) {
            var halfbyte = data[i].toInt() ushr 4 and 0x0F
            var two_halfs = 0
            while (two_halfs < 2) {
                if (halfbyte < 10)
                    buf.append(('0'.toInt() + halfbyte).toChar())
                else
                    buf.append(('a'.toInt() + (halfbyte - 10)).toChar())
                halfbyte = data[i].toInt() and 0x0F
                two_halfs++
            }
        }
        return buf.toString()
    }

    fun SHA256(text: String): String {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(text.toByteArray(charset("iso-8859-1")), 0, text.length)
            val sha1hash = md.digest()
            return convertToHex(sha1hash)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return ""
    }



}