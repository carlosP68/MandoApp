B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11.8
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim mySQL As SQL
	Dim myCUR As Cursor
End Sub

Sub ConnectionDB

	Dim ConDB As String
	ConDB =DBUtils.CopyDBFromAssets("Tramas_DB.db") 'contacs_db = database name
	mySQL.Initialize(ConDB, "Tramas_DB.db", True)
End Sub