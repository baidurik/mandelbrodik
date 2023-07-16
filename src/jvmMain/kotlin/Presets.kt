import androidx.compose.ui.graphics.Color

class Presets {
    class Palettes {
        companion object {
            val Deep = arrayOf(0xFF4C72B0, 0xFFDD8452, 0xFF55A868, 0xFFC44E52, 0xFF8172B3, 0xFF937860, 0xFFDA8BC3, 0xFF8C8C8C, 0xFFCCB974, 0xFF64B5CD);
        }
    }

    class Functions {
        companion object {
            fun square(x: Complex, c: Complex, max_k: Int): Int {
                var tmp = x;
                for (i in 0..max_k) {
                    if (tmp.module() > 2) return i;
                    tmp = tmp * tmp + c;
                }
                return max_k;
            }
        }
    }
}