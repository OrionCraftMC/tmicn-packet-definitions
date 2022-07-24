# `blaze_effect` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the server to apply a "Blaze" effect to a player.

This effect is just rendering Blaze rods on a player.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `name` | `str_chars` | This is the name of the player receiving this effect prefixed by an action.

### Actions
| Prefix Character |                         Description                         |
|:----------------:|:-----------------------------------------------------------:|
|       `+`        |   This action means we are adding this affect to a player   |
|       `-`        | This action means we are removing this effect from a player | |