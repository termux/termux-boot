# Termux:Boot

[![Build status](https://api.cirrus-ci.com/github/termux/termux-boot.svg?branch=master)](https://cirrus-ci.com/termux/termux-boot)
[![Join the chat at https://gitter.im/termux/termux](https://badges.gitter.im/termux/termux.svg)](https://gitter.im/termux/termux)

A [Termux](https://termux.com) add-on app to run programs at boot.

When developing (or packaging), note that this app needs to be signed with the
same key as the main Termux app in order to have the permission to execute scripts.

## Installation

Termux:Boot application can be obtained from:

- [Google Play](https://play.google.com/store/apps/details?id=com.termux.boot)
- [F-Droid](https://f-droid.org/en/packages/com.termux.boot/)
- [Kali Nethunter Store](https://store.nethunter.com/en/packages/com.termux.boot/)

Additionally we offer development builds for those who want to try out latest
features ready to be included in future versions. Such build can be obtained
directly from [Cirrus CI artifacts](https://api.cirrus-ci.com/v1/artifact/github/termux/termux-boot/debug-build/output/app/build/outputs/apk/debug/app-debug.apk).

Signature keys of all offered builds are different. Before you switch the
installation source, you will have to uninstall the Termux application and
all currently installed plugins.

## License

Released under [the GPLv3 license](https://www.gnu.org/licenses/gpl.html).

## How to use

1. Install the Termux:Boot app.
2. Start the Termux:Boot app once by clicking on its launcher icon. This allows the app to be run at boot.
3. Create the `~/.termux/boot/` directory.
4. Put scripts you want to execute inside the `~/.termux/boot/` directory. If there are multiple files, they will be executed in a sorted order.
5. Note that you may want to run `termux-wake-lock` as first thing if you want to ensure that the device is prevented from sleeping.

Example: To start an sshd server and prevent the device from sleeping at boot, create the following file at `~/.termux/boot/start-sshd`:

```sh
#!/data/data/com.termux/files/usr/bin/sh
termux-wake-lock
sshd
```
