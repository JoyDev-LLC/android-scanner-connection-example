package ru.gradus.datagetter.utils

import android.view.InputDevice

val possibleInputDevices = arrayOf(
    InputDevice.SOURCE_KEYBOARD to "keyboard",
    InputDevice.SOURCE_DPAD to "dpad",
    InputDevice.SOURCE_TOUCHSCREEN to "touchscreen",
    InputDevice.SOURCE_MOUSE to "mouse",
    InputDevice.SOURCE_STYLUS to "stylus",
    InputDevice.SOURCE_TRACKBALL to "trackball",
    InputDevice.SOURCE_MOUSE_RELATIVE to "mouse_relative",
    InputDevice.SOURCE_TOUCHPAD to "touchpad",
    InputDevice.SOURCE_JOYSTICK to "joystick",
    InputDevice.SOURCE_GAMEPAD to "gamepad",
)

fun getSources(sources: Int): String {
    var sourcesString = ""
    possibleInputDevices.forEach { (code, name) ->
        if ((sources and code) == code) {
            sourcesString += " $name"
        }
    }
    return sourcesString
}
