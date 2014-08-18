#!/bin/bash
adb uninstall de.guerda.matekarte
adb install ./build/outputs/apk/Matekarte-debug.apk
adb shell am start -n de.guerda.matekarte/de.guerda.matekarte.MapActivity
adb logcat
