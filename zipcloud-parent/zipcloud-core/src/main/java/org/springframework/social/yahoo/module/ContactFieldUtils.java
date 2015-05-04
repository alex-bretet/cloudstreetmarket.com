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
 * Utility class to ease the retrieval of certain fields from a Contact object.
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
class ContactFieldUtils  {

    public static DateFieldValue getDateValue(Contact contact, FieldType fieldType) {
        DateField dateField = (DateField) getField(contact, fieldType);
        if (dateField != null) {
            return dateField.getValue();
        }
        return null;
    }

    public static NameFieldValue getNameValue(Contact contact) {
        NameField nameField = (NameField) getField(contact, FieldType.NAME);
        if (nameField != null) {
            return nameField.getValue();
        }
        return null;
    }

    public static AddressFieldValue getAddressValue(Contact contact) {
        AddressField addressField = (AddressField) getField(contact, FieldType.ADDRESS);
        if (addressField != null) {
            return addressField.getValue();
        }
        return null;
    }

    public static String getValueFromSingleValueField(Contact contact, FieldType fieldType) {
        return getValueFromSingleField(getField(contact, fieldType));
    }

    private static String getValueFromSingleField(Field field) {
        SingleValueField singleValueField = (SingleValueField) field;
        if (field != null) {
            return ((SingleFieldValue) singleValueField.getValue()).getValue();
        }
        return null;
    }

    private static Field getField(Contact contact, FieldType fieldType) {

        for (Field field : contact.getFields()) {
            if (field.getType().equals(fieldType)) {
                return field;
            }
        }
        return null;
    }
}
