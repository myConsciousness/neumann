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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.thinkit.api.catalog.BiCatalog;
import org.thinkit.common.Preconditions;
import org.thinkit.neumann.catalog.Arity;
import org.thinkit.neumann.catalog.CloseBracket;
import org.thinkit.neumann.catalog.FunctionPattern;
import org.thinkit.neumann.catalog.MathematicalConstant;
import org.thinkit.neumann.catalog.MathematicalFunction;
import org.thinkit.neumann.catalog.MathematicalOperator;
import org.thinkit.neumann.catalog.OpenBracket;
import org.thinkit.neumann.catalog.OperatorAssociativity;
import org.thinkit.neumann.catalog.OperatorPattern;

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

    default String evaluate() {
        final Deque<BigDecimal> values = new ArrayDeque<>(); // values stack
        final Deque<ExpressionToken> stack = new ArrayDeque<>(); // operator stack
        final Deque<Integer> previousValuesSize = new ArrayDeque<>();
        final ExpressionTokenizer tokens = ExpressionTokenizer.of(this.getExpression());

        ExpressionToken previous = null;

        while (tokens.hasMoreTokens()) {

            String strToken = tokens.nextToken();

            if (StringUtils.isEmpty(strToken)) {
                continue;
            }

            final ExpressionToken token = toExpressionToken(strToken);

            if (token.isOpenBracket()) {

                stack.push(token);

                if (previous != null && previous.isFunction()) {
                    if (!BiCatalog.contains(OpenBracket.class, token.getOpenBracket().getTag())) {
                        throw new IllegalArgumentException("Invalid bracket after function: " + strToken);
                    }
                } else {
                    if (!BiCatalog.contains(OpenBracket.class, token.getOpenBracket().getTag())) {
                        throw new IllegalArgumentException("Invalid bracket in expression: " + strToken);
                    }
                }
            } else if (token.isCloseBracket()) {
                Preconditions.requireNonNull(previous,
                        new IllegalArgumentException("expression can't start with a close bracket"));

                if (previous.isFunctionArgumentSeparator()) {
                    throw new IllegalArgumentException("argument is missing");
                }

                CloseBracket closeBracket = token.getCloseBracket();
                boolean openBracketFound = false;

                while (!stack.isEmpty()) {
                    ExpressionToken sc = stack.pop();
                    if (sc.isOpenBracket()) {
                        if (BiCatalog.getEnum(OpenBracket.class, closeBracket.getCode()).getTag()
                                .equals(sc.getOpenBracket().getTag())) {
                            openBracketFound = true;
                            break;
                        } else {
                            throw new IllegalArgumentException(String.format("Invalid parenthesis match: %s and %s",
                                    sc.getOpenBracket().getTag(), closeBracket.getTag()));
                        }
                    } else {
                        output(values, sc);
                    }
                }

                if (!openBracketFound) {
                    throw new IllegalArgumentException("Parentheses mismatched");
                }

                if (!stack.isEmpty() && stack.peek().isFunction()) {
                    final int argumentCount = values.size() - previousValuesSize.pop();
                    this.evaluate(values, stack.pop().getFunction(), argumentCount);
                }
            } else if (token.isFunctionArgumentSeparator()) {
                if (previous == null) {
                    throw new IllegalArgumentException("expression can't start with a function argument separator");
                }

                if (previous.isOpenBracket() || previous.isFunctionArgumentSeparator()) {

                    throw new IllegalArgumentException("argument is missing");
                }

                boolean pe = false;

                while (!stack.isEmpty()) {
                    if (stack.peek().isOpenBracket()) {
                        pe = true;
                        break;
                    } else {
                        output(values, stack.pop());
                    }
                }
                if (!pe) {
                    throw new IllegalArgumentException("Separator or parentheses mismatched");
                } else {
                    ExpressionToken openBracket = stack.pop();
                    ExpressionToken scopeToken = stack.peek();
                    stack.push(openBracket);
                    if (!scopeToken.isFunction()) {
                        throw new IllegalArgumentException("Argument separator used outside of function scope");
                    }
                }
            } else if (token.isFunction()) {
                stack.push(token);
                previousValuesSize.push(values.size());
            } else if (token.isOperator()) {

                while (!stack.isEmpty()) {

                    ExpressionToken sc = stack.peek();

                    if (sc.isOperator()
                            && ((token.getOperator().getTag().getAssociativity() == OperatorAssociativity.LEFT)
                                    && (token.getOperator().getTag().getPrecedence().getTag() <= sc.getOperator()
                                            .getTag().getPrecedence().getTag())
                                    || (token.getOperator().getTag().getPrecedence().getTag() < sc.getOperator()
                                            .getTag().getPrecedence().getTag()))) {
                        this.output(values, stack.pop());
                    } else {
                        break;
                    }
                }

                stack.push(token);
            } else {

                if ((previous != null) && previous.isLiteral()) {
                    throw new IllegalArgumentException("A literal can't follow another literal");
                }

                output(values, token);
            }
            previous = token;
        }

        while (!stack.isEmpty()) {
            ExpressionToken sc = stack.pop();
            if (sc.isOpenBracket() || sc.isCloseBracket()) {
                throw new IllegalArgumentException("Parentheses mismatched");
            }
            output(values, sc);
        }

        if (values.size() != 1) {
            throw new IllegalArgumentException();
        }

        return values.pop().toString();
    }

    private void evaluate(Deque<BigDecimal> values, MathematicalFunction function, int argumentCount) {
        values.push(this.evaluate(function, this.toArguments(values, argumentCount)));
    }

    private void output(Deque<BigDecimal> values, ExpressionToken token) {
        if (token.isLiteral()) {

            final String literal = token.getLiteral();
            final MathematicalConstant constant = BiCatalog.getEnumByTag(MathematicalConstant.class,
                    token.getLiteral());

            values.push(constant == null ? new BigDecimal(literal) : this.evaluate(constant));

        } else if (token.isOperator()) {
            MathematicalOperator operator = token.getOperator();
            values.push(this.evaluate(operator,
                    this.toArguments(values, this.toArgumentCount(operator.getTag().getArity()))));
        }
    }

    private int toArgumentCount(@NonNull Arity arity) {
        return switch (arity) {
            case UNARY -> 1;
            case BINARY -> 2;
            case FINITARY -> Integer.MAX_VALUE;
            case NULLARY, TERNARY, MULTIARY -> throw new UnsupportedOperationException();
        };
    }

    private Iterator<BigDecimal> toArguments(Deque<BigDecimal> values, int argumentCount) {

        LinkedList<BigDecimal> arguments = new LinkedList<>();

        for (int i = 0; i < argumentCount; i++) {
            arguments.addFirst(values.pop());
        }

        return arguments.iterator();
    }

    private ExpressionToken toExpressionToken(@NonNull String token) {
        if (",".equals(token)) {
            return ExpressionToken.separator();
        } else if (BiCatalog.contains(FunctionPattern.class, token)) {
            final FunctionPattern functionPattern = BiCatalog.getEnumByTag(FunctionPattern.class, token);
            return ExpressionToken.of(BiCatalog.getEnum(MathematicalFunction.class, functionPattern.getCode()));
        } else if (BiCatalog.contains(OperatorPattern.class, token)) {
            final OperatorPattern operatorPattern = BiCatalog.getEnumByTag(OperatorPattern.class, token);
            return ExpressionToken.of(BiCatalog.getEnum(MathematicalOperator.class, operatorPattern.getCode()));
        } else if (BiCatalog.contains(OpenBracket.class, token) || BiCatalog.contains(CloseBracket.class, token)) {
            if (BiCatalog.contains(OpenBracket.class, token)) {
                return ExpressionToken.of(BiCatalog.getEnumByTag(OpenBracket.class, token));
            } else {
                return ExpressionToken.of(BiCatalog.getEnumByTag(CloseBracket.class, token));
            }
        } else {
            return ExpressionToken.of(token);
        }
    }
}
