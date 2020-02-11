/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import ca.weblite.tools.ddd.Property.Name;
import com.codename1.ui.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author shannah
 */
public class ContentType<T> {
    
    private static List<DataTransformer> registeredTransformers = new ArrayList<>();

    /**
     * @return the representationClass
     */
    public Class<T> getRepresentationClass() {
        return representationClass;
    }

    /**
     * @return the name
     */
    public Name getName() {
        return name;
    }
    private Class<T> representationClass;
    private Name name;
    
    public ContentType(Name name, Class<T> representationClass) {
        this.name = name;
        this.representationClass = representationClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ContentType t = (ContentType)obj;
        return Objects.equals(t.name, name) && Objects.equals(t.representationClass, representationClass);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.representationClass);
        hash = 47 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    
    
    public <V> V to(ContentType<V> otherType, T data) {
        if (this.equals(otherType)) {
            return (V)data;
        }
        throw new IllegalArgumentException("Cannot convert type "+this+" to type "+otherType);
    } 
    
    public boolean canConvertTo(ContentType otherType) {
        if (this.equals(otherType)) {
            return true;
        }
        return false;
    }
    
    public <V> T from(ContentType<V> otherType, V data) {
        if (this.equals(otherType)) {
            return (T)data;
        }
        throw new IllegalArgumentException("Cannot convert type "+this+" from type "+otherType);
    }
    
    public boolean canConvertFrom(ContentType otherType) {
        if (this.equals(otherType)) {
            return true;
        }
        return false;
    }
    
    public static <T,V> T convert(ContentType<V> sourceType, V sourceData, ContentType<T> targetType) {
        if (targetType.canConvertFrom(sourceType)) {
            return targetType.from(sourceType, sourceData);
        } else if (sourceType.canConvertTo(targetType)) {
            return sourceType.to(targetType, sourceData);
        } else {
            for (DataTransformer dt : registeredTransformers) {
                if (dt.supports(sourceType, targetType)) {
                    return (T)dt.transform(sourceType, targetType, sourceData);
                }
            }
        }
        throw new IllegalArgumentException("No supported conversions from "+sourceType+" to "+targetType);
    }
    
    
    
    public static final ContentType<String> Text = new ContentType<String>(new Name("text/plain;java.lang.String"), String.class) {
        
        @Override
        public <V> V to(ContentType<V> otherType, String data) {
            if (super.canConvertTo(otherType)) {
                return super.to(otherType, data);
            }
            data = data.trim();
            Class cls = otherType.getRepresentationClass();
            
            if (cls == Integer.class) {
                return (V)(Integer)Integer.parseInt(data);
            }
            if (cls == Double.class) {
                return (V)(Double)Double.parseDouble(data);
            }
            if (cls == Float.class) {
                return (V)(Float)Float.parseFloat(data);
            }
            if (cls == Boolean.class) {
                return (V)(Boolean)Boolean.parseBoolean(data);
            }
            
            return super.to(otherType, data);
            
        }

        @Override
        public boolean canConvertTo(ContentType otherType) {
            Class cls = otherType.getRepresentationClass();
            return super.canConvertTo(otherType) ||
                    cls == Integer.class ||
                    cls == Double.class ||
                    cls == Float.class ||
                    cls == Boolean.class;
                    
        }

        @Override
        public <V> String from(ContentType<V> otherType, V data) {
            if (super.canConvertFrom(otherType)) {
                return super.from(otherType, data);
            }
            Class cls = otherType.getRepresentationClass();
            if (cls == Integer.class || cls == Double.class || cls == Float.class || cls == Boolean.class) {
                return String.valueOf(data);
            }
            
            return super.from(otherType, data);
            
            
        }

        @Override
        public boolean canConvertFrom(ContentType otherType) {
            Class cls = otherType.getRepresentationClass();
            return super.canConvertFrom(otherType) ||
                    cls == Integer.class ||
                    cls == Double.class ||
                    cls == Float.class ||
                    cls == Boolean.class;
        }
        
 
    };
    
    public static final ContentType<Boolean> BooleanType = new ContentType<Boolean>(new Name("Boolean"), Boolean.class) {
        
    };
    
    public static final ContentType<Integer> IntegerType = new ContentType<Integer>(new Name("Integer"), Integer.class) {
        
    };
    
    public static final ContentType<Float> FloatType = new ContentType<Float>(new Name("Float"), Float.class) {};
    public static final ContentType<Double> DoubleType = new ContentType<Double>(new Name("Double"), Double.class){};
    public static final ContentType<Entity> EntityType = new ContentType<Entity>(new Name("Entity"), Entity.class){};
    public static final ContentType<EntityList> EntityListType = new ContentType<EntityList>(new Name("EntityList"), EntityList.class);
    public static <V> ContentType<V> createObjectType(Class<V> representationClass) {
        return new ContentType<V>(new Name(representationClass.getName()), representationClass) {
            @Override
            public boolean equals(Object obj) {
                return obj.getClass() == this.getClass() && ((ContentType)obj).getRepresentationClass() == representationClass;
                
            }

            @Override
            public int hashCode() {
                return representationClass.hashCode();
            }
            
            
            
        };
    }
    
    
    
            
   
    
    
}
