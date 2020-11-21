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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 数値の範囲 (from - to)を表現するクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class Range {

    /**
     * 開始値
     */
    @Getter
    private int from;

    /**
     * 終了値
     */
    @Getter
    private int to;

    /**
     * デフォルトコンストラクタ
     */
    private Range() {
    }

    /**
     * コンストラクタ
     *
     * @param from 開始値
     * @param to   終了値
     */
    private Range(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * 引数として渡された {@code from} と {@code to} の範囲を持つ {@link Range}
     * クラスの新しいインスタンスを生成し返却します。
     *
     * @param from 開始値
     * @param to   終了値
     * @return 引数として渡された {@code from} と {@code to} の範囲を持つ {@link Range}
     *         クラスの新しいインスタンス
     */
    public static Range of(int from, int to) {
        return new Range(from, to);
    }
}
