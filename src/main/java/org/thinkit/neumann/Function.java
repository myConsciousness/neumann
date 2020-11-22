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

import java.io.Serializable;

import org.thinkit.neumann.catalog.Arity;
import org.thinkit.neumann.catalog.FunctionPattern;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * 関数を表現するクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class Function implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 8800770035344657343L;

    /**
     * 関数パターン
     */
    @Getter(AccessLevel.PROTECTED)
    private FunctionPattern functionPattern;

    /**
     * 引数の数
     */
    @Getter(AccessLevel.PROTECTED)
    private Arity arity;

    /**
     * デフォルトコンストラクタ
     */
    private Function() {
    }

    /**
     * コンストラクタ
     *
     * @param functionPattern 関数パターン
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private Function(@NonNull FunctionPattern functionPattern) {
        this.functionPattern = functionPattern;

        if (functionPattern == FunctionPattern.MAX || functionPattern == FunctionPattern.MIN
                || functionPattern == FunctionPattern.SUM || functionPattern == FunctionPattern.AVERAGE) {
            this.arity = Arity.FINITARY;
        } else {
            this.arity = Arity.UNARY;
        }
    }

    /**
     * 引数として渡された関数パターンに対応する新しい {@link Function} クラスのインスタンスを生成し返却します。
     *
     * @param functionPattern 関数パターン
     * @return 引数として渡された関数パターンに対応する新しい {@link Function} クラスのインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static Function of(@NonNull FunctionPattern functionPattern) {
        return new Function(functionPattern);
    }
}
