﻿B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private LabelDispositivo As Label
	Private RadioTv As RadioButton
	Private RadioSonido As RadioButton
	Private RadioAireAc As RadioButton
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("LayoutEscogerDipositivo")

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub RadioAireAc_CheckedChange(Checked As Boolean)
	
End Sub

Private Sub RadioSonido_CheckedChange(Checked As Boolean)
	
End Sub

Private Sub RadioTv_CheckedChange(Checked As Boolean)
	
End Sub