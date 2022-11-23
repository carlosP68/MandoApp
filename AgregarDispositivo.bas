B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private LabelNombreNuevoDispositivo As Label
	Private EditTextNombreNuevoDisp As EditText
	Private CheckBoxPower As CheckBox
	Private CheckBoxDirecciones As CheckBox
	Private CheckBoxNumeros As CheckBox
	Private LabelBotonAgg As Label
	Private CheckBoxOK As CheckBox
	Private BotonGuardar As Button
	Private CheckBoxMenu As CheckBox
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("LayoutNuevoDisp")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Private Sub BotonAgregarDispositivo_Click
	
End Sub

Private Sub CheckBoxMenu_CheckedChange(Checked As Boolean)
	
End Sub

Private Sub BotonGuardar_Click
	StartActivity(SeleccionarMando)
End Sub

Private Sub CheckBoxOK_CheckedChange(Checked As Boolean)
	
End Sub

Private Sub CheckBoxNumeros_CheckedChange(Checked As Boolean)
	
End Sub

Private Sub CheckBoxDirecciones_CheckedChange(Checked As Boolean)
	
End Sub

Private Sub CheckBoxPower_CheckedChange(Checked As Boolean)
	
End Sub