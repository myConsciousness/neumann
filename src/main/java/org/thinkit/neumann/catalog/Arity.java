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

import org.thinkit.api.catalog.Catalog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * オペランドの個数（アリティ）を管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum Arity implements Catalog<Arity> {

    /**
     * 零項
     */
    NULLARY(0),

    /**
     * 単項
     */
    UNARY(1),

    /**
     * 二項
     */
    BINARY(2),

    /**
     * 三項
     */
    TERNARY(3),

    /**
     * 多項
     */
    MULTIARY(4),

    /**
     * n-項
     */
    FINITARY(5);

    /**
     * コード値
     */
    @Getter
    private final int code;
}
