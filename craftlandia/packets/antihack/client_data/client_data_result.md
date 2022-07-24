# `client_data_result` [Client to Server]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the client to respond to the server's request of some game constants.

This data is then used by the server to flag certain cheats like Hitboxes.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `speed_modifier` | `double` | The player's speed modifier and is presumably used to detect Speed hacks. It is assumed that if this is different than what is expected, the player is flagged as cheating. |
| `unknown_data_1` | `double` | It is unknown what this is used for, although it appears to be a constant. |
| `unknown_data_2` | `double` | It is unknown what this is used for, although it appears to be a constant. |
| `unknown_data_3` | `double` | It is unknown what this is used for, although it appears to be a constant. |
| `unknown_data_4` | `double` | It is unknown what this is used for, although it appears to be a constant. |
| `unknown_data_5` | `double` | It is unknown what this is used for, although it appears to be a constant. |
| `entity_hitbox_width` | `float` | This is the constant of an entity width. It is assumed that if this is different than what is expected, the player is flagged as cheating. |
| `entity_hitbox_height` | `float` | This is the constant of an entity height. It is assumed that if this is different than what is expected, the player is flagged as cheating. |
