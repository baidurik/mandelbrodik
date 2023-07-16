import java.lang.Math.sqrt

class Complex {
    var re: Double;
    var im: Double;
    constructor(_re: Double, _im: Double) {
        this.re = _re;
        this.im = _im;
    }

    operator fun unaryPlus(): Complex {
        return Complex(this.re, this.im);
    }
    operator fun unaryMinus(): Complex {
        return Complex(-this.re, -this.im);
    }
    fun conj(): Complex {
        return Complex(this.re, -this.im);
    }
    fun module(): Double {
        return sqrt(this.re * this.re + this.im * this.im);
    }

    operator fun plus(b: Double): Complex {
        return Complex(this.re + b, this.im);
    }
    operator fun minus(b: Double): Complex {
        return Complex(this.re - b, this.im);
    }
    operator fun times(b: Double): Complex {
        return Complex(this.re * b, this.im * b);
    }
    operator fun div(b: Double): Complex {
        return Complex(this.re / b, this.im / b);
    }

    operator fun plus(b: Complex): Complex {
        return Complex(this.re + b.re, this.im + b.im);
    }
    operator fun minus(b: Complex): Complex {
        return Complex(this.re - b.re, this.im - b.im);
    }
    operator fun times(b: Complex): Complex {
        return Complex(this.re * b.re - this.im * b.im, this.re * b.im + this.im * b.re);
    }
    operator fun div(b: Complex): Complex {
        return this * b.conj() / b.module();
    }
}
