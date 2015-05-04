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
package edu.zipcloud.spring.social.yahoo.filter;

import static edu.zipcloud.spring.social.yahoo.filter.TokenUtils.*;
import edu.zipcloud.spring.social.yahoo.module.FieldType;

/**
 * Convenience class that unites all of the classes which are responsible for building request parameters which
 * either filter or sort Contact objects.
 * This can only be used when request the Contacts resource.
 *
 * @see {@link com.github.gabrielruiu.springsocial.yahoo.api.ContactsOperations#getContacts(ContactsFilter)}
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class ContactsFilter {

    private SearchFilter orSearchFilter = new SearchFilter(SYMBOL_COMMA);
    private SearchFilter andSearchFilter = new SearchFilter(SYMBOL_SEMICOLON);
    private SortFields sortFields = new SortFields();
    private SortOrder sortOrder = new SortOrder();
    private DisplayFilter displayFilter = new DisplayFilter();

    /**
     * Only those contacts which respect AT LEAST ONE of the conditions will be added to the response.
     * Can be combined with:
     *    {@link #withOrFilter(com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchableField, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withAndFilter(FieldType, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withAndFilter(com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchableField, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     */
    public ContactsFilter withOrFilter(FieldType fieldType, SearchFilter.SearchFilterConstraint key, String condition) {
        orSearchFilter.addField(fieldType, key, condition);
        return this;
    }

    /**
     * Only those contacts which respect AT LEAST ONE of the conditions will be added to the response.
     * Can be combined with:
     *    {@link #withOrFilter(FieldType, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withAndFilter(FieldType, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withAndFilter(com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchableField, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     */
    public ContactsFilter withOrFilter(SearchFilter.SearchableField field, SearchFilter.SearchFilterConstraint key, String condition) {
        orSearchFilter.addField(field, key, condition);
        return this;
    }

    /**
     * Only those contacts which respect ALL of the conditions will be added to the response.
     * Can be combined with:
     *    {@link #withAndFilter(com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchableField, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withOrFilter(FieldType, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withOrFilter(com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchableField, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     */
    public ContactsFilter withAndFilter(FieldType fieldType, SearchFilter.SearchFilterConstraint key, String condition) {
        andSearchFilter.addField(fieldType, key, condition);
        return this;
    }

    /**
     * Only those contacts which respect ALL of the conditions will be added to the response.
     * Can be combined with:
     *    {@link #withAndFilter(FieldType, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withOrFilter(FieldType, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     *    {@link #withOrFilter(com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchableField, com.github.gabrielruiu.springsocial.yahoo.filter.SearchFilter.SearchFilterConstraint, String)}
     */
    public ContactsFilter withAndFilter(SearchFilter.SearchableField field, SearchFilter.SearchFilterConstraint key, String condition) {
        andSearchFilter.addField(field, key, condition);
        return this;
    }

    /**
     * Only the specified fields are retrieved for each Contact object, no matter if there are other non-empty fields.
     */
    public ContactsFilter displaySelectedFields(FieldType... fieldTypes) {
        displayFilter.addFields(fieldTypes);
        return this;
    }

    /**
     * All fields from a Contact object, which have a non-empty value, are included in the response.
     */
    public ContactsFilter displayAllFields() {
        displayFilter.addAllFields();
        return this;
    }

    /**
     * Specifies by which fields the Contacts should be ordered; FieldType.NAME and FieldType.ADDRESS are not allowed
     * when sorting.
     * If you want to sort by name, use {@link com.github.gabrielruiu.springsocial.yahoo.filter.SortFields.SortableField}.
     * Can be combined with {@link #sortBy(com.github.gabrielruiu.springsocial.yahoo.filter.SortFields.SortableField...)}
     */
    public ContactsFilter sortBy(FieldType... fieldTypes) {
        sortFields.addFields(fieldTypes);
        return this;
    }

    /**
     * Specifies by which fields the Contacts should be ordered; all {@link com.github.gabrielruiu.springsocial.yahoo.filter.SortFields.SortableField}s are allowed.
     * Can be combined with {@link #sortBy(FieldType...)}
     */
    public ContactsFilter sortBy(SortFields.SortableField... sortableFields) {
        sortFields.addFields(sortableFields);
        return this;
    }

    /**
     * Order ascending or descending the Contacts.
     * Must be accompanied by calls to {@link #sortBy(FieldType...)} or {@link #sortBy(com.github.gabrielruiu.springsocial.yahoo.filter.SortFields.SortableField...)}
     */
    public ContactsFilter sortOrder(SortOrder.Order order) {
        sortOrder.setOrder(order);
        return this;
    }

    /**
     * Concatenates each of the request parameters built by each filter, which is then appended to the request for
     * the Contacts resource
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        if (orSearchFilter.hasTokens()) {
            sb.append(orSearchFilter.toRequest());
        }
        if (andSearchFilter.hasTokens()) {
            sb.append(SYMBOL_SEMICOLON);
            sb.append(andSearchFilter.toRequest());
        }
        if (displayFilter.hasTokens()) {
            sb.append(SYMBOL_SEMICOLON);
            sb.append(displayFilter.toRequest());
        }
        if (sortFields.hasTokens()) {
            sb.append(SYMBOL_SEMICOLON);
            sb.append(sortFields.toRequest());
        }
        if (sortOrder.hasTokens()) {
            sb.append(SYMBOL_SEMICOLON);
            sb.append(sortOrder.toRequest());
        }
        return sb.toString();
    }
}
