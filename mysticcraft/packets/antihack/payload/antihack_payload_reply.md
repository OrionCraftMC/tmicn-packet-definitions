# `antihack_payload_reply` [Client to Server]
Plugin message channel: No stable plugin message channel

## Documentation
This is the packet that wraps all the communication that is done by the client.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `multiplex_id` | `byte` | The ID of the packet to be multiplexed. |
| `packet_content` | `multiplex_content` | The content of the packet that was multiplexed. |

## Multiplexing specification

This packet performs multiplexing. Meaning it is used to wrap multiple different packets under the same one.

|  Id  | Name |
| ---- | ---- |
| `3` | `game_fingerprint_response` |
