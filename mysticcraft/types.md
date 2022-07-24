## Packet Types

### 1. Type `byte`
This is a byte value that is read with `InputStream#read` and written with `InputStream#write`.

### 2. Type `multiplex_content`
This is a multiplex content type.

The field marked with this type is replaced with the actual byte content from the multiplexed packet

### 3. Type `str_bytes`
This is a String that is read from a byte array.


<details>
<summary>I/O Pseudocode</summary>

Reading in Java:
```java
public static String readStrBytes(InputStream input) {
    // Read the number of bytes that make up the string
    int length = input.read();
    byte[] bytes = new byte[length];
    // Read the bytes from the stream into the array
    input.read(bytes);
    // Finally, convert the bytes to a string
    String result = new String(bytes, "UTF-8");

    return result;
}
```

Writing in Java:
```java
public static void writeStrBytes(String str, OutputStream output) throws IOException {
    output.write(str.length());
    output.write(str.getBytes());
}
```

</details>

### 4. Type `str_chars`
No documentation available.
