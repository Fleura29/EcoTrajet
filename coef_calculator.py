import numpy as np

# 3 points à (km/h, emissions)
points = [(30, 150), (70, 100), (130, 200)]

# On a 3 équations
# 150 = c * 30² + b * 30 + a
# 100 = c * 70² + b * 70 + a
# 200 = c * 130² + b * 130 + a

# On résoud pour a, b et c.
# Ca nous donne les 3 coefficients

# Sous forme de matrices

Y = np.array([points[0][1], points[1][1], points[2][1]])
X = np.array([
    [points[0][0] ** 2, points[0][0], 1],
    [points[1][0] ** 2, points[1][0], 1],
    [points[2][0] ** 2, points[2][0], 1]
])

# Compute A = X^(-1) * Y
A = np.dot(np.linalg.inv(X), Y)

print("a: ", round(A[2], 2))
print("b: ", round(A[1], 2))
print("c: ", round(A[0], 2))