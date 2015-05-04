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
package org.springframework.social.yahoo.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.yahoo.module.FieldType;

import static org.springframework.social.yahoo.filter.TokenUtils.*;

/**
 * When sorting a Contacts resource, the order, in which the Contact objects appear, can be specified
 * through one or more Fields.
 *
 * Whether the order is ascending or descending, is specified through the SortOrder class.
 *
 * @see <a href="https://developer.yahoo.com/social/rest_api_guide/sorting.html">Sorting</a>
 * @see {@link ContactsFilter#sortBy(FieldType...)}
 * @see {@link ContactsFilter#sortBy(SortableField...)}
 * @see {@link SortOrder}
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class SortFields extends ContactsRequestCustomizer {

    private static final String SORT_FIELDS_KEY = "sort-fields";
    private static final List<String> SORTABLE_FIELDS = sortableFields();

    public void addFields(FieldType... fieldTypes) {
        for (FieldType type : fieldTypes) {
            String fieldName = type.name().toLowerCase();
            addToken(new CustomizerToken(fieldName, null, fieldName));
        }
    }

    public void addFields(SortableField... sortableFields) {
        for (SortableField field : sortableFields) {
            addToken(new CustomizerToken(field.getParameterName(), null, field.getParameterName()));
        }
    }

    /**
     * The 'sort-fields' key can consist of any of Field Types except the structured fields Name
     * and Address. Use displayName, first or last to sort by name.
     */
    @Override
    public boolean isFieldAllowed(String fieldName) {
        return SORTABLE_FIELDS.contains(fieldName);
    }

    @Override
    public String toRequest() {
        StringBuilder sb = new StringBuilder();
        if (hasTokens()) {
            sb.append(SORT_FIELDS_KEY).append(SYMBOL_EQUALS);
            List<CustomizerToken> tokens = getTokens();
            for (CustomizerToken token : getTokens()) {
                sb.append(token.getValue());
                if (shouldAddTokenSeparator(tokens, token)) {
                    sb.append(SYMBOL_COMMA);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Verify that, only SortableField and FieldTypes(except NAME or ADDRESS) can be sorted by
     */
    private static List<String> sortableFields() {
        List<String> sortableFields = new ArrayList<String>();
        for (FieldType type : FieldType.values()) {
            if (!(type.equals(FieldType.NAME) || type.equals(FieldType.ADDRESS))) {
                sortableFields.add(type.name().toLowerCase());
            }
        }
        for (SortableField sortableField : SortableField.values()) {
            sortableFields.add(sortableField.getParameterName());
        }
        return sortableFields;
    }


    /**
     * Sort-specific field names by which a sort can be performed
     */
    public static enum SortableField {

        DISPLAY_NAME("displayName"),
        FIRST_NAME("first"),
        LAST_NAME("last");

        private String parameterName;

        SortableField(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }
}

