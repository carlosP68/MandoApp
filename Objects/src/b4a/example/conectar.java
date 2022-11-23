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

public class conectar extends Activity implements B4AActivity{
	public static conectar mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.conectar");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (conectar).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.conectar");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.conectar", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (conectar) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (conectar) Resume **");
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
		return conectar.class;
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
            BA.LogInfo("** Activity (conectar) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (conectar) Pause event (activity is not paused). **");
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
            conectar mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (conectar) Resume **");
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
public static anywheresoftware.b4a.objects.SocketWrapper.UDPSocket _udps = null;
public static anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket _udpp = null;
public static anywheresoftware.b4a.objects.SocketWrapper.ServerSocketWrapper _server = null;
public static String _shrip = "";
public static String _shrport = "";
public anywheresoftware.b4a.objects.PanelWrapper _panelheader = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelmyip = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelsender = null;
public anywheresoftware.b4a.objects.PanelWrapper _paneltextsend = null;
public anywheresoftware.b4a.objects.ButtonWrapper _buttonsend = null;
public anywheresoftware.b4a.objects.ButtonWrapper _buttonclearedittextsend = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelreceiver = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelreceiver = null;
public anywheresoftware.b4a.objects.ButtonWrapper _buttonclearedittextreceiver = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelheader = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittextsend = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittextreceiver = null;
public static String _destinationip = "";
public static String _destinationport = "";
public anywheresoftware.b4a.objects.ButtonWrapper _botonsiguiente = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelintruccion = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittextclave = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelclave = null;
public anywheresoftware.b4a.jalle007.wificonnect.WiFiConnect _wifi1 = null;
public b4a.example.main _main = null;
public b4a.example.mandotv _mandotv = null;
public b4a.example.connection _connection = null;
public b4a.example.dbutils _dbutils = null;
public b4a.example.grabarteclasaire _grabarteclasaire = null;
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
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 42;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bg.jpg").getObject()));
 //BA.debugLineNum = 43;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 44;BA.debugLine="UdpS.Initialize(\"UdpSEvent\", 2000, 1024)";
_udps.Initialize(processBA,"UdpSEvent",(int) (2000),(int) (1024));
 };
 //BA.debugLineNum = 46;BA.debugLine="Activity.LoadLayout(\"LayoutConectar\")";
mostCurrent._activity.LoadLayout("LayoutConectar",mostCurrent.activityBA);
 //BA.debugLineNum = 47;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bg.jpg").getObject()));
 //BA.debugLineNum = 48;BA.debugLine="LabelMyIP.Text = \"My IP : \" & server.GetMyWifiIP";
mostCurrent._labelmyip.setText(BA.ObjectToCharSequence("My IP : "+_server.GetMyWifiIP()));
 //BA.debugLineNum = 50;BA.debugLine="DestinationIp = ShrIP";
mostCurrent._destinationip = _shrip;
 //BA.debugLineNum = 51;BA.debugLine="DestinationPort = ShrPort";
mostCurrent._destinationport = _shrport;
 //BA.debugLineNum = 53;BA.debugLine="DestinationIp = \"192.168.4.1\"";
mostCurrent._destinationip = "192.168.4.1";
 //BA.debugLineNum = 54;BA.debugLine="DestinationPort = \"80\"";
mostCurrent._destinationport = "80";
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _botonsiguiente_click() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Private Sub BotonSiguiente_Click";
 //BA.debugLineNum = 87;BA.debugLine="StartActivity(SeleccionarMando)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._seleccionarmando.getObject()));
 //BA.debugLineNum = 88;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _buttonclearedittextreceiver_click() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Private Sub ButtonClearEditTextReceiver_Click";
 //BA.debugLineNum = 70;BA.debugLine="EditTextReceiver.Text = \"\"";
mostCurrent._edittextreceiver.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _buttonclearedittextsend_click() throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Private Sub ButtonClearEditTextSend_Click";
 //BA.debugLineNum = 74;BA.debugLine="EditTextSend.Text = \"\"";
mostCurrent._edittextsend.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 75;BA.debugLine="EditTextClave.Text = \"\"";
mostCurrent._edittextclave.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _buttonsend_click() throws Exception{
String _str = "";
 //BA.debugLineNum = 78;BA.debugLine="Private Sub ButtonSend_Click";
 //BA.debugLineNum = 79;BA.debugLine="Dim Str As String = EditTextSend.Text & \";\" & Edi";
_str = mostCurrent._edittextsend.getText()+";"+mostCurrent._edittextclave.getText();
 //BA.debugLineNum = 80;BA.debugLine="UdpP.Initialize(Str.GetBytes(\"UTF8\"), Destination";
_udpp.Initialize(_str.getBytes("UTF8"),mostCurrent._destinationip,(int)(Double.parseDouble(mostCurrent._destinationport)));
 //BA.debugLineNum = 81;BA.debugLine="UdpS.Send(UdpP)";
_udps.Send(_udpp);
 //BA.debugLineNum = 82;BA.debugLine="ToastMessageShow(\"Intentando conectar...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Intentando conectar..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 83;BA.debugLine="Wifi1.connectToSSID(Wifi1.WIFI_WPA, EditTextSend.";
mostCurrent._wifi1.connectToSSID(processBA,mostCurrent._wifi1.WIFI_WPA,mostCurrent._edittextsend.getText(),mostCurrent._edittextclave.getText());
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Private PanelHeader As Panel";
mostCurrent._panelheader = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private LabelMyIP As Label";
mostCurrent._labelmyip = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private LabelSender As Label";
mostCurrent._labelsender = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private PanelTextSend As Panel";
mostCurrent._paneltextsend = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private ButtonSend As Button";
mostCurrent._buttonsend = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private ButtonClearEditTextSend As Button";
mostCurrent._buttonclearedittextsend = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private LabelReceiver As Label";
mostCurrent._labelreceiver = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private PanelReceiver As Panel";
mostCurrent._panelreceiver = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private ButtonClearEditTextReceiver As Button";
mostCurrent._buttonclearedittextreceiver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private LabelHeader As Label";
mostCurrent._labelheader = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private EditTextSend As EditText";
mostCurrent._edittextsend = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private EditTextReceiver As EditText";
mostCurrent._edittextreceiver = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private DestinationIp As String";
mostCurrent._destinationip = "";
 //BA.debugLineNum = 32;BA.debugLine="Private DestinationPort As String";
mostCurrent._destinationport = "";
 //BA.debugLineNum = 33;BA.debugLine="Private BotonSiguiente As Button";
mostCurrent._botonsiguiente = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private LabelIntruccion As Label";
mostCurrent._labelintruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private EditTextClave As EditText";
mostCurrent._edittextclave = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private PanelClave As Panel";
mostCurrent._panelclave = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim Wifi1 As WiFiConnect";
mostCurrent._wifi1 = new anywheresoftware.b4a.jalle007.wificonnect.WiFiConnect();
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Public UdpS As UDPSocket";
_udps = new anywheresoftware.b4a.objects.SocketWrapper.UDPSocket();
 //BA.debugLineNum = 10;BA.debugLine="Public UdpP As UDPPacket";
_udpp = new anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket();
 //BA.debugLineNum = 11;BA.debugLine="Private server As ServerSocket";
_server = new anywheresoftware.b4a.objects.SocketWrapper.ServerSocketWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Public ShrIP, ShrPort As String";
_shrip = "";
_shrport = "";
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _udpsevent_packetarrived(anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket _packet) throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub UdpSEvent_PacketArrived (Packet As UDPPacket)";
 //BA.debugLineNum = 66;BA.debugLine="EditTextReceiver.Text = BytesToString(Packet.Data";
mostCurrent._edittextreceiver.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.BytesToString(_packet.getData(),_packet.getOffset(),_packet.getLength(),"ASCII")));
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
}
