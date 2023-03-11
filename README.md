# Termux:Boot

[![Build status](https://github.com/termux/termux-boot/workflows/Build/badge.svg)](https://github.com/termux/termux-boot/actions)
[![Join the chat at https://gitter.im/termux/termux](https://badges.gitter.im/termux/termux.svg)](https://gitter.im/termux/termux)

A [Termux](https://termux.dev) add-on app to run programs at boot.

When developing (or packaging), note that this app needs to be signed with the
same key as the main Termux app in order to have the permission to execute scripts.

## Installation

Termux:Boot application can be obtained from [F-Droid](https://f-droid.org/en/packages/com.termux.boot/).

Additionally we provide per-commit debug builds for those who want to try
out the latest features or test their pull request. This build can be obtained
from one of the workflow runs listed on [Github Actions](https://github.com/termux/termux-boot/actions)
page.

Signature keys of all offered builds are different. Before you switch the
installation source, you will have to uninstall the Termux application and
all currently installed plugins.

## How to use

1. Install the Termux:Boot app.
2. Start the Termux:Boot app once by clicking on its launcher icon. This allows the app to be run at boot.
3. Create the `~/.termux/boot/` directory.
4. Put scripts you want to execute inside the `~/.termux/boot/` directory. If there are multiple files, they will be executed in a sorted order.
5. Note that you may want to run `termux-wake-lock` as first thing if you want to ensure that the device is prevented from sleeping.

### Examples

To start an sshd server and prevent the device from sleeping at boot,
create the following file at `~/.termux/boot/start-sshd`:

```sh
#!/data/data/com.termux/files/usr/bin/sh
termux-wake-lock
sshd
```

To start
[termux-services](https://wiki.termux.com/wiki/Termux-services), which
in turn starts enabled services, you can put the following in
`~/.termux/boot/start-services`:

```sh
#!/data/data/com.termux/files/usr/bin/sh
termux-wake-lock
source /data/data/com.termux/files/usr/etc/profile.d/start-services.sh
```

## License

Released under [the GPLv3 license](https://www.gnu.org/licenses/gpl.html).
