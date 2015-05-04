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

/**
 * When sorting a Contacts resource, the order, in which the Contact objects appear, can be specified
 * through one or more Fields.
 *
 * Whether the order is ascending or descending, is specified through the SortOrder class.
 *
 * @see <a href="https://developer.yahoo.com/social/rest_api_guide/sorting.html">Sorting</a>
 * @see {@link ContactsFilter#sortOrder(Order))
 * @see {@link SortFields}
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class SortOrder extends ContactsRequestCustomizer {

    private static final String SORT_ORDER_KEY = "sort";

    public void setOrder(Order order) {
        getTokens().clear();
        String fieldName = order.name().toLowerCase();
        addToken(new CustomizerToken(fieldName, null, fieldName));
    }

    @Override
    public boolean isFieldAllowed(String fieldName) {
        return fieldName.equals(Order.ASC.name().toLowerCase()) ||
               fieldName.equals(Order.DESC.name().toLowerCase());
    }

    @Override
    public String toRequest() {
        StringBuilder sb = new StringBuilder();
        if (hasTokens()) {
            sb.append(SORT_ORDER_KEY).append(TokenUtils.SYMBOL_EQUALS);
            sb.append(getTokens().get(0).getValue());
        }
        return sb.toString();
    }

    public static enum Order {
        ASC, DESC
    }
}
