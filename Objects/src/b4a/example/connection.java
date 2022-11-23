package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class connection {
private static connection mostCurrent = new connection();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _mysql = null;
public static anywheresoftware.b4a.sql.SQL.CursorWrapper _mycur = null;
public b4a.example.main _main = null;
public b4a.example.mandotv _mandotv = null;
public b4a.example.conectar _conectar = null;
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
public static String  _connectiondb(anywheresoftware.b4a.BA _ba) throws Exception{
String _condb = "";
 //BA.debugLineNum = 10;BA.debugLine="Sub ConnectionDB";
 //BA.debugLineNum = 12;BA.debugLine="Dim ConDB As String";
_condb = "";
 //BA.debugLineNum = 13;BA.debugLine="ConDB =DBUtils.CopyDBFromAssets(\"Tramas_DB.db\") '";
_condb = mostCurrent._dbutils._copydbfromassets /*String*/ (_ba,"Tramas_DB.db");
 //BA.debugLineNum = 14;BA.debugLine="mySQL.Initialize(ConDB, \"Tramas_DB.db\", True)";
_mysql.Initialize(_condb,"Tramas_DB.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim mySQL As SQL";
_mysql = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 7;BA.debugLine="Dim myCUR As Cursor";
_mycur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
