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
 * 終わり括弧を管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum CloseBracket implements BiCatalog<CloseBracket, String> {

    /**
     * 終わり丸括弧
     */
    PARENTHESIS(0, Parenthesis.CLOSE.getTag()),

    /**
     * 終わり角括弧
     */
    BRACKET(1, Bracket.CLOSE.getTag()),

    /**
     * 終わり波括弧
     */
    CURLY(2, CurlyBracket.CLOSE.getTag()),

    /**
     * 終わり山括弧
     */
    ANGLE(3, AngleBracket.CLOSE.getTag());

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
