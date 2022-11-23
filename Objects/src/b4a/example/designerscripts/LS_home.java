package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_home{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[Home/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="LabelBienvenido.Left = 50%X-LabelBienvenido.Width/2"[Home/General script]
views.get("labelbienvenido").vw.setLeft((int)((50d / 100 * width)-(views.get("labelbienvenido").vw.getWidth())/2d));
//BA.debugLineNum = 5;BA.debugLine="LabelBienvenido.Top = 45%Y-LabelBienvenido.Width/2"[Home/General script]
views.get("labelbienvenido").vw.setTop((int)((45d / 100 * height)-(views.get("labelbienvenido").vw.getWidth())/2d));
//BA.debugLineNum = 6;BA.debugLine="LabelInstrucciones.Left = 50%X-LabelInstrucciones.Width/2"[Home/General script]
views.get("labelinstrucciones").vw.setLeft((int)((50d / 100 * width)-(views.get("labelinstrucciones").vw.getWidth())/2d));
//BA.debugLineNum = 7;BA.debugLine="LabelConectar.Left = 50%X-LabelConectar.Width/2"[Home/General script]
views.get("labelconectar").vw.setLeft((int)((50d / 100 * width)-(views.get("labelconectar").vw.getWidth())/2d));

}
}