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

/**
 * Each Field object is accompanied by a specified type.
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public enum FieldType {

    /**
     * The contacts GUID
     */
    GUID,
    /**
     * The contacts nickname
     */
    NICKNAME,
    /**
     * The contacts email
     */
    EMAIL,
    /**
     * Yahoo Messenger id
     */
    YAHOOID,
    /**
     * Other identifiers, distinguished by {@link FieldFlag} types
     */
    OTHERID,
    /**
     * Phone types, distinguished by {@link FieldFlag} types
     */
    PHONE,
    /**
     * The contacts job title
     */
    JOBTITLE,
    /**
     * The contacts company name
     */
    COMPANY,
    /**
     * Miscellaneous notes and comments about the contact
     */
    NOTES,
    /**
     * The link to the Contact resource, which can be directly requested
     */
    LINK,
    /**
     * A custom field created with Yahoo Address Book, Add a Custom Field dialog.
     * Personal note: I have been able to find any dialog to allow adding/editing of custom fields
     * in the current version of the Yahoo Mail Client
     */
    CUSTOM,
    /**
     * The contacts name
     */
    NAME,
    /**
     * The contacts postal address
     */
    ADDRESS,
    /**
     * The contacts birthday
     */
    BIRTHDAY,
    /**
     * The contacts anniversary
     */
    ANNIVERSARY
}
