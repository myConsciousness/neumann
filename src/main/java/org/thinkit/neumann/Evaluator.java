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

        final Deque<BigDecimal> valueStack = new ArrayDeque<>();
        final Deque<ExpressionToken> symbolStack = new ArrayDeque<>();
        final Deque<Integer> valueSizeStack = new ArrayDeque<>();
        final ExpressionTokenizer tokens = ExpressionTokenizer.of(this.getExpression());

        ExpressionToken previous = null;

        while (tokens.hasMoreTokens()) {

            final String token = tokens.nextToken();

            if (StringUtils.isEmpty(token)) {
                continue;
            }

            final ExpressionToken expressionToken = this.toExpressionToken(token);

            if (expressionToken.isOpenBracket()) {

                symbolStack.push(expressionToken);

                if (previous != null && previous.isFunction()) {
                    if (!BiCatalog.contains(OpenBracket.class, expressionToken.getOpenBracket().getTag())) {
                        throw new IllegalArgumentException("Invalid bracket after function: " + token);
                    }
                } else {
                    if (!BiCatalog.contains(OpenBracket.class, expressionToken.getOpenBracket().getTag())) {
                        throw new IllegalArgumentException("Invalid bracket in expression: " + token);
                    }
                }
            } else if (expressionToken.isCloseBracket()) {
                Preconditions.requireNonNull(previous,
                        new IllegalArgumentException("expression can't start with a close bracket"));

                if (previous.isFunctionArgumentSeparator()) {
                    throw new IllegalArgumentException("argument is missing");
                }

                CloseBracket closeBracket = expressionToken.getCloseBracket();
                boolean openBracketFound = false;

                while (!symbolStack.isEmpty()) {
                    ExpressionToken sc = symbolStack.pop();
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
                        output(valueStack, sc);
                    }
                }

                if (!openBracketFound) {
                    throw new IllegalArgumentException("Parentheses mismatched");
                }

                if (!symbolStack.isEmpty() && symbolStack.peek().isFunction()) {
                    final int argumentCount = valueStack.size() - valueSizeStack.pop();
                    this.evaluate(valueStack, symbolStack.pop().getFunction(), argumentCount);
                }
            } else if (expressionToken.isFunctionArgumentSeparator()) {
                if (previous == null) {
                    throw new IllegalArgumentException("expression can't start with a function argument separator");
                }

                if (previous.isOpenBracket() || previous.isFunctionArgumentSeparator()) {

                    throw new IllegalArgumentException("argument is missing");
                }

                boolean pe = false;

                while (!symbolStack.isEmpty()) {
                    if (symbolStack.peek().isOpenBracket()) {
                        pe = true;
                        break;
                    } else {
                        output(valueStack, symbolStack.pop());
                    }
                }
                if (!pe) {
                    throw new IllegalArgumentException("Separator or parentheses mismatched");
                } else {
                    ExpressionToken openBracket = symbolStack.pop();
                    ExpressionToken scopeToken = symbolStack.peek();
                    symbolStack.push(openBracket);
                    if (!scopeToken.isFunction()) {
                        throw new IllegalArgumentException("Argument separator used outside of function scope");
                    }
                }
            } else if (expressionToken.isFunction()) {
                symbolStack.push(expressionToken);
                valueSizeStack.push(valueStack.size());
            } else if (expressionToken.isOperator()) {

                while (!symbolStack.isEmpty()) {

                    ExpressionToken sc = symbolStack.peek();

                    if (sc.isOperator() && ((expressionToken.getOperator().getTag()
                            .getAssociativity() == OperatorAssociativity.LEFT)
                            && (expressionToken.getOperator().getTag().getPrecedence().getTag() <= sc.getOperator()
                                    .getTag().getPrecedence().getTag())
                            || (expressionToken.getOperator().getTag().getPrecedence().getTag() < sc.getOperator()
                                    .getTag().getPrecedence().getTag()))) {
                        this.output(valueStack, symbolStack.pop());
                    } else {
                        break;
                    }
                }

                symbolStack.push(expressionToken);
            } else {

                if ((previous != null) && previous.isLiteral()) {
                    throw new IllegalArgumentException("A literal can't follow another literal");
                }

                output(valueStack, expressionToken);
            }

            previous = expressionToken;
        }

        while (!symbolStack.isEmpty()) {

            ExpressionToken sc = symbolStack.pop();

            if (sc.isOpenBracket() || sc.isCloseBracket()) {
                throw new IllegalArgumentException("Parentheses mismatched");
            }

            output(valueStack, sc);
        }

        if (valueStack.size() != 1) {
            throw new IllegalArgumentException();
        }

        return valueStack.pop().toString();
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
