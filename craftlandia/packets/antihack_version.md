# `antihack_version` [Server to Client]
Plugin message channel: `CL|AHVersion`

## Documentation
This packet is used by the server to notify the client of the current anti-hack version build.


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `version_hash` | `str_bytes` | The version hash of the current anti-hack build. |
