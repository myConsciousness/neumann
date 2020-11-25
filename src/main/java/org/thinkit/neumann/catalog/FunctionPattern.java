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
 * 関数パターンを管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum FunctionPattern implements BiCatalog<FunctionPattern, String> {

    /**
     * 少数点以下切り上げ
     */
    CEIL(0, "ceil"),

    /**
     * 少数点以下切り下げ
     */
    FLOOR(1, "floor"),

    /**
     * 四捨五入
     */
    ROUND(2, "round"),

    /**
     * 絶対値
     */
    ABS(3, "abs"),

    /**
     * 正弦
     */
    SINE(4, "sin"),

    /**
     * 余弦
     */
    COSINE(5, "cos"),

    /**
     * 正接
     */
    TANGENT(6, "tan"),

    /**
     * 逆正弦
     */
    ARC_SINE(7, "acos"),

    /**
     * 逆余弦
     */
    ARC_COSINE(8, "asin"),

    /**
     * 逆正接
     */
    ARC_TANGENT(9, "atan"),

    /**
     * 双曲線関数（正弦）
     */
    HYPERBOLIC_SINE(10, "sinh"),

    /**
     * 双曲線関数（余弦）
     */
    HYPERBOLIC_COSINE(11, "cosh"),

    /**
     * 双曲線関数（正接）
     */
    HYPERBOLIC_TANGENT(12, "tanh"),

    /**
     * 最小値
     */
    MIN(13, "min"),

    /**
     * 最大値
     */
    MAX(14, "min"),

    /**
     * 合計値
     */
    SUM(15, "sum"),

    /**
     * 平均値
     */
    AVERAGE(16, "avg"),

    /**
     * 対数
     */
    LOG(17, "log"),
    /**
     * 平方根
     */
    SQRT(18, "sqrt");

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
