/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.yahoo.module;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class YahooTypeIdResolver extends TypeIdResolverBase {

    private Map<String, JavaType> javaTypeMappings;
    private MapperConfig<?> mapperConfig;

    public YahooTypeIdResolver(MapperConfig<?> mapperConfig) {
        this.mapperConfig = mapperConfig;
        this.javaTypeMappings = generateFieldValueMappings();
    }

    @Override
    public JavaType typeFromId(String id) {
        return javaTypeMappings.get(id);
    }

    public String idFromValue(Object value) {
        return null;
    }

    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return null;
    }

    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.NAME;
    }

    private Map<String, JavaType> generateFieldValueMappings() {
        Map<String, JavaType> mappings = new HashMap<String, JavaType>();
        mappings.put("address", mapperConfig.constructType(AddressField.class));
        mappings.put("name", mapperConfig.constructType(NameField.class));
        mappings.put("anniversary", mapperConfig.constructType(DateField.class));
        mappings.put("birthday", mapperConfig.constructType(DateField.class));
        mappings.put("guid", mapperConfig.constructType(SingleValueField.class));
        mappings.put("nickname", mapperConfig.constructType(SingleValueField.class));
        mappings.put("email", mapperConfig.constructType(SingleValueField.class));
        mappings.put("yahooid", mapperConfig.constructType(SingleValueField.class));
        mappings.put("otherid", mapperConfig.constructType(SingleValueField.class));
        mappings.put("phone", mapperConfig.constructType(SingleValueField.class));
        mappings.put("jobTitle", mapperConfig.constructType(SingleValueField.class));
        mappings.put("company", mapperConfig.constructType(SingleValueField.class));
        mappings.put("notes", mapperConfig.constructType(SingleValueField.class));
        mappings.put("link", mapperConfig.constructType(SingleValueField.class));
        mappings.put("custom", mapperConfig.constructType(SingleValueField.class));
        return mappings;
    }
}
