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

import java.util.List;

import org.springframework.social.yahoo.module.FieldType;

import static org.springframework.social.yahoo.filter.TokenUtils.*;

/**
 * Implementation of {@link ContactsRequestCustomizer} that builds a filter for the Contacts resource, such that only
 * the specified fields will be retrieved for each Contact, no matter if the Contact object has other non-empty fields.
 *
 * @see {@link ContactsFilter#displaySelectedFields(com.github.gabrielruiu.springsocial.yahoo.module.FieldType...)}
 * @see {@link ContactsFilter#displayAllFields()}
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class DisplayFilter extends ContactsRequestCustomizer {

    private static final String DISPLAY_FILTER_KEY = "out";

    public void addFields(FieldType... fieldTypes) {
        for (FieldType type : fieldTypes) {
            String fieldName = type.name().toLowerCase();
            addToken(new CustomizerToken(fieldName, null, fieldName));
        }
    }

    public void addAllFields() {
        getTokens().clear();
        addToken(new CustomizerToken(Display.ALL.name().toLowerCase(), null, Display.ALL.name().toLowerCase()));
    }

    @Override
    public boolean isFieldAllowed(String fieldName) {
        return true;
    }

    @Override
    public String toRequest() {
        StringBuilder sb = new StringBuilder();
        if (hasTokens()) {
            sb.append(DISPLAY_FILTER_KEY).append(SYMBOL_EQUALS);
            List<CustomizerToken> tokens = getTokens();
            for (CustomizerToken token : tokens) {
                sb.append(token.getValue());
                if (shouldAddTokenSeparator(tokens, token)) {
                    sb.append(SYMBOL_COMMA);
                }
            }
        }
        return sb.toString();
    }

    public static enum Display {
        ALL
    }
}
