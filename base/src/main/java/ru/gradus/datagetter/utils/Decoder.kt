package ru.gradus.datagetter.utils

import android.view.KeyEvent

sealed interface Decoder {
    fun handleKey(keyEvent: KeyEvent): String? {
        return when (keyEvent.keyCode) {
            in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9,
            in KeyEvent.KEYCODE_A..KeyEvent.KEYCODE_PERIOD,
            in KeyEvent.KEYCODE_GRAVE..KeyEvent.KEYCODE_AT, KeyEvent.KEYCODE_PLUS, KeyEvent.KEYCODE_SPACE -> {
                var newString = ""
                Character.toChars(keyEvent.getUnicodeChar(keyEvent.metaState))
                    .map { it }
                    .toString().let { string ->
                        for (i in string.indices step 3) {
                            newString += string[i + 1]
                        }
                    }
                newString.ifEmpty { return null }
            }

            else -> null
        }
    }

    class Keyboard(private val deviceId: Int) : Decoder {
        private var temp = ""
        override fun handleKey(keyEvent: KeyEvent): String? {
            if (keyEvent.deviceId != deviceId) return null
            temp += super.handleKey(keyEvent)
            return temp
        }

        fun clearTemp() {
            temp = ""
        }
    }

    class Scanner(private val deviceId: Int, private val lastKeyAction: Int) : Decoder {
        private var temp = ""
        override fun handleKey(keyEvent: KeyEvent): String? {
            if (keyEvent.deviceId != deviceId) return null
            val defaultHandler = super.handleKey(keyEvent)
            temp += defaultHandler ?: ""
            if (defaultHandler == null) {
                when (keyEvent.keyCode) {
                    lastKeyAction -> {
                        val value = temp
                        temp = ""
                        return value
                    }
                }
            }
            return null
        }
    }
}