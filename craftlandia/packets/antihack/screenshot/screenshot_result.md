# `screenshot_result` [Client to Server]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used by the client to notify the server that a game screenshot from the client was uploaded successfully.

The upload process consists of the following steps:
- Screenshot the game through the normal process.
- Upload the screenshot to the server on the endpoint `/imgs/upload.php?uploadKey=<uploadKey>` where `<uploadKey>` is the key that was provided by the server when the request was made.
- Delete the screenshot from the client's local storage.
- Reply with this packet to the server with the same `uploadKey`.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `upload_key` | `str_chars` | The upload key for the screenshot. This is used by the server to keep track of the client's screenshot upload. |