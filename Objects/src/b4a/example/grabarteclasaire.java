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

public class grabarteclasaire extends Activity implements B4AActivity{
	public static grabarteclasaire mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.grabarteclasaire");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (grabarteclasaire).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.grabarteclasaire");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.grabarteclasaire", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (grabarteclasaire) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (grabarteclasaire) Resume **");
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
		return grabarteclasaire.class;
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
            BA.LogInfo("** Activity (grabarteclasaire) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (grabarteclasaire) Pause event (activity is not paused). **");
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
            grabarteclasaire mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (grabarteclasaire) Resume **");
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
public anywheresoftware.b4a.objects.SpinnerWrapper _spinnerteclas = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelteclaagrabar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonlimpiar = null;
public anywheresoftware.b4a.objects.PanelWrapper _paneltramarecibida = null;
public anywheresoftware.b4a.objects.LabelWrapper _labeltramarecibida = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botongrabartecla = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittexttramarecibida = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonvolver = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelinstruccion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprobartrama = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btniniciarcomunicacion = null;
public static String _trama = "";
public b4a.example.main _main = null;
public b4a.example.mandotv _mandotv = null;
public b4a.example.conectar _conectar = null;
public b4a.example.connection _connection = null;
public b4a.example.dbutils _dbutils = null;
public b4a.example.grabarteclasdecoder _grabarteclasdecoder = null;
public b4a.example.grabarteclasreproductor _grabarteclasreproductor = null;
public b4a.example.grabarteclastv _grabarteclastv = null;
public b4a.example.mandoaire _mandoaire = null;
public b4a.example.mandodecodernumerico _mandodecodernumerico = null;
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
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 35;BA.debugLine="Activity.LoadLayout(\"LayoutGrabarTeclasAire\")";
mostCurrent._activity.LoadLayout("LayoutGrabarTeclasAire",mostCurrent.activityBA);
 //BA.debugLineNum = 36;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bg.jpg").getObject()));
 //BA.debugLineNum = 39;BA.debugLine="SpinnerTeclas.AddAll(Array As String(\"Power\",\"Tem";
mostCurrent._spinnerteclas.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Power","Temperatura +","Temperatura -","Mode","Sleep","Fan Speed","Swing","Air Direction","Led Display","Timer On","Timer Off","Turbo"}));
 //BA.debugLineNum = 40;BA.debugLine="ConectarServidor";
_conectarservidor();
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _astreams_error() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub AStreams_Error";
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _astreams_newdata(byte[] _buffer) throws Exception{
String _msg = "";
 //BA.debugLineNum = 80;BA.debugLine="Sub AStreams_NewData(Buffer() As Byte)";
 //BA.debugLineNum = 81;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 82;BA.debugLine="msg = BytesToString(Buffer,0,Buffer.Length,\"UTF8\"";
_msg = anywheresoftware.b4a.keywords.Common.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8");
 //BA.debugLineNum = 83;BA.debugLine="ToastMessageShow(msg,False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_msg),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 84;BA.debugLine="EditTextTramaRecibida.Text = \"\"";
mostCurrent._edittexttramarecibida.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 85;BA.debugLine="EditTextTramaRecibida.Text = msg";
mostCurrent._edittexttramarecibida.setText(BA.ObjectToCharSequence(_msg));
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _botongrabartecla_click() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Private Sub BotonGrabarTecla_Click";
 //BA.debugLineNum = 94;BA.debugLine="If EditTextTramaRecibida.Text = \"\" Then";
if ((mostCurrent._edittexttramarecibida.getText()).equals("")) { 
 //BA.debugLineNum = 95;BA.debugLine="ToastMessageShow(\"La trama no puede estar vac??a\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("La trama no puede estar vac??a"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 98;BA.debugLine="Select SpinnerTeclas.SelectedIndex";
switch (BA.switchObjectToInt(mostCurrent._spinnerteclas.getSelectedIndex(),(int) (0),(int) (1),(int) (2),(int) (3),(int) (4),(int) (5),(int) (6),(int) (7),(int) (8),(int) (9),(int) (10),(int) (11))) {
case 0: {
 //BA.debugLineNum = 100;BA.debugLine="GuardarTramaAire(1)";
_guardartramaaire((int) (1));
 break; }
case 1: {
 //BA.debugLineNum = 102;BA.debugLine="GuardarTramaAire(2)";
_guardartramaaire((int) (2));
 break; }
case 2: {
 //BA.debugLineNum = 104;BA.debugLine="GuardarTramaAire(3)";
_guardartramaaire((int) (3));
 break; }
case 3: {
 //BA.debugLineNum = 106;BA.debugLine="GuardarTramaAire(4)";
_guardartramaaire((int) (4));
 break; }
case 4: {
 //BA.debugLineNum = 108;BA.debugLine="GuardarTramaAire(5)";
_guardartramaaire((int) (5));
 break; }
case 5: {
 //BA.debugLineNum = 110;BA.debugLine="GuardarTramaAire(6)";
_guardartramaaire((int) (6));
 break; }
case 6: {
 //BA.debugLineNum = 112;BA.debugLine="GuardarTramaAire(7)";
_guardartramaaire((int) (7));
 break; }
case 7: {
 //BA.debugLineNum = 114;BA.debugLine="GuardarTramaAire(8)";
_guardartramaaire((int) (8));
 break; }
case 8: {
 //BA.debugLineNum = 116;BA.debugLine="GuardarTramaAire(9)";
_guardartramaaire((int) (9));
 break; }
case 9: {
 //BA.debugLineNum = 118;BA.debugLine="GuardarTramaAire(10)";
_guardartramaaire((int) (10));
 break; }
case 10: {
 //BA.debugLineNum = 120;BA.debugLine="GuardarTramaAire(11)";
_guardartramaaire((int) (11));
 break; }
case 11: {
 //BA.debugLineNum = 122;BA.debugLine="GuardarTramaAire(12)";
_guardartramaaire((int) (12));
 break; }
}
;
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _botonlimpiar_click() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Private Sub BotonLimpiar_Click";
 //BA.debugLineNum = 126;BA.debugLine="EditTextTramaRecibida.Text = \"\"";
mostCurrent._edittexttramarecibida.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _botonvolver_click() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Private Sub BotonVolver_Click";
 //BA.debugLineNum = 129;BA.debugLine="StartActivity(MandoAire)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mandoaire.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="Socket1.Close";
_socket1.Close();
 //BA.debugLineNum = 131;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _btniniciarcomunicacion_click() throws Exception{
byte[] _buffer = null;
String _str = "";
 //BA.debugLineNum = 133;BA.debugLine="Private Sub BtnIniciarComunicacion_Click";
 //BA.debugLineNum = 135;BA.debugLine="If AStreams.IsInitialized = False Then Return";
if (_astreams.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 137;BA.debugLine="If MandoAire.myIp.Length > 0 Then";
if (mostCurrent._mandoaire._myip /*String*/ .length()>0) { 
 //BA.debugLineNum = 138;BA.debugLine="Dim buffer() As Byte";
_buffer = new byte[(int) (0)];
;
 //BA.debugLineNum = 139;BA.debugLine="Dim str As String = MandoAire.myIp";
_str = mostCurrent._mandoaire._myip /*String*/ ;
 //BA.debugLineNum = 140;BA.debugLine="str = str & Chr(10) & Chr(13)";
_str = _str+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)));
 //BA.debugLineNum = 141;BA.debugLine="buffer = str.GetBytes(\"UTF8\")";
_buffer = _str.getBytes("UTF8");
 //BA.debugLineNum = 142;BA.debugLine="AStreams.Write(buffer)";
_astreams.Write(_buffer);
 };
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _btnprobartrama_click() throws Exception{
byte[] _buffer = null;
 //BA.debugLineNum = 145;BA.debugLine="Private Sub BtnProbarTrama_Click";
 //BA.debugLineNum = 147;BA.debugLine="If AStreams.IsInitialized = False Then Return";
if (_astreams.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 148;BA.debugLine="If (MandoAire.MyIp = \"127.0.0.1\") = False Then";
if (((mostCurrent._mandoaire._myip /*String*/ ).equals("127.0.0.1"))==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 149;BA.debugLine="Trama = EditTextTramaRecibida.Text";
mostCurrent._trama = mostCurrent._edittexttramarecibida.getText();
 //BA.debugLineNum = 150;BA.debugLine="Dim buffer() As Byte";
_buffer = new byte[(int) (0)];
;
 //BA.debugLineNum = 151;BA.debugLine="Trama = Trama & Chr(10) & Chr(13)";
mostCurrent._trama = mostCurrent._trama+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)));
 //BA.debugLineNum = 152;BA.debugLine="buffer = Trama.GetBytes(\"UTF8\")";
_buffer = mostCurrent._trama.getBytes("UTF8");
 //BA.debugLineNum = 153;BA.debugLine="AStreams.Write(buffer)";
_astreams.Write(_buffer);
 };
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _conectarservidor() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub ConectarServidor";
 //BA.debugLineNum = 59;BA.debugLine="Try";
try { //BA.debugLineNum = 60;BA.debugLine="If Datos_Validos = False Then Return";
if (_datos_validos()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 62;BA.debugLine="If Socket1.IsInitialized = False Then";
if (_socket1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 63;BA.debugLine="Socket1.Initialize(\"Socket1\")";
_socket1.Initialize("Socket1");
 }else {
 //BA.debugLineNum = 65;BA.debugLine="Socket1.Close";
_socket1.Close();
 //BA.debugLineNum = 66;BA.debugLine="Socket1.Initialize(\"Socket1\")";
_socket1.Initialize("Socket1");
 };
 //BA.debugLineNum = 68;BA.debugLine="Socket1.Connect(SeleccionarMando.nServer,Selecci";
_socket1.Connect(processBA,mostCurrent._seleccionarmando._nserver /*String*/ ,(int)(Double.parseDouble(mostCurrent._seleccionarmando._nport /*String*/ )),(int) (0));
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static boolean  _datos_validos() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Datos_Validos As Boolean";
 //BA.debugLineNum = 52;BA.debugLine="If MandoAire.myIp = \"127.0.0.1\" Then";
if ((mostCurrent._mandoaire._myip /*String*/ ).equals("127.0.0.1")) { 
 //BA.debugLineNum = 53;BA.debugLine="Msgbox(\"Verifica tu conexion a la nueva red\",\"AC";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Verifica tu conexion a la nueva red"),BA.ObjectToCharSequence("AC_Electronics"),mostCurrent.activityBA);
 //BA.debugLineNum = 54;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 56;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private SpinnerTeclas As Spinner";
mostCurrent._spinnerteclas = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private LabelTeclaAGrabar As Label";
mostCurrent._labelteclaagrabar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private BotonLimpiar As Button";
mostCurrent._botonlimpiar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private PanelTramaRecibida As Panel";
mostCurrent._paneltramarecibida = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private LabelTramaRecibida As Label";
mostCurrent._labeltramarecibida = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private BotonGrabarTecla As Button";
mostCurrent._botongrabartecla = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private EditTextTramaRecibida As EditText";
mostCurrent._edittexttramarecibida = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private BotonVolver As Button";
mostCurrent._botonvolver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private LabelInstruccion As Label";
mostCurrent._labelinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private BtnProbarTrama As Button";
mostCurrent._btnprobartrama = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private BtnIniciarComunicacion As Button";
mostCurrent._btniniciarcomunicacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Trama As String";
mostCurrent._trama = "";
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _guardartramaaire(int _numero) throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub GuardarTramaAire(numero As Int)";
 //BA.debugLineNum = 158;BA.debugLine="Connection.myCUR = Connection.mySQL.ExecQuery(\"SE";
mostCurrent._connection._mycur /*anywheresoftware.b4a.sql.SQL.CursorWrapper*/  = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._connection._mysql /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT Trama FROM TramasAire WHERE id='"+BA.NumberToString(_numero)+"'")));
 //BA.debugLineNum = 159;BA.debugLine="If Connection.myCUR.RowCount > 0 Then";
if (mostCurrent._connection._mycur /*anywheresoftware.b4a.sql.SQL.CursorWrapper*/ .getRowCount()>0) { 
 //BA.debugLineNum = 161;BA.debugLine="Connection.mySQL.ExecNonQuery(\"UPDATE TramasAire";
mostCurrent._connection._mysql /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery("UPDATE TramasAire SET Trama='"+mostCurrent._edittexttramarecibida.getText()+"' WHERE id='"+BA.NumberToString(_numero)+"'");
 }else {
 //BA.debugLineNum = 164;BA.debugLine="Connection.mySQL.ExecNonQuery(\"INSERT INTO Trama";
mostCurrent._connection._mysql /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery("INSERT INTO TramasAire (id,Trama) VALUES('"+BA.NumberToString(_numero)+"','"+mostCurrent._edittexttramarecibida.getText()+"')");
 };
 //BA.debugLineNum = 166;BA.debugLine="ToastMessageShow(\"Tecla Grabada\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Tecla Grabada"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Socket1 As Socket";
_socket1 = new anywheresoftware.b4a.objects.SocketWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Dim AStreams As AsyncStreams";
_astreams = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _socket1_connected(boolean _succesful) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Socket1_Connected(Succesful As Boolean)";
 //BA.debugLineNum = 74;BA.debugLine="If Succesful Then";
if (_succesful) { 
 //BA.debugLineNum = 75;BA.debugLine="AStreams.Initialize(Socket1.InputStream, Socket1";
_astreams.Initialize(processBA,_socket1.getInputStream(),_socket1.getOutputStream(),"AStreams");
 }else {
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
}
