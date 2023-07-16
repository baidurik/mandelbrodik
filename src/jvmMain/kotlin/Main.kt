
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import Complex
import Presets
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.WindowPosition.PlatformDefault.x
import androidx.compose.ui.window.WindowPosition.PlatformDefault.y
import java.lang.Float.max
import java.lang.Float.min

var cx1 = -2.0;
var cy1 = -2.0;
var cx2 = 2.0;
var cy2 = 2.0;

class Pixel(x : Int, y : Int) {
    val x = x;
    val y = y;
}

fun pxToC(p : Pixel, x1 : Int, y1 : Int, x2 : Int, y2 : Int): Complex {
    return Complex(cx1 + (p.x.toDouble() - x1) / (x2 - x1) * (cx2 - cx1), cy1 + (p.y.toDouble() - y1) / (y2 - y1) * (cy2 - cy1));
}

fun cToPx(c : Complex, x1 : Int, y1 : Int, x2 : Int, y2 : Int) : Pixel {
    return Pixel((x1 + (c.re - cx1) / (cx2 - cx1) * (x2 - x1)).toInt(), (y1 + (c.im - cy1) / (cy2 - cy1) * (y2 - y1)).toInt());
}

@OptIn(ExperimentalGraphicsApi::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
//    var data by remember { Array<IntArray>(1, IntArray(1, 0)) }

    var start_pt by remember { mutableStateOf(Offset.Zero) }
    var end_pt by remember { mutableStateOf(Offset.Zero) }
    MaterialTheme {
        Box(modifier=Modifier.fillMaxHeight().fillMaxWidth(0.7F).padding(2.dp).border(1.dp, Color.Black)){
            Canvas(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(2.dp).pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        start_pt = offset;
                        end_pt = offset;
                    },
                    onDragEnd = {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val p1 = pxToC(Pixel(min(start_pt.x, end_pt.x).toInt(), min(start_pt.y, end_pt.y).toInt()), 0, 0, canvasWidth.toInt(), canvasHeight.toInt());
                        val p2 = pxToC(Pixel(max(start_pt.x, end_pt.x).toInt(), max(start_pt.y, end_pt.y).toInt()), 0, 0, canvasWidth.toInt(), canvasHeight.toInt());
                        cx1 = p1.re;
                        cx2 = p2.re;
                        cy1 = p1.im;
                        cy2 = p2.im;

                        start_pt = Offset.Zero;
                        end_pt = Offset.Zero;
                    },
                    onDrag = { change, offset ->
                        end_pt += offset;
                    }
                )
            }
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                for (i in 0..canvasWidth.toInt()) {
                    for (j in 0..canvasHeight.toInt()) {
                        val c = pxToC(Pixel(i, j), 0, 0, canvasWidth.toInt(), canvasHeight.toInt());
                        val k = Presets.Functions.square(c, Complex(-0.217, 0.7), 100);
                        val col_prv = (k / 10).toInt();
                        val col_nxt = ((k + 9) / 10).toInt();
                        val col_prv_ct = (k - 10 * col_prv) / 10;
                        drawPoints(
                            listOf(Offset(i.toFloat(), j.toFloat())),
                            PointMode.Points,
                            color = if (k > 90) Color.Black else Color.hsl(k.toFloat() / 100 * 360, 0.5F, 0.5F),
                            strokeWidth = 1F
                        );
                    }
                }
                var rect_topleft = Offset(min(start_pt.x, end_pt.x).toFloat(), min(start_pt.y, end_pt.y).toFloat())
                var rect_botright = Offset(max(start_pt.x, end_pt.x).toFloat(), max(start_pt.y, end_pt.y).toFloat())
                drawRect(
                    color = Color.Gray,
                    topLeft = rect_topleft,
                    size = Size(rect_botright.x - rect_topleft.x, rect_botright.y - rect_topleft.y),
                    style = Stroke(),
                    alpha = 0.9F
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
