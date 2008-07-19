package com.thoughtworks.selenium.grid;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class IOHelperTest extends UsingClassMock {

    @Test
    public void closeWorksFineIfInputStreamIsNull() {
        IOHelper.close((InputStream) null);
    }

    @Test
    public void closeCallsCloseOnTheInputStreamWhenNotNull() {
        final Mock inputStream;

        inputStream = mock(InputStream.class);
        inputStream.expects("close");
        IOHelper.close((InputStream) inputStream);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void closeOnInputStreamDoNotThowExceptionIfCloseRaisesAnIOException() {
        final Mock inputStream;

        inputStream = mock(InputStream.class);
        inputStream.stubs("close").will(throwException(new IOException("fake IO exception")));
        IOHelper.close((InputStream) inputStream);
    }

    @Test
    public void closeWorksFineIfWriterIsNull() {
        IOHelper.close((Writer) null);
    }

    @Test
    public void closeCallsCloseOnTheWriterWhenNotNull() {
        final Mock writer;

        writer = mock(Writer.class);
        writer.expects("close");
        IOHelper.close((Writer) writer);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void closeOnWriterDoNotThowExceptionIfCloseRaisesAnIOException() {
        final Mock writer;

        writer = mock(Writer.class);
        writer.stubs("close").will(throwException(new IOException("fake IO exception")));
        IOHelper.close((Writer) writer);
    }

    @Test
    public void closeWorksFineIfReaderIsNull() {
        IOHelper.close((Reader) null);
    }

    @Test
    public void closeCallsCloseOnTheReaderWhenNotNull() {
        final Mock reader;

        reader = mock(Reader.class);
        reader.expects("close");
        IOHelper.close((Reader) reader);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void closeOnReaderDoNotThowExceptionIfCloseRaisesAnIOException() {
        final Mock reader;

        reader = mock(Reader.class);
        reader.stubs("close").will(throwException(new IOException("fake IO exception")));
        IOHelper.close((Reader) reader);
        IOHelper.close((Reader) reader);
    }

    @Test
    public void closeWorksFineIfOutputStreamIsNull() {
        IOHelper.close((OutputStream) null);
    }

    @Test
    public void closeCallsCloseOnTheOutputStreamWhenNotNull() {
        final Mock outputStream;

        outputStream = mock(OutputStream.class);
        outputStream.expects("close");
        IOHelper.close((OutputStream) outputStream);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void closeOnOutputStreamDoNotThowExceptionIfCloseRaisesAnIOException() {
        final Mock outputStream;

        outputStream = mock(OutputStream.class);
        outputStream.stubs("close").will(throwException(new IOException("fake IO exception")));
        IOHelper.close((OutputStream) outputStream);
    }

    @Test
    public void closeWorksFineIfSocketIsNull() {
        IOHelper.close((Socket) null);
    }

    @Test
    public void closeCallsCloseOnTheSocketWhenNotNull() {
        final Mock socket;

        socket = mock(Socket.class);
        socket.expects("close");
        IOHelper.close((Socket) socket);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void closeOnSocketDoNotThowExceptionIfCloseRaisesAnIOException() {
        final Mock socket;

        socket = mock(Socket.class);
        socket.stubs("close").will(throwException(new IOException("fake IO exception")));
        IOHelper.close((Socket) socket);
    }

    @Test
    public void copyStreamCopyFullContent() throws IOException {
        final ByteArrayInputStream inputStream;
        final StringWriter writer;

        inputStream = new ByteArrayInputStream("lines \n of content".getBytes());
        writer = new StringWriter();
        try {
          IOHelper.copyStream(inputStream, writer, 5);
            assertEquals("lines \n of content", writer.toString());
        } finally{
            IOHelper.close(inputStream);
        }
    }
}
