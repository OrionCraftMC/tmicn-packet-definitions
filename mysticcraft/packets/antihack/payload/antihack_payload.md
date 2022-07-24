# `antihack_payload` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This is the main packet that wraps all the communication that is done by the server.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `multiplex_id` | `byte` | The ID of the packet to be multiplexed. |
| `packet_content` | `multiplex_content` | The content of the packet that was multiplexed. |

## Multiplexing specification

This packet performs multiplexing. Meaning it is used to wrap multiple different packets under the same one.

|  Id  | Name |
| ---- | ---- |
| `1` | `screenshot_request` |
| `2` | `game_hash_request` |
| `3` | `game_fingerprint_request` |
| `4` | `blaze_effect` |
| `5` | `mickey_effect` |
| `6` | `unknown_packet1` |
| `7` | `antihack_version` |
