B4A=true
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

	Private Boton1 As Button
	Private Boton2 As Button
	Private Boton3 As Button
	Private Boton4 As Button
	Private Boton5 As Button
	Private Boton6 As Button
	Private Boton7 As Button
	Private Boton8 As Button
	Private Boton9 As Button
	Private Boton0 As Button
	Private BotonesDireccion As Button
	Private BotonPower As Button
	Private BotonCanalArriba As Button
	Private BotonCanalAbajo As Button
	Private LabelCanal As Label
	Private BotonMas As Button
	Private BotonMenos As Button
	Private LabelVolumen As Label
	Private BotonGrabarTeclas As Button
	Private Salir As Button
	Private ToggleMute As ToggleButton
	Private BotonVolver As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("layoutBtnNumericos")
	BotonPower.Color = 0xFFFF0000
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	
End Sub

Private Sub BotonesDireccion_Click
	StartActivity(MandoTV)
End Sub

Private Sub Boton0_Click
	
End Sub

Private Sub Boton9_Click
	
End Sub

Private Sub Boton8_Click
	
End Sub

Private Sub Boton7_Click
	
End Sub

Private Sub Boton6_Click
	
End Sub

Private Sub Boton5_Click
	
End Sub

Private Sub Boton4_Click
	
End Sub

Private Sub Boton3_Click
	
End Sub

Private Sub Boton2_Click
	
End Sub

Private Sub Boton1_Click
	
End Sub

Private Sub BotonMute_Click
	
End Sub

Private Sub Salir_Click
	
End Sub

Private Sub BotonGrabarTeclas_Click
	StartActivity(GrabarTeclasTv)
End Sub

Private Sub LabelVolumen_Click
	
End Sub

Private Sub BotonMenos_Click
	
End Sub

Private Sub BotonMas_Click
	
End Sub

Private Sub LabelCanal_Click
	
End Sub

Private Sub BotonCanalAbajo_Click
	
End Sub

Private Sub BotonCanalArriba_Click
	
End Sub

Private Sub BotonPower_Click
	
End Sub

Private Sub ToggleMute_CheckedChange(Checked As Boolean)
	If Checked == True Then
		ToastMessageShow("Mute On",False)
	Else If Checked == False Then
		ToastMessageShow("Mute Off",False)
	End If
End Sub

Private Sub BotonVolver_Click
	StartActivity(MandoTV)
	Activity.Finish
End Sub