package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import junit.framework.Assert;
import org.junit.Test;


@SuppressWarnings({"ThrowableInstanceNeverThrown"})
public class RenderingExceptionTest {

    @Test
    public void captureOriginalExceptionWhenBuiltFromAThrowable() {
      final Throwable originalException = new Throwable() {};
      Assert.assertEquals(originalException, new RenderingException(originalException).getCause());
    }

    @Test
    public void defineMessageWhenBuiltFromAString() {
      Assert.assertEquals("a message", new RenderingException("a message").getMessage());
    }

}
