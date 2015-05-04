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
package edu.zipcloud.spring.social.yahoo.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;

import java.util.Date;
import java.util.Set;

/**
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
//TODO: add 'image' property
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonTypeResolver(YahooTypeResolver.class)
abstract class FieldMixin {

    @JsonProperty("id") int id;

    @JsonProperty("created") Date created;

    @JsonProperty("updated") Date updated;

    @JsonProperty("uri") String uri;

    @JsonProperty("isConnection") boolean isConnection;

    @JsonProperty("nickname") String nickname;

    @JsonProperty("title") String title;

    @JsonProperty("value") Object value;

    @JsonDeserialize(using = FieldTypeDeserializer.class)
    @JsonProperty("type") FieldType type;

    @JsonProperty("flags") Set<FieldFlag> flags;
}
