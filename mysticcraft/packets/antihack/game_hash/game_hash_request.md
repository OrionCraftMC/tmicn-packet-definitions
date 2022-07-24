# `game_hash_request` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the server to request the client to send a hash of the game jar.

The steps required to send a hash of the game jar are:
- The server sends this packet to the client.
- The client computes the md5 hash of the game jar.
- The client sends a chat message containing with the following format: `<chat prefix>.:.:<md5 hash>`
    - where `<chat prefix>` is the chat prefix defined in this packet
    - where `<md5 hash>` is the md5 hash of the game jar


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `chat_prefix` | `str_chars` | The chat prefix used to identify a hash request. |