class Fibonacci {
    fib(n) {
        if (n <= 1) return n;
        return fib(n - 2) + fib(n - 1);
    }
}

class F < Fibonacci {
    printFib() {
        for (var i = 0; i < 20; i = i + 1) {
            print super.fib(i);
        }
    }
}

F().printFib();