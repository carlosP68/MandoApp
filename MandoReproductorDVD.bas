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
	Dim Socket1 As Socket
	Dim AStreams As AsyncStreams
	Dim Trama As String
	Private server As ServerSocket
	Dim myIp As String
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private BotonPower As Button
	Private LabelOnOff As Label
	Private BotonSleep As Button
	Private LabelSleep As Label
	Private BotonDisplay As Button
	Private LabelDisplay As Label
	Private BotonSelect As Button
	Private LabelSelect As Label
	Private BotonSet As Button
	Private LabelSet As Label
	Private BotonTunerMemory As Button
	Private LabelTunerMemory As Label
	Private BotonPlayMode As Button
	Private LabelPlayMode As Label
	Private BotonRepeat As Button
	Private LabelRepeat As Label
	Private BotonUSB As Button
	Private LabelUSB As Label
	Private BotonCD As Button
	Private LabelCD As Label
	Private BotonTuner As Button
	Private LabelTuner As Label
	Private BotonlFunction As Button
	Private LabelFunction As Label
	Private BotonLess As Button
	Private LabelLess As Label
	Private BotonMore As Button
	Private LabelMore As Label
	Private BotonAnterior As Button
	Private BotonSiguiente As Button
	Private BotonPlay As Button
	Private BotonStop As Button
	Private BotonComplete As Button
	Private BotonMas As Button
	Private BotonMenos As Button
	Private LabelVolume As Label
	Private BotonVolver As Button
	Private BotonGrabarTeclas As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("layoutMandoReproductor")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	myIp=server.GetMyWifiIP
	'nServer = "192.168.0.19"
	'nPort = 8080
	ConectarServidor
End Sub

Sub Activity_Resume

End Sub
Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Datos_Validos As Boolean
	If myIp = "127.0.0.1" Then
		Msgbox("Verifica tu conexion a la nueva red","AC_Electronics")
		Return False
	End If
	Return True
End Sub
Sub ConectarServidor
	Try
		If Datos_Validos = False Then Return
		
		If Socket1.IsInitialized = False Then
			Socket1.Initialize("Socket1")
		Else
			Socket1.Close
			Socket1.Initialize("Socket1")
		End If
		Socket1.Connect(SeleccionarMando.nServer,SeleccionarMando.nPort,0)
	Catch
		'Log(LastException)
	End Try
End Sub
Sub Socket1_Connected(Succesful As Boolean)
	If Succesful Then
		AStreams.Initialize(Socket1.InputStream, Socket1.OutputStream, "AStreams")
	Else
		'ToastMessageShow(LastException.Message, True)
	End If
End Sub
Sub AStreams_Error
	'ToastMessageShow(LastException.Message,True)
End Sub

Private Sub BotonMenos_Click
	transmiTrama(3)
End Sub
Private Sub BotonMas_Click
	transmiTrama(2)
End Sub
Private Sub BotonComplete_Click
	transmiTrama(6)
End Sub
Private Sub BotonStop_Click
	transmiTrama(5)
End Sub
Private Sub BotonPlay_Click
	transmiTrama(4)
End Sub
Private Sub BotonSiguiente_Click
	transmiTrama(10)
End Sub
Private Sub BotonAnterior_Click
	transmiTrama(9)
End Sub
Private Sub BotonMore_Click
	transmiTrama(8)
End Sub
Private Sub BotonLess_Click
	transmiTrama(7)
End Sub
Private Sub BotonlFunction_Click
	transmiTrama(14)
End Sub
Private Sub BotonTuner_Click
	transmiTrama(13)
End Sub
Private Sub BotonCD_Click
	transmiTrama(12)
End Sub
Private Sub BotonUSB_Click
	transmiTrama(11)
End Sub
Private Sub BotonRepeat_Click
	transmiTrama(20)
End Sub
Private Sub BotonPlayMode_Click
	transmiTrama(19)
End Sub
Private Sub BotonTunerMemory_Click
	transmiTrama(18)
End Sub
Private Sub BotonSet_Click
	transmiTrama(17)
End Sub
Private Sub BotonSelect_Click
	transmiTrama(16)
End Sub
Private Sub BotonDisplay_Click
	transmiTrama(15)
End Sub
Private Sub BotonSleep_Click
	transmiTrama(21)
End Sub
Private Sub BotonPower_Click
	transmiTrama(1)
End Sub
Private Sub BotonVolver_Click
	StartActivity(SeleccionarMando)
	Socket1.Close
	Activity.Finish
End Sub
Private Sub BotonGrabarTeclas_Click
	StartActivity(GrabarTeclasReproductor)
	Socket1.Close
	Activity.Finish
End Sub

Sub transmiTrama(numero As Int)
	If AStreams.IsInitialized = False Then Return
	If (myIp = "127.0.0.1") = False Then
		Connection.myCUR = Connection.mySQL.ExecQuery("SELECT Trama FROM TramasReproductor WHERE id='"& numero &"'") 'TramasTv = nombre de la tabla en la bd
		For i = 0 To Connection.myCUR.RowCount - 1
			Connection.myCUR.Position = i
			Trama = Connection.myCUR.getString("Trama")
			Dim buffer() As Byte
			Trama = Trama & Chr(10) & Chr(13)
			buffer = Trama.GetBytes("UTF8")
			AStreams.Write(buffer)
		Next
	End If
End Sub