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
 * 数学関数を管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum MathematicalFunction implements Catalog<MathematicalFunction> {

    /**
     * 少数点以下切り上げ
     */
    CEIL(0),

    /**
     * 少数点以下切り下げ
     */
    FLOOR(1),

    /**
     * 四捨五入
     */
    ROUND(2),

    /**
     * 絶対値
     */
    ABS(3),

    /**
     * 正弦
     */
    SINE(4),

    /**
     * 余弦
     */
    COSINE(5),

    /**
     * 正接
     */
    TANGENT(6),

    /**
     * 逆正弦
     */
    ARC_SINE(7),

    /**
     * 逆余弦
     */
    ARC_COSINE(8),

    /**
     * 逆正接
     */
    ARC_TANGENT(9),

    /**
     * 双曲線関数（正弦）
     */
    HYPERBOLIC_SINE(10),

    /**
     * 双曲線関数（余弦）
     */
    HYPERBOLIC_COSINE(11),

    /**
     * 双曲線関数（正接）
     */
    HYPERBOLIC_TANGENT(12),

    /**
     * 最小値
     */
    MIN(13),

    /**
     * 最大値
     */
    MAX(14),

    /**
     * 合計値
     */
    SUM(15),

    /**
     * 平均値
     */
    AVERAGE(16),

    /**
     * 自然対数
     */
    LN(17),

    /**
     * 常用対数
     */
    LOG(18),

    /**
     * 乱数
     */
    RANDOM(19);

    /**
     * コード値
     */
    @Getter
    private final int code;
}
