# `screenshot_request` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the server to request a game screenshot from the client.

Along with the screenshot, the client also uploads the user's current process list, the current player's username.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `upload_data` | `str_bytes` | This field is a string in the following format `<upload key>/<staffer>` where:
   - `<upload key>` is a randomly generated upload key from the server
   - `<staffer>` is the username of the staff member that requested the screenshot from the player |
