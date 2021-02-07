/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

import java.io.*;
import java.util.Base64;
import java.util.zip.*;

public class Compression {

    /**
     * Compresses a String using the GZIP algorithm
     *
     * Using:
     * <p>
     * {@code //Compresses the String with gzip}
     * <p>
     * {@code String compressed = compress("Groooooooooooße Nachricht");}
     * <p>
     * <p>
     * {@code //Decompress the String}
     * <p>
     * {@code String decompressed = decompress(compressed);}
     * <p>
     * <p>
     * {@code System.out.println("Dekomprimiert: " + decompressed);}
     * <p>
     * <p>
     * @param toCompress The String to be compressed
     * @return The compressed String
     * @throws CompressionException If the string is not valid or something went
     * wrong during the compression
     */
    public static String compress(String toCompress) throws CompressionException {
        if (toCompress == null || toCompress.isEmpty()) {
            throw new CompressionException("The string to be decompressed is null or empty");
        }
        if (!isUTF8(toCompress)) {
            throw new CompressionException("The string to encrypt has to be in UTF-8");
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzos = null;
        try {
            gzos = new GZIPOutputStream(baos);
            gzos.write(toCompress.getBytes("UTF8"));
        } catch (Exception e) {
            throw new CompressionException("Could not compress the string: "+e.getMessage());
        } finally {
            if (gzos != null) {
                try {
                    gzos.close();
                } catch (Exception ex) {
                    throw new CompressionException("Could not finish the compression: "+ex.getMessage());
                }
            }
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Decompresses a String using the GZIP algorithm
     *
     * Using:
     * <p>
     * {@code //Compresses the String with gzip}
     * <p>
     * {@code String compressed = compress("Groooooooooooße Nachricht");}
     * <p>
     * <p>
     * {@code //Decompress the String}
     * <p>
     * {@code String decompressed = decompress(compressed);}
     * <p>
     * <p>
     * {@code System.out.println("Dekomprimiert: " + decompressed);}
     * <p>
     * <p>
     * @param toDecompress The String to be decompressed
     * @return The decompressed String
     * @throws CompressionException If the string is not valid or someting went
     * wrong during the decompression
     */
    public static String decompress(String toDecompress) throws CompressionException {
        if (toDecompress == null || toDecompress.isEmpty()) {
            throw new CompressionException("The string to be decompressed is null or empty");
        }
        InputStreamReader isr = null;
        try {
            byte[] input = Base64.getDecoder().decode(toDecompress);
            isr = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(input)));
            StringWriter sw = new StringWriter();
            char[] chars = new char[1024];
            for (int len; (len = isr.read(chars)) > 0;) {
                sw.write(chars, 0, len);
            }
            return sw.toString();
        } catch (Exception ex) {
            throw new CompressionException("Could not decompress string: " + ex.getMessage());
        } finally {
            try {
                isr.close();
            } catch (Exception ex) {
                throw new CompressionException("Could not finish the decompression by closing the gzipstream: " + ex.getMessage());
            }
        }
    }

    private static boolean isUTF8(String toCheck) {
        byte[] pText = toCheck.getBytes();
        int expectedLength;

        for (int i = 0; i < pText.length; i++) {
            if ((pText[i] & 0b10000000) == 0b00000000) {
                expectedLength = 1;
            } else if ((pText[i] & 0b11100000) == 0b11000000) {
                expectedLength = 2;
            } else if ((pText[i] & 0b11110000) == 0b11100000) {
                expectedLength = 3;
            } else if ((pText[i] & 0b11111000) == 0b11110000) {
                expectedLength = 4;
            } else if ((pText[i] & 0b11111100) == 0b11111000) {
                expectedLength = 5;
            } else if ((pText[i] & 0b11111110) == 0b11111100) {
                expectedLength = 6;
            } else {
                return false;
            }

            while (--expectedLength > 0) {
                if (++i >= pText.length) {
                    return false;
                }
                if ((pText[i] & 0b11000000) != 0b10000000) {
                    return false;
                }
            }
        }

        return true;
    }

    public static class CompressionException extends Exception {

        public CompressionException() {
            super();
        }

        public CompressionException(String message) {
            super(message);
        }

    }
}
