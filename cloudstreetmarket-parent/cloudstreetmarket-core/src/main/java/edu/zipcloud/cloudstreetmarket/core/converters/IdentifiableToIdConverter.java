package edu.zipcloud.cloudstreetmarket.core.converters;
import java.lang.reflect.Field;

import org.springframework.hateoas.Identifiable;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class IdentifiableToIdConverter implements Converter {
	
	    private final Class type;

	    /**
	     * Creates a new ToAttributedValueConverter instance.
	     * 
	     * @param mapper the mapper in use
	     * @param reflectionProvider the reflection provider in use
	     * @param lookup the converter lookup in use
	     * @param valueFieldName the field defining the tag's value (may be null)
	     */
	    public IdentifiableToIdConverter(
	        final Class type, final Mapper mapper, final ReflectionProvider reflectionProvider,
	        final ConverterLookup lookup, final String valueFieldName) {
	        this(type, mapper, reflectionProvider, lookup, valueFieldName, null);
	    }

	    /**
	     * Creates a new ToAttributedValueConverter instance.
	     * 
	     * @param mapper the mapper in use
	     * @param reflectionProvider the reflection provider in use
	     * @param lookup the converter lookup in use
	     * @param valueFieldName the field defining the tag's value (may be null)
	     * @param valueDefinedIn the type defining the field
	     */
	    public IdentifiableToIdConverter(
	        final Class<Identifiable<?>> type, final Mapper mapper, final ReflectionProvider reflectionProvider,
	        final ConverterLookup lookup, final String valueFieldName, Class valueDefinedIn) {
	        this.type = type;

            Field field = null;
            try {
                field = (valueDefinedIn != null ? valueDefinedIn : type.getSuperclass())
                    .getDeclaredField("id");
               
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(e.getMessage() + ": " + valueFieldName);
            }
	        
	    }

	    public boolean canConvert(final Class type) {
	        return type.isAssignableFrom(this.type);
	    }

	    public void marshal(final Object source, final HierarchicalStreamWriter writer,
	        final MarshallingContext context) {
	        if(source instanceof Identifiable){
	        	writer.setValue(((Identifiable<?>)source).getId().toString());
	        }
	    }

	    public Object unmarshal(final HierarchicalStreamReader reader,
	        final UnmarshallingContext context) {
	    	return null;
	    }
}
