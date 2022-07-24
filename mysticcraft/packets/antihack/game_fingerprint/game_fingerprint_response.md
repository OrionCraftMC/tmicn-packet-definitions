# `game_fingerprint_response` [Client to Server]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the client to respond to the server with the fingerprint.

It is currently unknown how this fingerprint is generated, but one would assume it's generated randomly at build time.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `fingerprint_uuid` | `str_chars` | The client fingerprint converted to a UUID by using `UUID#nameUUIDFromBytes` |
