# `screenshot_request` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the server to request a game screenshot from the client.

Along with the screenshot, the client also uploads the user's current process list.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `upload_key` | `str_chars` | The upload key for the screenshot. This is used by the server to keep track of the client's screenshot upload. |
