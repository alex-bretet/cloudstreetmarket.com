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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class ContactMixin extends YahooObjectMixin {

    @JsonProperty("id") int id;

    @JsonProperty("created") Date created;

    @JsonProperty("updated") Date updated;

    @JsonProperty("uri") String uri;

    @JsonProperty("isConnection") boolean isConnection;

    @JsonProperty("fields") List<Field> fields;

    @JsonProperty("categories") List<Category> categories;

    @JsonIgnore abstract String getEmail();
    @JsonIgnore abstract String getGuid();
    @JsonIgnore abstract String getYahooId();
    @JsonIgnore abstract String getOtherId();
    @JsonIgnore abstract String getCompany();
    @JsonIgnore abstract String getNickName();
    @JsonIgnore abstract String getPhone();
    @JsonIgnore abstract String getJobTitle();
    @JsonIgnore abstract String getNotes();
    @JsonIgnore abstract String getLink();
    @JsonIgnore abstract String getCustom();
    @JsonIgnore abstract DateFieldValue getBirthday();
    @JsonIgnore abstract DateFieldValue getAnniversary();
    @JsonIgnore abstract NameFieldValue getName();
    @JsonIgnore abstract AddressFieldValue getAddress();
}
