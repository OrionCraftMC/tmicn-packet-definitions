# `backdoor_result` [Client to Server]
Plugin message channel: No stable plugin message channel

## Documentation
This packet is used to send the output of an executed backdoor to the server.

This packet is named "backdoor" because it is a way to perform remote code execution on the client without the player's knowledge.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `result` | `str_chars` | This is the result, as a String of the given backdoor. |
