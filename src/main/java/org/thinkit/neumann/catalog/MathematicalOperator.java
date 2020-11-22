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
import org.thinkit.neumann.Operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数学演算子を管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum MathematicalOperator implements BiCatalog<MathematicalOperator, Operator> {

    /**
     * 反転
     */
    NEGATE(0, Operator.of(OperatorPattern.NEGATE)),

    /**
     * 減算
     */
    MINUS(1, Operator.of(OperatorPattern.MINUS)),

    /**
     * 加算
     */
    PLUS(2, Operator.of(OperatorPattern.PLUS)),

    /**
     * 乗算
     */
    MULTIPLY(3, Operator.of(OperatorPattern.MULTIPLY)),

    /**
     * 除算
     */
    DIVIDE(4, Operator.of(OperatorPattern.DIVIDE)),

    /**
     * 指数
     */
    EXPONENT(5, Operator.of(OperatorPattern.EXPONENT)),

    /**
     * 剰余
     */
    MODULO(6, Operator.of(OperatorPattern.MODULO));

    /**
     * コード値
     */
    @Getter
    private final int code;

    /**
     * タグ
     */
    @Getter
    private final Operator tag;
}
