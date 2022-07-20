# Tell Me I Cheat Now!

_Go ahead, tell me I cheat now! **You won't!**_

## What is this?

This is a rewritten version of the TMICN project. The old version remains archived [here](https://github.com/OrionCraftMC/tmicn-lib-archive).

This version makes use of data-driven protocol definitions, and is therefore more flexible and extensible and less cumbersome to work on.
By using data-driven protocol definitions, you can easily add new protocols to the system, and easily keep up with changes to the system.

We make use of [TOML](https://github.com/toml-lang/toml/) for defining protocol definitions.

Automatically generated documentation based on the protocols specified in this branch are available [here](https://github.com/OrionCraftMC/tmicn-packet-definitions/tree/docs).

## Description

This library is designed to help with communicating that a player is not cheating when confronted with a client-side
anti-cheat check.

### Disclaimer

With this library, we are not encouraging cheating, nor we provide a way to do so.
We are simply providing a way to communicate with the server-side code that is responsible for the anti-cheat check.

## Supported Servers

- CraftLandia Farewell (1.5.2)
