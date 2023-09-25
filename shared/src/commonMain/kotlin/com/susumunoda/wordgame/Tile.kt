package com.susumunoda.wordgame

enum class Tile(val points: Int, val distribution: Int) {
    A(points = 1, distribution = 9),
    B(points = 3, distribution = 2),
    C(points = 3, distribution = 2),
    D(points = 2, distribution = 4),
    E(points = 1, distribution = 12),
    F(points = 4, distribution = 2),
    G(points = 2, distribution = 3),
    H(points = 4, distribution = 2),
    I(points = 1, distribution = 9),
    J(points = 8, distribution = 1),
    K(points = 5, distribution = 1),
    L(points = 1, distribution = 4),
    M(points = 3, distribution = 2),
    N(points = 1, distribution = 6),
    O(points = 1, distribution = 8),
    P(points = 3, distribution = 2),
    Q(points = 10, distribution = 1),
    R(points = 1, distribution = 6),
    S(points = 1, distribution = 4),
    T(points = 1, distribution = 6),
    U(points = 1, distribution = 4),
    V(points = 4, distribution = 2),
    W(points = 4, distribution = 2),
    X(points = 8, distribution = 1),
    Y(points = 4, distribution = 2),
    Z(points = 10, distribution = 1),
    BLANK(points = 0, distribution = 2);

    val displayName: String get() = if (this == BLANK) "_" else name
}
