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

package org.thinkit.neumann.catalog;

import org.thinkit.api.catalog.BiCatalog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 始め括弧を管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum OpenBracket implements BiCatalog<OpenBracket, String> {

    /**
     * 始め丸括弧
     */
    PARENTHESIS(0, Parenthesis.OPEN.getTag()),

    /**
     * 始め角括弧
     */
    BRACKET(1, Bracket.OPEN.getTag()),

    /**
     * 始め波括弧
     */
    CURLY(2, CurlyBracket.OPEN.getTag()),

    /**
     * 始め山括弧
     */
    ANGLE(3, AngleBracket.OPEN.getTag());

    /**
     * コード値
     */
    @Getter
    private final int code;

    /**
     * タグ
     */
    @Getter
    private final String tag;
}
