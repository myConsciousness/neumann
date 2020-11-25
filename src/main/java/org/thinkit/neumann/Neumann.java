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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;

import org.thinkit.neumann.catalog.MathematicalConstant;
import org.thinkit.neumann.catalog.MathematicalFunction;
import org.thinkit.neumann.catalog.MathematicalOperator;

import ch.obermuhlner.math.big.BigDecimalMath;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class Neumann implements Evaluator {

    /**
     * 演算規則
     */
    private static final MathContext MATH_CONTEXT = new MathContext(20);

    /**
     * 評価する式
     */
    @Getter
    private String expression;

    /**
     * デフォルトコンストラクタ
     */
    private Neumann() {
    }

    /**
     * コンストラクタ
     *
     * @param expression 評価する式
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private Neumann(@NonNull String expression) {
        this.expression = expression;
    }

    /**
     * 引数として渡された式を評価する {@link Neumann} クラスの新しいインスタンスを生成し返却します。
     *
     * @param expression 評価する式
     * @return {@link Neumann} クラスの新しいインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static Evaluator input(@NonNull String expression) {
        return new Neumann(expression);
    }

    @Override
    public BigDecimal evaluate(@NonNull MathematicalConstant constant) {
        return switch (constant) {
            case PI -> BigDecimalMath.pi(MATH_CONTEXT);
            case NAPIER -> BigDecimalMath.e(MATH_CONTEXT);
        };
    }

    @Override
    public BigDecimal evaluate(@NonNull MathematicalOperator operator, @NonNull Iterator<BigDecimal> operands) {
        return switch (operator) {
            case NEGATE -> operands.next().negate();
            case MINUS -> operands.next().subtract(operands.next());
            case PLUS -> operands.next().add(operands.next());
            case MULTIPLY -> operands.next().multiply(operands.next());
            case DIVIDE -> operands.next().divide(operands.next());
            case EXPONENT -> BigDecimalMath.pow(operands.next(), operands.next(), MATH_CONTEXT);
            case MODULO -> operands.next().remainder(operands.next());
        };
    }

    @Override
    public BigDecimal evaluate(@NonNull MathematicalFunction function, @NonNull Iterator<BigDecimal> arguments) {
        return switch (function) {
            case CEIL -> arguments.next().setScale(0, RoundingMode.CEILING);
            case FLOOR -> arguments.next().setScale(0, RoundingMode.FLOOR);
            case ROUND -> arguments.next().round(MATH_CONTEXT);
            case ABS -> arguments.next().abs();
            case SINE -> BigDecimalMath.sin(arguments.next(), MATH_CONTEXT);
            case COSINE -> BigDecimalMath.cos(arguments.next(), MATH_CONTEXT);
            case TANGENT -> BigDecimalMath.tan(arguments.next(), MATH_CONTEXT);
            case ARC_SINE -> BigDecimalMath.asin(arguments.next(), MATH_CONTEXT);
            case ARC_COSINE -> BigDecimalMath.acos(arguments.next(), MATH_CONTEXT);
            case ARC_TANGENT -> BigDecimalMath.atan(arguments.next(), MATH_CONTEXT);
            case HYPERBOLIC_SINE -> BigDecimalMath.sinh(arguments.next(), MATH_CONTEXT);
            case HYPERBOLIC_COSINE -> BigDecimalMath.cosh(arguments.next(), MATH_CONTEXT);
            case HYPERBOLIC_TANGENT -> BigDecimalMath.tanh(arguments.next(), MATH_CONTEXT);
            case MIN -> {
                BigDecimal minimum = arguments.next();

                while (arguments.hasNext()) {
                    minimum = minimum.min(arguments.next());
                }

                yield minimum;
            }
            case MAX -> {
                BigDecimal maximum = arguments.next();

                while (arguments.hasNext()) {
                    maximum = maximum.max(arguments.next());
                }

                yield maximum;
            }
            case SUM -> {
                BigDecimal sum = arguments.next();

                while (arguments.hasNext()) {
                    sum = sum.add(arguments.next());
                }

                yield sum;
            }
            case AVERAGE -> {
                BigDecimal average = arguments.next();
                int count = 1;

                while (arguments.hasNext()) {
                    average = average.add(arguments.next());
                    count++;
                }

                yield average.divide(new BigDecimal(count));
            }
            case LOG -> BigDecimalMath.log(arguments.next(), MATH_CONTEXT);
            case SQRT -> BigDecimalMath.sqrt(arguments.next(), MATH_CONTEXT);
        };
    }
}
