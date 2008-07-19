package com.thoughtworks.selenium.grid;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class HttpParametersTest extends UsingClassMock {

    @Test
    public void byDefaultNoParameterIsSet() {
        assertNull(new HttpParameters().get("aParameter"));
    }

    @Test
    public void AParameterValueCanBeRetrievedAfterItIsSet() {
        final HttpParameters parameters;

        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        assertEquals("aValue", parameters.get("aParameter"));
    }

    @Test
    public void AParameterValueIsTheLastOneWhenSetMultipleTime() {
        final HttpParameters parameters;

        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        parameters.put("aParameter", "anotherValue");
        assertEquals("anotherValue", parameters.get("aParameter"));
    }

    @Test
    public void TwoParametersCanBeSetToDifferentValues() {
        final HttpParameters parameters;

        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        parameters.put("anotherParameter", "anotherValue");
        assertEquals("aValue", parameters.get("aParameter"));
        assertEquals("anotherValue", parameters.get("anotherParameter"));
    }

    @Test
    public void byDefaultNamesReturnsAnEmptySet() {
        assertTrue(new HttpParameters().names().isEmpty());
    }

    @Test
    public void namesReturnsAllParameterNamesWhenThereIsSome() {
        final HttpParameters parameters;
        final Set<String> names;

        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        parameters.put("anotherParameter", "anotherValue");
        names = parameters.names();
        assertTrue(names.contains("aParameter"));
        assertTrue(names.contains("anotherParameter"));
        assertEquals(2, names.size());
    }

    @Test
    public void toStringReturnsAnEmptyStringWhenNoParameterIsSet() {
        assertEquals("", new HttpParameters().toString());
    }

    @Test
    public void toStringReturnsARubyLikeHashWhenAParameterIsSet() {
        final HttpParameters parameters;
        
        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        assertEquals("aParameter => \"aValue\"", parameters.toString());
    }

    @Test
    public void toStringReturnsARubyLikeHashWhenMultipleParametersAreSet() {
        final HttpParameters parameters;

        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        parameters.put("anotherParameter", "anotherValue");
        assertEquals("aParameter => \"aValue\", anotherParameter => \"anotherValue\"",
                      parameters.toString());
    }


    @Test
    public void instanceInheritsMapSettingsWhenAMapIsPassedToTheConstructor() {
        final Map<String, String[]> aMap;
        final HttpParameters parameters;

        aMap = new HashMap<String,String[]>();
        aMap.put("aParameter", new String[] {"aValue"});
        aMap.put("anotherParameter", new String[] {"anotherValue"});

        parameters = new HttpParameters(aMap);
        assertEquals("aValue", parameters.get("aParameter"));
        assertEquals("anotherValue", parameters.get("anotherParameter"));
    }

    @Test
    public void onlyTheFirstParameterValueIsRelevantWhenAMapIsPassedToTheConstructor() {
        final Map<String, String[]> aMap;
        final HttpParameters parameters;

        aMap = new HashMap<String,String[]>();
        aMap.put("aParameter", new String[] {"aValue", "anotherValue"});

        parameters = new HttpParameters(aMap);
        assertEquals("aValue", parameters.get("aParameter"));
    }

}