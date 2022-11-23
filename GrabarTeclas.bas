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
	
	Private SpinnerTeclas As Spinner
	Private LabelTeclaAGrabar As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("LayoutGrabarTeclas")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	SpinnerTeclas.AddAll(Array As String("Boton Power", "Boton +", "Boton -", "Boton Ok", "direcciones", "Botones Numericos"))
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub SeleccionarTecla
	Select SpinnerTeclas.SelectedIndex
		Case 0 
			ToastMessageShow("Tecla Grabada",False)
		Case 1
			ToastMessageShow("Tecla Grabada",False)
		Case 2
			ToastMessageShow("Tecla Grabada",False)
		Case 3
			ToastMessageShow("Tecla Grabada",False)
		Case 4
			ToastMessageShow("Tecla Grabada",False)
		Case 5
			ToastMessageShow("Tecla Grabada",False)
	End Select
End Sub


Private Sub SpinnerTeclas_ItemClick (Position As Int, Value As Object)
	SeleccionarTecla
End Sub