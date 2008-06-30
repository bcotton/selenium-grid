package com.thoughtworks.selenium.grid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * Band-aid for painful Java IO API.
 */
public class IOHelper {

    private static final Log LOGGER = LogFactory.getLog(IOHelper.class);

    /**
     * Copy remaining stream content to another stream.
     *
     * @param in             Input stream to copy (remaining) content from. Cannot be null.
     * @param out            Output stream to copy content to. Cannot be null.
     * @param copyBufferSize Size of the maximum chunk of data that will be copied in one step. A buffer a this
     *                       size will be allocated internally so beware of the usual speed vs. memory tradeoff.
     *                       Must be strictly positive.
     * @throws java.io.IOException on IO error.
     */
    public static void copyStream(InputStream in, Writer out, int copyBufferSize) throws IOException {
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(in);
            copyStream(reader, out, copyBufferSize);
        } finally {
            close(reader);
        }
        out.flush();
    }

    /**
     * Copy remaining stream content to another stream.
     *
     * @param in             Reader to copy (remaining) content from. Cannot be null.
     * @param out            Output stream to copy content to. Cannot be null.
     * @param copyBufferSize Size of the maximum chunk of data that will be copied in one step. A buffer a this
     *                       size will be allocated internally so beware of the usual speed vs. memory tradeoff.
     *                       Must be strictly positive.
     * @throws java.io.IOException on IO error.
     */
    public static void copyStream(Reader in, Writer out, int copyBufferSize) throws IOException {
        final char[] buffer;
        int bytesRead;

        buffer = new char[copyBufferSize];
        while (in.ready()) {
            bytesRead = in.read(buffer);
            if (bytesRead < 0) {    /* End of stream */
                break;
            }
            out.write(buffer, 0, bytesRead);
        }
        out.flush();
    }

    /**
     * Safely close an input stream  without bothering about null or IOExceptions.
     *
     * @param is InputStream to close. Can be null.
     */
    public static void close(InputStream is) {
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing input stream '" + is + "'", e);
            }
        }
    }

    /**
     * Safely close a Reader without bothering about null or IOExceptions.
     *
     * @param reader Reader to close. Can be null.
     */
    public static void close(Reader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing reader '" + reader + "'", e);
            }
        }
    }


    /**
     * Safely close a Writer without bothering about null or IOExceptions.
     *
     * @param writer Writer to close. Can be null.
     */
    public static void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing writer stream '" + writer + "'", e);
            }
        }
    }

    /**
     * Safely close an output stream  without bothering about null or IOExceptions.
     *
     * @param outputStream OutputStream to close. Can be null.
     */
    public static void close(OutputStream outputStream) {
        if (null != outputStream) {
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing input stream '" + outputStream + "'", e);
            }
        }
    }

    /**
     * Safely close an output stream  without bothering about null or IOExceptions.
     *
     * @param socket OutputStream to close. Can be null.
     */
    public static void close(Socket socket) {
        if (null != socket) {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing input stream '" + socket + "'", e);
            }
        }
    }

}
