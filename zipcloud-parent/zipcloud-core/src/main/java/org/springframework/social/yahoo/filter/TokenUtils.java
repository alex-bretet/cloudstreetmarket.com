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

/**
 * Utility class for helping with general operations with tokens
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class TokenUtils {

    public static final String SYMBOL_COMMA = ",";
    public static final String SYMBOL_SEMICOLON = ";";
    public static final String SYMBOL_EQUALS = "=";
    public static final String SYMBOL_PERIOD = ".";

    private TokenUtils() {
    }

    /**
     * Determines, when traversing and serializing a list of tokens, if the current token is not the last
     * one, and a separator can be added;
     */
    public static boolean shouldAddTokenSeparator(List<CustomizerToken> tokens, CustomizerToken token) {
        if (!tokens.contains(token)) {
            throw new IllegalArgumentException("Token must be part of token list");
        }
        return tokens.size() > 1 && !token.equals(tokens.get(tokens.size() - 1));
    }
}
