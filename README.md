Termux:Boot
===========
[![Join the chat at https://gitter.im/termux/termux](https://badges.gitter.im/termux/termux.svg)](https://gitter.im/termux/termux)

A [Termux](https://termux.com) add-on app to run programs at boot.

- [Termux:Boot on Google Play](https://play.google.com/store/apps/details?id=com.termux.boot)
- [Termux:Boot on F-Droid](https://f-droid.org/packages/com.termux.boot)

When developing (or packaging), note that this app needs to be signed with the same key as the main Termux app in order to have the permission to execute scripts.

License
=======
Released under [the GPLv3 license](https://www.gnu.org/licenses/gpl.html).

How to use
==========
1. Install the Termux:Boot app.
2. Start the Termux:Boot app once by clicking on its launcher icon. This allows the app to be run at boot.
3. Create the `~/.termux/boot/` directory.
4. Put scripts you want to execute inside the `~/.termux/boot/` directory. If there are multiple files, they will be executed in a sorted order.
5. Note that you may want to run `termux-wake-lock` as first thing if you want to ensure that the device is prevented from sleeping.

Example: To start an sshd server and prevent the device from sleeping at boot, create the following file at `~/.termux/boot/start-sshd`:

```sh
termux-wake-lock
sshd
```
