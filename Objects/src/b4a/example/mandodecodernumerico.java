package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class mandodecodernumerico extends Activity implements B4AActivity{
	public static mandodecodernumerico mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.mandodecodernumerico");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (mandodecodernumerico).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.mandodecodernumerico");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.mandodecodernumerico", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (mandodecodernumerico) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (mandodecodernumerico) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return mandodecodernumerico.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (mandodecodernumerico) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (mandodecodernumerico) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            mandodecodernumerico mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (mandodecodernumerico) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.SocketWrapper _socket1 = null;
public static anywheresoftware.b4a.randomaccessfile.AsyncStreams _astreams = null;
public static String _trama = "";
public anywheresoftware.b4a.objects.ButtonWrapper _botonpower = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncanalarriba = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncanalabajo = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelcanal = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonmas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonmenos = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelvolumen = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botongrabarteclas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonvolver = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botontvav = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonguia = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonprev = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonpowerdeco = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonmute = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton8 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton9 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _boton0 = null;
public b4a.example.main _main = null;
public b4a.example.mandotv _mandotv = null;
public b4a.example.conectar _conectar = null;
public b4a.example.connection _connection = null;
public b4a.example.dbutils _dbutils = null;
public b4a.example.grabarteclasaire _grabarteclasaire = null;
public b4a.example.grabarteclasdecoder _grabarteclasdecoder = null;
public b4a.example.grabarteclasreproductor _grabarteclasreproductor = null;
public b4a.example.grabarteclastv _grabarteclastv = null;
public b4a.example.mandoaire _mandoaire = null;
public b4a.example.mandodecodificador _mandodecodificador = null;
public b4a.example.mandoreproductordvd _mandoreproductordvd = null;
public b4a.example.mandotvnumerico _mandotvnumerico = null;
public b4a.example.seleccionarmando _seleccionarmando = null;
public b4a.example.starter _starter = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 46;BA.debugLine="Activity.LoadLayout(\"layoutMandoDecoderNumerico\")";
mostCurrent._activity.LoadLayout("layoutMandoDecoderNumerico",mostCurrent.activityBA);
 //BA.debugLineNum = 47;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bg.jpg").getObject()));
 //BA.debugLineNum = 48;BA.debugLine="ConectarServidor";
_conectarservidor();
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _astreams_error() throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub AStreams_Error";
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _boton0_click() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Private Sub Boton0_Click";
 //BA.debugLineNum = 126;BA.debugLine="transmiTrama(26)";
_transmitrama((int) (26));
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _boton1_click() throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Private Sub Boton1_Click";
 //BA.debugLineNum = 153;BA.debugLine="transmiTrama(17)";
_transmitrama((int) (17));
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _boton2_click() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Private Sub Boton2_Click";
 //BA.debugLineNum = 150;BA.debugLine="transmiTrama(18)";
_transmitrama((int) (18));
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _boton3_click() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Private Sub Boton3_Click";
 //BA.debugLineNum = 147;BA.debugLine="transmiTrama(19)";
_transmitrama((int) (19));
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _boton4_click() throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Private Sub Boton4_Click";
 //BA.debugLineNum = 144;BA.debugLine="transmiTrama(20)";
_transmitrama((int) (20));
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _boton5_click() throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Private Sub Boton5_Click";
 //BA.debugLineNum = 141;BA.debugLine="transmiTrama(21)";
_transmitrama((int) (21));
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _boton6_click() throws Exception{
 //BA.debugLineNum = 137;BA.debugLine="Private Sub Boton6_Click";
 //BA.debugLineNum = 138;BA.debugLine="transmiTrama(22)";
_transmitrama((int) (22));
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _boton7_click() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Private Sub Boton7_Click";
 //BA.debugLineNum = 135;BA.debugLine="transmiTrama(23)";
_transmitrama((int) (23));
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _boton8_click() throws Exception{
 //BA.debugLineNum = 131;BA.debugLine="Private Sub Boton8_Click";
 //BA.debugLineNum = 132;BA.debugLine="transmiTrama(24)";
_transmitrama((int) (24));
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return "";
}
public static String  _boton9_click() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Private Sub Boton9_Click";
 //BA.debugLineNum = 129;BA.debugLine="transmiTrama(25)";
_transmitrama((int) (25));
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _botoncanalabajo_click() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Private Sub BotonCanalAbajo_Click";
 //BA.debugLineNum = 99;BA.debugLine="transmiTrama(6)";
_transmitrama((int) (6));
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _botoncanalarriba_click() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Private Sub BotonCanalArriba_Click";
 //BA.debugLineNum = 102;BA.debugLine="transmiTrama(5)";
_transmitrama((int) (5));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _botongrabarteclas_click() throws Exception{
 //BA.debugLineNum = 160;BA.debugLine="Private Sub BotonGrabarTeclas_Click";
 //BA.debugLineNum = 161;BA.debugLine="StartActivity(GrabarTeclasDecoder)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._grabarteclasdecoder.getObject()));
 //BA.debugLineNum = 162;BA.debugLine="Socket1.Close";
_socket1.Close();
 //BA.debugLineNum = 163;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _botonguia_click() throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Private Sub BotonGuia_Click";
 //BA.debugLineNum = 111;BA.debugLine="transmiTrama(8)";
_transmitrama((int) (8));
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _botonmas_click() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Private Sub BotonMas_Click";
 //BA.debugLineNum = 96;BA.debugLine="transmiTrama(3)";
_transmitrama((int) (3));
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static String  _botonmenos_click() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Private Sub BotonMenos_Click";
 //BA.debugLineNum = 93;BA.debugLine="transmiTrama(4)";
_transmitrama((int) (4));
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _botonmenu_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Private Sub BotonMenu_Click";
 //BA.debugLineNum = 117;BA.debugLine="transmiTrama(9)";
_transmitrama((int) (9));
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _botonmute_click() throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Private Sub BotonMute_Click";
 //BA.debugLineNum = 123;BA.debugLine="transmiTrama(11)";
_transmitrama((int) (11));
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _botonpower_click() throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Private Sub BotonPower_Click";
 //BA.debugLineNum = 105;BA.debugLine="transmiTrama(1)";
_transmitrama((int) (1));
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _botonpowerdeco_click() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Private Sub BotonPowerDeco_Click";
 //BA.debugLineNum = 120;BA.debugLine="transmiTrama(2)";
_transmitrama((int) (2));
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _botonprev_click() throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Private Sub BotonPrev_Click";
 //BA.debugLineNum = 108;BA.debugLine="transmiTrama(10)";
_transmitrama((int) (10));
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _botontvav_click() throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Private Sub BotonTvAv_Click";
 //BA.debugLineNum = 114;BA.debugLine="transmiTrama(7)";
_transmitrama((int) (7));
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _botonvolver_click() throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Private Sub BotonVolver_Click";
 //BA.debugLineNum = 156;BA.debugLine="StartActivity(MandoDecodificador)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mandodecodificador.getObject()));
 //BA.debugLineNum = 157;BA.debugLine="Socket1.Close";
_socket1.Close();
 //BA.debugLineNum = 158;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _conectarservidor() throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub ConectarServidor";
 //BA.debugLineNum = 67;BA.debugLine="Try";
try { //BA.debugLineNum = 68;BA.debugLine="If Datos_Validos = False Then Return";
if (_datos_validos()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 70;BA.debugLine="If Socket1.IsInitialized = False Then";
if (_socket1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 71;BA.debugLine="Socket1.Initialize(\"Socket1\")";
_socket1.Initialize("Socket1");
 }else {
 //BA.debugLineNum = 73;BA.debugLine="Socket1.Close";
_socket1.Close();
 //BA.debugLineNum = 74;BA.debugLine="Socket1.Initialize(\"Socket1\")";
_socket1.Initialize("Socket1");
 };
 //BA.debugLineNum = 76;BA.debugLine="Socket1.Connect(SeleccionarMando.nServer,Selecci";
_socket1.Connect(processBA,mostCurrent._seleccionarmando._nserver /*String*/ ,(int)(Double.parseDouble(mostCurrent._seleccionarmando._nport /*String*/ )),(int) (0));
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); };
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static boolean  _datos_validos() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub Datos_Validos As Boolean";
 //BA.debugLineNum = 60;BA.debugLine="If MandoDecodificador.myIp = \"127.0.0.1\" Then";
if ((mostCurrent._mandodecodificador._myip /*String*/ ).equals("127.0.0.1")) { 
 //BA.debugLineNum = 61;BA.debugLine="Msgbox(\"Verifica tu conexion a la nueva red\",\"AC";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Verifica tu conexion a la nueva red"),BA.ObjectToCharSequence("AC_Electronics"),mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 64;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private BotonPower As Button";
mostCurrent._botonpower = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private BotonCanalArriba As Button";
mostCurrent._botoncanalarriba = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private BotonCanalAbajo As Button";
mostCurrent._botoncanalabajo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private LabelCanal As Label";
mostCurrent._labelcanal = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private BotonMas As Button";
mostCurrent._botonmas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private BotonMenos As Button";
mostCurrent._botonmenos = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private LabelVolumen As Label";
mostCurrent._labelvolumen = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private BotonGrabarTeclas As Button";
mostCurrent._botongrabarteclas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private BotonVolver As Button";
mostCurrent._botonvolver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private BotonMenu As Button";
mostCurrent._botonmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private BotonTvAv As Button";
mostCurrent._botontvav = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private BotonGuia As Button";
mostCurrent._botonguia = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private BotonPrev As Button";
mostCurrent._botonprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private BotonPowerDeco As Button";
mostCurrent._botonpowerdeco = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private BotonMute As Button";
mostCurrent._botonmute = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Boton1 As Button";
mostCurrent._boton1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Boton2 As Button";
mostCurrent._boton2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Boton3 As Button";
mostCurrent._boton3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Boton4 As Button";
mostCurrent._boton4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Boton5 As Button";
mostCurrent._boton5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Boton6 As Button";
mostCurrent._boton6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Boton7 As Button";
mostCurrent._boton7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Boton8 As Button";
mostCurrent._boton8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private Boton9 As Button";
mostCurrent._boton9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private Boton0 As Button";
mostCurrent._boton0 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Socket1 As Socket";
_socket1 = new anywheresoftware.b4a.objects.SocketWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Dim AStreams As AsyncStreams";
_astreams = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 11;BA.debugLine="Dim Trama As String";
_trama = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _socket1_connected(boolean _succesful) throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub Socket1_Connected(Succesful As Boolean)";
 //BA.debugLineNum = 82;BA.debugLine="If Succesful Then";
if (_succesful) { 
 //BA.debugLineNum = 83;BA.debugLine="AStreams.Initialize(Socket1.InputStream, Socket1";
_astreams.Initialize(processBA,_socket1.getInputStream(),_socket1.getOutputStream(),"AStreams");
 }else {
 };
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _transmitrama(int _numero) throws Exception{
int _i = 0;
byte[] _buffer = null;
 //BA.debugLineNum = 166;BA.debugLine="Sub transmiTrama(numero As Int)";
 //BA.debugLineNum = 167;BA.debugLine="If AStreams.IsInitialized = False Then Return";
if (_astreams.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 168;BA.debugLine="If (MandoDecodificador.myIp = \"127.0.0.1\") = Fals";
if (((mostCurrent._mandodecodificador._myip /*String*/ ).equals("127.0.0.1"))==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 169;BA.debugLine="Connection.myCUR = Connection.mySQL.ExecQuery(\"S";
mostCurrent._connection._mycur /*anywheresoftware.b4a.sql.SQL.CursorWrapper*/  = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._connection._mysql /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT Trama FROM TramasDecodificador WHERE id='"+BA.NumberToString(_numero)+"'")));
 //BA.debugLineNum = 170;BA.debugLine="For i = 0 To Connection.myCUR.RowCount - 1";
{
final int step4 = 1;
final int limit4 = (int) (mostCurrent._connection._mycur /*anywheresoftware.b4a.sql.SQL.CursorWrapper*/ .getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 171;BA.debugLine="Connection.myCUR.Position = i";
mostCurrent._connection._mycur /*anywheresoftware.b4a.sql.SQL.CursorWrapper*/ .setPosition(_i);
 //BA.debugLineNum = 172;BA.debugLine="Trama = Connection.myCUR.getString(\"Trama\")";
_trama = mostCurrent._connection._mycur /*anywheresoftware.b4a.sql.SQL.CursorWrapper*/ .GetString("Trama");
 //BA.debugLineNum = 173;BA.debugLine="Dim buffer() As Byte";
_buffer = new byte[(int) (0)];
;
 //BA.debugLineNum = 174;BA.debugLine="Trama = Trama & Chr(10) & Chr(13)";
_trama = _trama+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)));
 //BA.debugLineNum = 175;BA.debugLine="buffer = Trama.GetBytes(\"UTF8\")";
_buffer = _trama.getBytes("UTF8");
 //BA.debugLineNum = 176;BA.debugLine="AStreams.Write(buffer)";
_astreams.Write(_buffer);
 }
};
 };
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
}
