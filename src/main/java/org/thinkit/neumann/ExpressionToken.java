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

import org.thinkit.neumann.catalog.CloseBracket;
import org.thinkit.neumann.catalog.MathematicalFunction;
import org.thinkit.neumann.catalog.MathematicalOperator;
import org.thinkit.neumann.catalog.OpenBracket;
import org.thinkit.neumann.catalog.TokenType;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * 式の構成要素であるトークンを表現するクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class ExpressionToken implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1530347236413716636L;

    /**
     * トークン種別
     */
    private TokenType tokenType;

    /**
     * トークン
     */
    private Object token;

    /**
     * デフォルトコンストラクタ
     */
    private ExpressionToken() {
    }

    /**
     * コンストラクタ
     *
     * @param tokenType トークン種別
     * @param token     トークン
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private ExpressionToken(@NonNull TokenType tokenType, @NonNull Object token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    /**
     * リテラルを表現する新しい {@link ExpresionToken} クラスを生成し返却します。
     *
     * @param literal リテラル
     * @return リテラルを表現する新しい {@link ExpresionToken} クラス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static ExpressionToken of(@NonNull String literal) {
        return new ExpressionToken(TokenType.LITERAL, literal);
    }

    /**
     * 演算子を表現する新しい {@link ExpresionToken} クラスを生成し返却します。
     *
     * @param operator 演算子
     * @return 演算子を表現する新しい {@link ExpresionToken} クラス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static ExpressionToken of(@NonNull MathematicalOperator operator) {
        return new ExpressionToken(TokenType.OPERATOR, operator);
    }

    /**
     * 関数を表現する新しい {@link ExpresionToken} クラスを生成し返却します。
     *
     * @param function 関数
     * @return 関数を表現する新しい {@link ExpresionToken} クラス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static ExpressionToken of(@NonNull MathematicalFunction function) {
        return new ExpressionToken(TokenType.FUNCTION, function);
    }

    /**
     * 始め括弧を表現する新しい {@link ExpresionToken} クラスを生成し返却します。
     *
     * @param openBracket 始め括弧
     * @return 始め括弧を表現する新しい {@link ExpresionToken} クラス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static ExpressionToken of(@NonNull OpenBracket openBracket) {
        return new ExpressionToken(TokenType.OPEN_BRACKET, openBracket);
    }

    /**
     * 終わり括弧を表現する新しい {@link ExpresionToken} クラスを生成し返却します。
     *
     * @param closeBracket 終わり括弧
     * @return 終わり括弧を表現する新しい {@link ExpresionToken} クラス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static ExpressionToken of(@NonNull CloseBracket closeBracket) {
        return new ExpressionToken(TokenType.CLOSE_BRACKET, closeBracket);
    }
}
