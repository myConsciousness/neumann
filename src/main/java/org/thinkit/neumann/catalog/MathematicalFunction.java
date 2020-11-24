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
import org.thinkit.neumann.Function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数学関数を管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum MathematicalFunction implements BiCatalog<MathematicalFunction, Function> {

    /**
     * 少数点以下切り上げ
     */
    CEIL(0, Function.of(FunctionPattern.CEIL)),

    /**
     * 少数点以下切り下げ
     */
    FLOOR(1, Function.of(FunctionPattern.FLOOR)),

    /**
     * 四捨五入
     */
    ROUND(2, Function.of(FunctionPattern.ROUND)),

    /**
     * 絶対値
     */
    ABS(3, Function.of(FunctionPattern.ABS)),

    /**
     * 正弦
     */
    SINE(4, Function.of(FunctionPattern.SINE)),

    /**
     * 余弦
     */
    COSINE(5, Function.of(FunctionPattern.COSINE)),

    /**
     * 正接
     */
    TANGENT(6, Function.of(FunctionPattern.TANGENT)),

    /**
     * 逆正弦
     */
    ARC_SINE(7, Function.of(FunctionPattern.ARC_SINE)),

    /**
     * 逆余弦
     */
    ARC_COSINE(8, Function.of(FunctionPattern.ARC_COSINE)),

    /**
     * 逆正接
     */
    ARC_TANGENT(9, Function.of(FunctionPattern.ARC_TANGENT)),

    /**
     * 双曲線関数（正弦）
     */
    HYPERBOLIC_SINE(10, Function.of(FunctionPattern.HYPERBOLIC_SINE)),

    /**
     * 双曲線関数（余弦）
     */
    HYPERBOLIC_COSINE(11, Function.of(FunctionPattern.HYPERBOLIC_COSINE)),

    /**
     * 双曲線関数（正接）
     */
    HYPERBOLIC_TANGENT(12, Function.of(FunctionPattern.HYPERBOLIC_TANGENT)),

    /**
     * 最小値
     */
    MIN(13, Function.of(FunctionPattern.MIN)),

    /**
     * 最大値
     */
    MAX(14, Function.of(FunctionPattern.MAX)),

    /**
     * 合計値
     */
    SUM(15, Function.of(FunctionPattern.SUM)),

    /**
     * 平均値
     */
    AVERAGE(16, Function.of(FunctionPattern.AVERAGE)),

    /**
     * 対数
     */
    LOG(17, Function.of(FunctionPattern.LOG)),

    /**
     * 平方根
     */
    SQRT(18, Function.of(FunctionPattern.SQRT));

    /**
     * コード値
     */
    @Getter
    private final int code;

    /**
     * タグ
     */
    @Getter
    private final Function tag;
}
