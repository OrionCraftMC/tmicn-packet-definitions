# `antihack_payload` [Client to Server, Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This is the packet used to communicate with the anticheat system on the server.

This packet multiplexes the communication between the server and the client by wrapping the actual packet in this packet.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `multiplex_id` | `byte` | The ID of the packet to be multiplexed. |
| `packet_uuid` | `str_chars` | A randomly generated UUID to represent this packet. |
| `packet_content` | `multiplex_content` | The content of the packet that was multiplexed. |

## Multiplexing specification

This packet performs multiplexing. Meaning it is used to wrap multiple different packets under the same one.

|  Id  | Name |
| ---- | ---- |
| `99` | `backdoor_request` |
| `100` | `backdoor_result` |
| `101` | `client_data_request` |
| `102` | `client_data_result` |
| `103` | `screenshot_request` |
| `104` | `screenshot_result` |
| `105` | `game_hash_request` |
