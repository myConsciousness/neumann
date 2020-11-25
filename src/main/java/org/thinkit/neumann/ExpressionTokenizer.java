/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.neumann;

import java.util.StringTokenizer;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
final class ExpressionTokenizer {

    /**
     * 式の正規表現パターン
     */
    private static final String REGEX_PATTERN = "[()-+*/^% ]";

    private StringTokenizer tokenizer;

    private ExpressionTokenizer(@NonNull String expression) {
        this.tokenizer = new StringTokenizer(expression, REGEX_PATTERN, true);
    }

    public static ExpressionTokenizer of(@NonNull String expression) {
        return new ExpressionTokenizer(expression);
    }

    public boolean hasMoreTokens() {
        return this.tokenizer.hasMoreTokens();
    }

    public String nextToken() {
        return this.tokenizer.nextToken().trim();
    }
}
