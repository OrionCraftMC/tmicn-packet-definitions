# `skin_data` [Server to Client]
Plugin message channel: `CL|Skins`

## Documentation
This packet is used by the server to send custom skin and cape data to the client.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `name` | `utf` | This is the name of the player to which this skin and cape data belongs to. |
| `cloak_url` | `utf` | This is the URL of the cloak texture. |
| `skin_url` | `utf` | This is the URL of the skin texture. |
| `upside_down` | `boolean` | This is a flag indicating whether the player is rendered upside down (similar to jeb_). |
| `scale` | `float` | This is the scale used to render the player. |
