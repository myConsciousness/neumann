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

import java.math.BigDecimal;
import java.util.Iterator;

import org.thinkit.neumann.catalog.MathematicalConstant;
import org.thinkit.neumann.catalog.MathematicalFunction;
import org.thinkit.neumann.catalog.MathematicalOperator;

import lombok.NonNull;

/**
 * 式の評価を抽象化したインターフェースです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
public interface Evaluator {

    /**
     * 評価対象の式を返却します。
     *
     * @return 評価対象の式
     */
    public String getExpression();

    /**
     * 引数として渡された {@link MathematicalConstant} に対応する数学定数の評価を行います。
     * <p>
     * {@link Evaluator} インターフェースを実装する具象クラスは必ずこの
     * {@link #evaluate(MathematicalConstant)} メソッドを実装してください。
     *
     * @param constant {@link MathematicalConstant} で表現される数学定数
     * @return {@link MathematicalConstant} に対応する数学定数の評価結果
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public BigDecimal evaluate(@NonNull MathematicalConstant constant);

    /**
     * 引数として渡された {@link MathematicalOperator} に対応する数学演算子の評価を行います。
     * <p>
     * {@link Evaluator} インターフェースを実装する具象クラスは必ずこの
     * {@link #evaluate(MathematicalOperator)} メソッドを実装してください。
     *
     * @param operator {@link MathematicalOperator} で表現される数学演算子
     * @param operands 被演算子のイテレータ
     * @return {@link MathematicalOperator} に対応する数学演算子の評価結果
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public BigDecimal evaluate(@NonNull MathematicalOperator operator, @NonNull Iterator<BigDecimal> operands);

    /**
     * 引数として渡された {@link MathematicalFunction} に対応する数学関数の評価を行います。
     * <p>
     * {@link Evaluator} インターフェースを実装する具象クラスは必ずこの
     * {@link #evaluate(MathematicalFunction)} メソッドを実装してください。
     *
     * @param function  {@link MathematicalFunction} で表現される数学関数
     * @param arguments 数学関数に渡す引数のイテレータ
     * @return {@link MathematicalFunction} に対応する数学関数の評価結果
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public BigDecimal evaluate(@NonNull MathematicalFunction function, @NonNull Iterator<BigDecimal> arguments);
}
