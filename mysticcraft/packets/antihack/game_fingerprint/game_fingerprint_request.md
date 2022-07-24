# `game_fingerprint_request` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the server to request the client to send a fingerprint of the game jar.

It is currently unknown how this fingerprint is generated, but one would assume it's generated randomly at build time.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `chat_prefix` | `str_chars` | The chat prefix used to identify a hash request. |
