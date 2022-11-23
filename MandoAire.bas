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

	Private LabelTemp As Label
	Private BotonTempMas As Button
	Private BotonTempMenos As Button
	Private LabelMode As Label
	Private LabelOnOff As Label
	Private LabelFanSpeed As Label
	Private BotonPower As Button
	Private BotonMode As Button
	Private BotonFanSpeed As Button
	Private BotonSleep As Button
	Private BotonSwing As Button
	Private BotonTimerOn As Button
	Private LabelSwing As Label
	Private LabelSleep As Label
	Private LabelTimerOn As Label
	Private BotonTimerOff As Button
	Private LabelTimerOff As Label
	Private BotonLedDisplay As Button
	Private BotonAirDirection As Button
	Private BotonTurbo As Button
	Private LabelAirDirection As Label
	Private LabelLedDisplay As Label
	Private LabelTurbo As Label
	Private LabelVolver As Label
	Private BotonVolver As Button
	Private BotonGrabarTeclas As Button
	Private LabelGrabarTecla As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("layoutMandoAire")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	myIp=server.GetMyWifiIP
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

Private Sub BotonTurbo_Click
	transmiTrama(12)
End Sub
Private Sub BotonAirDirection_Click
	transmiTrama(8)
End Sub
Private Sub BotonLedDisplay_Click
	transmiTrama(9)
End Sub
Private Sub BotonTimerOff_Click
	transmiTrama(11)
End Sub
Private Sub BotonTimerOn_Click
	transmiTrama(10)
End Sub
Private Sub BotonSwing_Click
	transmiTrama(7)
End Sub
Private Sub BotonSleep_Click
	transmiTrama(5)
End Sub
Private Sub BotonFanSpeed_Click
	transmiTrama(6)
End Sub
Private Sub BotonMode_Click
	transmiTrama(4)
End Sub
Private Sub BotonPower_Click
	transmiTrama(1)
End Sub
Private Sub BotonTempMenos_Click
	transmiTrama(3)
End Sub
Private Sub BotonTempMas_Click
	transmiTrama(2)
End Sub
Private Sub BotonVolver_Click
	StartActivity(SeleccionarMando)
	Socket1.Close
	Activity.Finish
End Sub
Private Sub BotonGrabarTeclas_Click
	StartActivity(GrabarTeclasAire)
	Socket1.Close
	Activity.Finish
End Sub

Sub transmiTrama(numero As Int)
	If AStreams.IsInitialized = False Then Return
	If (myIp = "127.0.0.1") = False Then
		Connection.myCUR = Connection.mySQL.ExecQuery("SELECT Trama FROM TramasAire WHERE id='"& numero &"'") 'TramasTv = nombre de la tabla en la bd
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