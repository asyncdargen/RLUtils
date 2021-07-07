package ru.redline.util.io;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import ru.redline.util.GsonSerializer;

import java.nio.BufferOverflowException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NetUtil {

    public static ByteBuf buffer() {
        return Unpooled.buffer();
    }

    public static int readVarInt(ByteBuf input) {
        byte in;
        int out = 0;
        int bytes = 0;
        int var4 = input.readableBytes();
        do {
            if (var4-- == 0)
                throw new ArrayIndexOutOfBoundsException("End of buffer");
            in = input.readByte();
            out |= (in & Byte.MAX_VALUE) << bytes++ * 7;
            if (bytes > 5)
                throw new ArrayIndexOutOfBoundsException("Var int to big");
        } while ((in & 0x80) == 128);
        return out;
    }

    public static long readVarLong(ByteBuf input) {
        byte in;
        long out = 0L;
        int bytes = 0;
        int var5 = input.readableBytes();
        do {
            if (var5-- == 0)
                throw new ArrayIndexOutOfBoundsException("Ent of buffer");
            in = input.readByte();
            out |= (in & Byte.MAX_VALUE) << bytes++ * 7;
            if (bytes > 10)
                throw new ArrayIndexOutOfBoundsException("Var long to big");
        } while ((in & 0x80) == 128);
        return out;
    }

    public static void writeVarInt(int value, ByteBuf output) {
        while (true) {
            int bits = value & 0x7F;
            value >>>= 7;
            if (value == 0) {
                output.writeByte((byte) bits);
                return;
            }
            output.writeByte((byte) (bits | 0x80));
        }
    }

    public static void writeVarLong(long value, ByteBuf output) {
        while (true) {
            long bits = value & 0xFFFFFFFFFFFFFF80L;
            if (bits == 0L) {
                output.writeByte((int) value);
                return;
            }
            output.writeByte((int) (value & 0x7FL) | 0x80);
            value >>>= 7L;
        }
    }

    public static UUID readId(ByteBuf input) {
        return new UUID(input.readLong(), input.readLong());
    }

    public static void writeId(UUID id, ByteBuf output) {
        output.ensureWritable(16).writeLong(id.getMostSignificantBits()).writeLong(id.getLeastSignificantBits());
    }

    public static byte[] readArray(ByteBuf input, int limit) {
        int len = readVarInt(input);
        if (len > limit)
            throw new BufferOverflowException();
        byte[] bytes = new byte[len];
        input.readBytes(bytes);
        return bytes;
    }

    public static byte[] readArray(ByteBuf input) {
        return readArray(input, input.readableBytes());
    }

    public static void writeArray(byte[] array, int start, int end, ByteBuf output) {
        writeVarInt(end - start, output);
        output.writeBytes(array, start, end);
    }

    public static void writeArray(byte[] array, ByteBuf output) {
        writeVarInt(array.length, output);
        output.writeBytes(array);
    }

    public static ByteBuf readBuffer(ByteBuf input, int limit) {
        int len = readVarInt(input);
        if (len > limit)
            throw new BufferOverflowException();
        return input.readBytes(len);
    }

    public static void writeBuffer(ByteBuf buf, ByteBuf output) {
        int readable = buf.readableBytes();
        output.ensureWritable(varIntSize(readable) + readable);
        writeVarInt(readable, output);
        int idx = buf.readerIndex();
        output.writeBytes(buf, idx, readable);
        buf.readerIndex(idx + readable);
    }

    public static void writeBuffer(ByteBuf buf, int start, int end, ByteBuf output) {
        writeVarInt(end - start, output);
        output.writeBytes(buf, start, end);
    }

    public static String readUtf8(ByteBuf input, int limit) {
        return readString0(input, limit, 4, StandardCharsets.UTF_8);
    }

    public static String readUtf8(ByteBuf input) {
        return readString0(input, 32767, 4, StandardCharsets.UTF_8);
    }

    public static void writeUtf8(String s, ByteBuf output) {
        writeArray(s.getBytes(StandardCharsets.UTF_8), output);
    }

    public static GameProfile readGameProfile(ByteBuf input, int nameLimit) {
        GameProfile profile = new GameProfile(readId(input), readUtf8(input, nameLimit));
        PropertyMap properties = profile.getProperties();
        int i = 0;
        for (int j = readVarInt(input); i < j; i++) {
            String name = readUtf8(input, 32767);
            String value = readUtf8(input, 32767);
            if (input.readBoolean()) {
                properties.put(name, new Property(name, value, readUtf8(input, 32767)));
            } else {
                properties.put(name, new Property(name, value));
            }
        }
        return profile;
    }

    public static GameProfile readGameProfile(ByteBuf input) {
        return readGameProfile(input, 16);
    }

    public static void writeGameProfile(GameProfile profile, ByteBuf output) {
        writeId(profile.getId(), output);
        writeUtf8(profile.getName(), output);
        Collection<Property> properties = profile.getProperties().values();
        writeVarInt(properties.size(), output);
        Iterator<Property> var3 = properties.iterator();
        while (var3.hasNext()) {
            Property property = var3.next();
            writeUtf8(property.getName(), output);
            writeUtf8(property.getValue(), output);
            if (property.hasSignature()) {
                output.writeBoolean(true);
                writeUtf8(property.getSignature(), output);
                continue;
            }
            output.writeBoolean(false);
        }
    }

    public static Date readDate(ByteBuf input) {
        return new Date(input.readLong());
    }

    public static void writeDate(Date date, ByteBuf output) {
        output.writeLong(date.getTime());
    }

    public static List<String> readUtf8s(ByteBuf input, int climit, int slimit) {
        int count = readVarInt(input);
        if (count > climit)
            throw new BufferOverflowException();
        ArrayList<String> list = new ArrayList(count);
        while (count-- > 0)
            list.add(readUtf8(input, slimit));
        return list;
    }

    public static void writeUtf8s(List<String> strings, ByteBuf output) {
        int j = strings.size();
        writeVarInt(j, output);
        if (j != 0)
            if (strings instanceof java.util.RandomAccess) {
                for (int i = 0; i < j; i++)
                    writeUtf8(strings.get(i), output);
            } else {
                Iterator<String> var5 = strings.iterator();
                while (var5.hasNext()) {
                    String str = var5.next();
                    writeUtf8(str, output);
                }
            }
    }

    public static int[] readVarIntArray(ByteBuf input, int[] into, int limit) {
        int count = readVarInt(input);
        if (count > limit)
            throw new BufferOverflowException();
        if (into == null)
            into = new int[count];
        for (int i = 0; i < count; i++)
            into[i] = readVarInt(input);
        return into;
    }

    public static int[] readVarIntArray(ByteBuf input, int limit) {
        return readVarIntArray(input, null, limit);
    }

    public static int[] readVarIntArray(ByteBuf input, int[] into) {
        return readVarIntArray(input, into, into.length);
    }

    public static int[] readVarIntArray(ByteBuf input) {
        return readVarIntArray(input, null, 2147483647);
    }

    public static void writeVarIntArray(int[] ints, int from, int to, ByteBuf output) {
        int count = to - from;
        output.ensureWritable((to - from) * 4);
        writeVarInt(count, output);
        while (from < to)
            writeVarInt(ints[from++], output);
    }

    public static void writeVarIntArray(int[] ints, ByteBuf output) {
        int j = ints.length;
        output.ensureWritable((j + 1) * 4);
        writeVarInt(j, output);
        for (int i = 0; i < j; i++)
            writeVarInt(ints[i], output);
    }

    public static long[] readLongArray(ByteBuf input, long[] into, int limit) {
        int count = readVarInt(input);
        if (count > limit)
            throw new BufferOverflowException();
        if (into == null)
            into = new long[count];
        for (int i = 0; i < count; i++)
            into[i] = input.readLong();
        return into;
    }

    public static long[] readLongArray(ByteBuf input, int limit) {
        return readLongArray(input, null, limit);
    }

    public static long[] readLongArray(ByteBuf input, long[] into) {
        return readLongArray(input, into, into.length);
    }

    public static long[] readLongArray(ByteBuf input) {
        return readLongArray(input, null, 2147483647);
    }

    public static void writeLongArray(long[] longs, int from, int to, ByteBuf output) {
        int count = to - from;
        output.ensureWritable(4 + count * 8);
        writeVarInt(count, output);
        while (from < to)
            output.writeLong(longs[from++]);
    }

    public static void writeLongArray(long[] longs, ByteBuf output) {
        int j = longs.length;
        output.ensureWritable(4 + j * 8);
        writeVarInt(j, output);
        for (int i = 0; i < j; i++)
            output.writeLong(longs[i]);
    }

    public static <T extends Enum<T>> T readEnum(T[] values, ByteBuf buf) {
        return values[readVarInt(buf)];
    }

    public static void writeEnum(Enum<?> value, ByteBuf buf) {
        writeVarInt(value.ordinal(), buf);
    }

    public static int varIntSize(int varint) {
        return ((varint & 0xFFFFFF80) == 0) ? 1 : (((varint & 0xFFFFC000) == 0) ? 2 : (((varint & 0xFFE00000) == 0) ? 3 : (((varint & 0xF0000000) == 0) ? 4 : 5)));
    }

    public static String hexDump(ByteBuf buf, int maxLen) {
        return ByteBufUtil.hexDump(buf, 0, Math.min(buf.writerIndex(), maxLen));
    }

    private static String readString0(ByteBuf input, int limit, int bytes, Charset charset) {
        int len = readVarInt(input);
        if (len > limit * bytes)
            throw new IllegalStateException("Cannot receive encoded string longer than " + (len * 4) + " (got " + len + " characters)");
        int idx = input.readerIndex();
        String s = input.toString(idx, len, charset);
        input.readerIndex(idx + len);
        int actual = s.length();
        if (actual > limit)
            throw new IllegalStateException("Cannot receive string longer than " + len + " (got " + actual + " characters)");
        return s;
    }

    public static void writeJson(ByteBuf buf, Object obj) {
        writeUtf8(GsonSerializer.toJson(obj), buf);
    }

    public static <T> T readJson(ByteBuf buf, Class<T> clazz) {
        return GsonSerializer.fromJson(readUtf8(buf), clazz);
    }
}
