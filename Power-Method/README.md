# Numerical Analysis Lab

The power method is an iterative technique used in numerical analysis to find the dominant eigenvalue and corresponding eigenvector of a matrix. It is particularly useful for large sparse matrices where other methods may be computationally expensive.  Its extension to the inverse power method is practical for finding any eigenvalue provided that a
good initial approximation is known.

Ensure you have Python installed on your system. Then, install the necessary dependencies by running:

```shell
pip install -r requirements.txt
```

## Computing the Dominant Eigenvalues

Let $A$ be an $n \times n$, non-singular real valued matrix with a basis of eigenvectors. Denote the eigenvalues by $\lambda_j$ and eigenvectors by $v_j$. 

We assume here there is a single eigenvalue of largest magnitude (the ‘dominant’ eigenvalue). Label them as follows:

$$ |\lambda_1| > |\lambda_2| \geq |\lambda_3| \geq \dots \geq |\lambda_n| > 0$$

Note that if $A$ has real-valued entries, it must be that $\lambda_1$ is real (why?).

The simplest approach to computing $\lambda_1$ and $v_1$ is the power method. The idea is as follows. Let $x$ be any vector. Then, since $\left(v_j\right)$ is a basis,

$$x = c_1v_1+\dots+c_nv_n$$

Now suppose that $c_1 \neq 0$. Then,

$$Ax = c_1\lambda_1v_1 + \dots + c_n\lambda_nv_n$$

and, applying $A$ again,

$$A^kx = {\sum_{j=1}^n}c_j\lambda_j^kv_j$$

Since the $\lambda_1^k$ term is largest in magnitude, in the sequence

$$x, Ax, A^2x, A^3x\dots$$

we expect the $\lambda_1^kv_1$  term will dominate so

$${A} A^kx \approx c\lambda_1^kv_1 + smaller terms$$ 


Each iteration grows the largest term relative to the others, so after enough iterations only the first term (what we want) will be left.

## The Basic Method

Let’s formalize the observation and derive a practical method. The main trouble is that $\lambda_1^k$
will either grow exponentially (bad) or decay to zero (less bad, but still bad). By taking the
right ratio, the issue can be avoided.

Let $x$ and $x$ be vectors with $w^Tv_1 \neq 0$ and such that $x$ has non-zero $v_1$ component. Then

$$ \frac{w^tA^kx}{w^tA^{k-1}x} = \lambda_1 + O((\lambda_2/\lambda_1)^k)\quad as \quad k \to \infty  $$

***Poof.*** Since the eigenvectors form a basis, there are scalars $c_1,\dots,c_n$ such that

$$x = \sum_{j=1}^nv_jc_j$$

By assumption, $c_1 \neq 0$. Since $A^kv_j = \lambda_j^kv_j$, it follows that

$$A^kx = \sum_{j=1}^nc_j\lambda_j^kv_j = \lambda_1^k(c_1v_1 + \sum_{j=2}^nc_j(\frac{\lambda_j}{\lambda_1})^kv_j )$$

All the terms in the parentheses except the first go to zero in magnitude as $k \to \infty$. Thus

$$\begin{align}\frac{w^tA^kx}{w^tA^{k-1}x} &= \lambda_1\frac{d_1 + \sum_{j=2}^nd_j(\lambda_j/\lambda_1)^k}{d_1 + \sum_{j=2}^nd_j(\lambda_j/\lambda_1)^{k-1}}\\\\&= \lambda_1 \frac{1+O((\lambda_2/\lambda_1)^k)}{1+O((\lambda_2/\lambda_1)^{k-1}}\\\\ &= \lambda_1 + O((\lambda_2/\lambda_1)^k) \end{align}$$

where $d_j = c_jw^Tv_j$ (note that $d_1 \neq 0$ by assumption). Note that we have used that

$$\frac{1}{1+f} = 1 + O(f)$$

Thus, the power method computes the dominant eigenvalue (largest in magnitude), and the convergence is linear. The rate depends on the size of $\lambda_1$ relative to the next largest eigenvalue $\lambda_2$.

## The Inverse Power Method

The Inverse Power method is a modification of the Power method that gives faster convergence. It is used to determine the eigenvalue of $A$ that is closest to a specified number $q$.

Suppose that matrix $A$ has eigenvalues $\lambda_1, \lambda_2, \dots, \lambda_n$ with linearly independent eigenvectors $v_1^{(1)}, v_2^{(2)}, \dots, v_n^{(n)}$. The eigenvalues of $(A-qI)^{-1}$ where $q \neq \lambda_i$ for $i = 1,2,3, \dots, n$ are:

$$\frac{1}{\lambda_1 - q}, \frac{1}{\lambda_2 - q}, \dots, \frac{1}{\lambda_n - q}$$

Applying the Power Method to $(A-qI)^{-1}$ gives

$$ y^{(m)} = (A-qI)^{-1}x^{(m-1)} ,$$

$$ \mu^{(m)} = y_{p_{m-1}}^{(m)} = \frac{y_{p_{m-1}}^{(m)}}{x_{p_{m-1}}^{(m-1)}} = \frac{ \sum_{j=1}^n \beta\frac{1}{(\lambda_j-1)^m} v_{p_{m-1}}^{(j)} } {\sum_{j=1}^n \beta\frac{1}{(\lambda_j-1)^{m-1}} v_{p_{m-1}}^{(j)}},$$

$$x^{(m)} = \frac{y^{(m)}}{y_{p_m}^{(m)}}$$

where at each step, $p_m$ represents the smallest integer for which $\left| y_{p_m}^{(m)} \right| = \| y^{(m)} \|_\infty$. The sequence $\left\{\mu^{(m)}\right\} $ converges to $\frac{1}{\lambda_k - q}$ , where

$$ \frac{1}{\|\lambda_k-q\|} = \max_{1 \leq i \leq n}\frac{1}{|\lambda_k-q|},$$

and $\lambda_k \approx q + \frac{1}{\mu^{(m)}}$ is the eigenvalue of $A$ that is closest to $q$.

The vector $y^{(m)}$ is obtained by solving the system of linear equations $(A-qI)y^{(m)} = x^{(m-1)}$ and for that we use the `numpy.linalg.solve` function, that uses LU decomposition with partial pivoting and row interchanges.

# Eigen-Analyzer
As a practical example, we have implemented a Streamlit app that words on these methods. So have fun with it!

```shell
streamlit run app.py
```

