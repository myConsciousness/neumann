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
import org.thinkit.neumann.catalog.TokenPattern;

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
     * トークンパターン
     */
    private TokenPattern tokenPattern;

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
     * @param tokenPattern トークンパターン
     * @param token        トークン
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private ExpressionToken(@NonNull TokenPattern tokenPattern, @NonNull Object token) {
        this.tokenPattern = tokenPattern;
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
        return new ExpressionToken(TokenPattern.LITERAL, literal);
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
        return new ExpressionToken(TokenPattern.OPERATOR, operator);
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
        return new ExpressionToken(TokenPattern.FUNCTION, function);
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
        return new ExpressionToken(TokenPattern.OPEN_BRACKET, openBracket);
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
        return new ExpressionToken(TokenPattern.CLOSE_BRACKET, closeBracket);
    }

    /**
     * トークンがリテラルであるか判定します。
     *
     * @return トークンがリテラルである場合は {@code true} 、それ以外の場合は {@code false}
     */
    public boolean isLiteral() {
        return this.tokenPattern == TokenPattern.LITERAL;
    }

    /**
     * トークンが演算子であるか判定します。
     *
     * @return トークンが演算子である場合は {@code true} 、それ以外の場合は {@code false}
     */
    public boolean isOperator() {
        return this.tokenPattern == TokenPattern.OPERATOR;
    }

    /**
     * トークンが関数であるか判定します。
     *
     * @return トークンが関数である場合は {@code true} 、それ以外の場合は {@code false}
     */
    public boolean isFunction() {
        return this.tokenPattern == TokenPattern.FUNCTION;
    }

    /**
     * トークンが始め括弧であるか判定します。
     *
     * @return トークンが始め括弧である場合は {@code true} 、それ以外の場合は {@code false}
     */
    public boolean isOpenBracket() {
        return this.tokenPattern == TokenPattern.OPEN_BRACKET;
    }

    /**
     * トークンが終わり括弧であるか判定します。
     *
     * @return トークンが終わり括弧である場合は {@code true} 、それ以外の場合は {@code false}
     */
    public boolean isCloseBracket() {
        return this.tokenPattern == TokenPattern.CLOSE_BRACKET;
    }

    /**
     * トークンを文字列として返却します。
     *
     * @return 文字列のトークン
     */
    public String getLiteral() {
        return String.valueOf(this.token);
    }

    /**
     * トークンを {@link MathematicalOperator} 型へ変換し返却します。
     * <p>
     * {@link #getOperator()} メソッドを呼び出す前に必ず {@link #isOperator()}
     * メソッドでトークンパターンを確認してください。トークンパターンが {@link TokenPattern#OPERATOR}
     * ではないトークンオブジェクトに対して {@link #getOperator()} メソッドを使用した場合は必ず
     * {@link ClassCastException} が実行時に発生します。
     *
     * @return {@link MathematicalOperator} 型のトークン
     *
     * @exception ClassCastException トークンパターンが {@link TokenPattern#OPERATOR}
     *                               ではないトークンオブジェクトに対して {@link #getOperator()}
     *                               メソッドが呼び出された場合
     */
    public MathematicalOperator getOperator() {
        return (MathematicalOperator) this.token;
    }

    public MathematicalFunction getFunction() {
        return (MathematicalFunction) this.token;
    }

    public OpenBracket getOpenBracket() {
        return (OpenBracket) this.token;
    }

    public CloseBracket getCloseBracket() {
        return (CloseBracket) this.token;
    }
}
