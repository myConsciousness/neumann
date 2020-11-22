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
import org.thinkit.neumann.catalog.OperatorAssociativity;
import org.thinkit.neumann.catalog.OperatorPattern;
import org.thinkit.neumann.catalog.OperatorPrecedence;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * 演算子を表現するクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class Operator implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = -4212700130452217046L;

    /**
     * 演算子パターン
     */
    @Getter(AccessLevel.PROTECTED)
    private OperatorPattern operatorPattern;

    /**
     * 引数の数
     */
    @Getter(AccessLevel.PROTECTED)
    private Arity arity;

    /**
     * 演算子の結合則
     */
    @Getter(AccessLevel.PROTECTED)
    private OperatorAssociativity associativity;

    /**
     * 演算子の優先順位
     */
    @Getter(AccessLevel.PROTECTED)
    private OperatorPrecedence precedence;

    /**
     * デフォルトコンストラクタ
     */
    private Operator() {
    }

    /**
     * コンストラクタ
     *
     * @param operatorPattern 演算子パターン
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private Operator(@NonNull OperatorPattern operatorPattern) {
        this.operatorPattern = operatorPattern;

        switch (operatorPattern) {
            case NEGATE -> {
                this.arity = Arity.UNARY;
                this.associativity = OperatorAssociativity.RIGHT;
                this.precedence = OperatorPrecedence.NEGATE;
            }

            case MINUS -> {
                this.arity = Arity.BINARY;
                this.associativity = OperatorAssociativity.LEFT;
                this.precedence = OperatorPrecedence.MINUS;
            }

            case PLUS -> {
                this.arity = Arity.BINARY;
                this.associativity = OperatorAssociativity.LEFT;
                this.precedence = OperatorPrecedence.PLUS;
            }

            case MULTIPLY -> {
                this.arity = Arity.BINARY;
                this.associativity = OperatorAssociativity.LEFT;
                this.precedence = OperatorPrecedence.MULTIPLY;
            }

            case DIVIDE -> {
                this.arity = Arity.BINARY;
                this.associativity = OperatorAssociativity.LEFT;
                this.precedence = OperatorPrecedence.DIVIDE;
            }

            case EXPONENT -> {
                this.arity = Arity.BINARY;
                this.associativity = OperatorAssociativity.LEFT;
                this.precedence = OperatorPrecedence.EXPONENT;
            }

            case MODULO -> {
                this.arity = Arity.BINARY;
                this.associativity = OperatorAssociativity.LEFT;
                this.precedence = OperatorPrecedence.MODULO;
            }
        }
    }

    /**
     * 引数として渡された演算子パターンに対応する新しい {@link Operator} クラスの新しいインスタンスを生成し返却します。
     *
     * @param operatorPattern 演算子パターン
     * @return 引数として渡された演算子パターンに対応する新しい {@link Operator} クラスの新しいインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static Operator of(@NonNull OperatorPattern operatorPattern) {
        return new Operator(operatorPattern);
    }
}
