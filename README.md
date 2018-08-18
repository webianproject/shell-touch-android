# Webian Shell Touch
A graphical shell for the web (touchscreen version).

This application is designed to be run as the main system app on [Android Things](https://developer.android.com/things/sdk/index.html).

## Getting Started
* Flash Android Things onto a developer board (e.g. [flash onto a Raspberry Pi 3](https://developer.android.com/things/hardware/raspberrypi.html) with the [official Raspberry Pi touchscreen](https://www.raspberrypi.org/products/raspberry-pi-touch-display/) or [RasPad](https://www.kickstarter.com/projects/35410622/raspad-raspberry-pi-tablet-for-your-creative-proje) touchscreen connected), boot it up and connect it to your local network via Ethernet or WiFi
* Clone this GitHub project onto a desktop computer and import the project into [Android Studio](https://developer.android.com/studio/index.html)
* Connect your desktop computer to the development board using `adb connect {ip address}` where `{ip address}` is the IP address displayed on the screen of your development board
* Click the "run" button to deploy the app to your development board over adb

## Tips

How to flip the display on a Raspberry Pi touchscreen (needed for some cases which mount the display upside down).

Plug the SD card into an SD card reader and mount the boot partition (you may need to create the `/mnt/sd` directory first and replace `disk2s1` with correct device reference for your computer):
```
$ sudo mount -t msdos /dev/disk2s1 /mnt/sd
```

Edit config.txt:
```
$ cd /mnt/sd
$ sudo nano config.txt
```

Add line:
```
lcd_rotate=2
```

Unmount the SD card:
```
$ sudo umount /mnt/sd
```

Alternatively you can flip the orientation in AndroidManifest.xml, but it will only apply to the Shell app:
```
<activity android:name=".MainActivity" android:screenOrientation="reverseLandscape">
```
