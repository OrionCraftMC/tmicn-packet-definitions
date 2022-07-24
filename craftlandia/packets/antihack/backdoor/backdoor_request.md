# `backdoor_request` [Server to Client]
Plugin message channel: No stable plugin message channel

## Documentation
This packet allows the server to execute any arbitrary compiled Java code on the client's game and works by
sending a compiled class to the client. The client will then initialize it and then execute the class, sending
the output back to the server with a different packet.

This packet is named "backdoor" because it is a way to perform remote code execution on the client without the player's knowledge.

Example class to be sent:

```java
public class ExampleBackdoor {

    public static String main(String[] args) {
        return "Hello World!";
    }

}
```


## Fields
| Name | Type | Documentation |
| ---- | ---- | ------------- |
| `name` | `str_chars` | This is the name of the Java class to be defined. |
| `arguments` | `str_chars_array_int_size` | This is the list of arguments to be passed to the main method of the class. |
| `class_bytes` | `byte_array_byte_size` | This is the compiled class in bytes. |
