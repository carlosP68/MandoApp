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
	Dim nServer As String = "192.168.0.13"
	Dim nPort As String = 8080
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Private MandoDeco As Button
	Private MandoReproductor As Button
	Private MandoTelevision As Button
	Private MandoAireAcon As Button
	Private BotonVolver As Button
	Private LabelHeader As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("SeleccionarMando")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Private Sub MandoReproductor_Click
	StartActivity(MandoReproductorDVD)
End Sub

Private Sub MandoDeco_Click
	StartActivity(MandoDecodificador)
End Sub

Private Sub MandoAireAcon_Click
	StartActivity(MandoAire)
End Sub

Private Sub MandoTelevision_Click
	StartActivity(MandoTV)
End Sub

Private Sub BotonVolver_Click
	StartActivity(Main)
	Activity.Finish
End Sub