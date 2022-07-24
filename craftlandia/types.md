## Packet Types

### 1. Type `boolean`
This is a boolean value that is read with `DataInputStream#readBoolean()` and written with `DataInputStream#writeBoolean()`.

### 2. Type `byte`
This is a byte value that is read with `InputStream#read` and written with `InputStream#write`.

### 3. Type `byte_array_byte_size`
This is an array of bytes written to the wire in the following format:
- The length of the array is written as a byte `OutputStream#write`
- Each byte is written as a byte `OutputStream#write`

### 4. Type `double`
This is a double value that is read with `DataInputStream#readDouble()` and written with `DataInputStream#writeDouble()`.

### 5. Type `float`
This is a float value that is read with `DataInputStream#readFloat()` and written with `DataInputStream#writeFloat()`.

### 6. Type `multiplex_content`
This is a multiplex content type.

The field marked with this type is replaced with the actual byte content from the multiplexed packet

### 7. Type `str_bytes`
This is a String that is read from a byte array.


<details>
<summary>I/O Pseudocode</summary>

Reading in Java:
```java
public static String readStrBytes(InputStream input) {
    String result = "";
    // First check if we have a string
    boolean hasValue = input.read() == 1;
    if (hasValue) {
        // We do, so read the number of bytes that make up the string
        int length = input.read();
        byte[] bytes = new byte[length];
        // Read the bytes from the stream into the array
        input.read(bytes);
        // Finally, convert the bytes to a string
        result = new String(bytes);
    }

    return result;
}
```

Writing in Java:
```java
public static void writeStrBytes(String str, OutputStream output) throws IOException {
    output.write(str != null ? 1 : 0);
    if (str != null) {
        output.write(str.length());
        output.write(str.getBytes());
    }
}
```

</details>

### 8. Type `str_chars`
This is a string that is written in the format of a list of characters,
being prefixed with the length of the string serialized as a short.

<details>
<summary>I/O Pseudocode</summary>

Reading in Java:
```java
public String readStrChars(DataOutputStream data) {
    int length = s.readShort();
    if (length > Short.MAX_VALUE) {
        throw new IOException("Received string length longer than maximum allowed (" + length + " > " + max + ")");
    }
    if (length < 0) {
        throw new IOException("Received string length is less than zero! Weird string!");
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; ++i) {
        sb.append(data.readChar());
    }
    return sb.toString();
}
```

Writing in Java:
```java
public static void writeStrChars(String str, DataOutputStream out) throws IOException {
    if (str.length() > Short.MAX_VALUE) {
        throw new IOException("String too big");
    }
    out.writeShort(str.length());
    out.writeChars(str);
}
```

</details>

### 9. Type `str_chars_array_int_size`
This is an array of strings of type str_chars but written to the wire in the following format:
- The length of the array is written as an int `DataOutputStream#writeInt`
- Each string is written as a str_chars

### 10. Type `utf`
This is a UTF string that is read with `DataInputStream#readUTF()` and written with `DataInputStream#writeUTF()`.
