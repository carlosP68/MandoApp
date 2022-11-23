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
	Private BotonCanalArriba As Button
	Private BotonCanalAbajo As Button
	Private LabelCanal As Label
	Private BotonMas As Button
	Private BotonMenos As Button
	Private LabelVolumen As Label
	Private BotonGrabarTeclas As Button
	Private BotonVolver As Button
	Private BotonArriba As Button
	Private BotonAbajo As Button
	Private BotonIzquierda As Button
	Private BotonDerecha As Button
	Private BotonOk As Button
	Private BotonMenu As Button
	Private BotonTvAv As Button
	Private BotonGuia As Button
	Private BotonPrev As Button
	Private BotonNumerico As Button
	Private BotonMute As Button
	Private BotonPowerDeco As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("layoutMandoDecoder")
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

Private Sub BotonOk_Click
	transmiTrama(12)
End Sub
Private Sub BotonDerecha_Click
	transmiTrama(16)
End Sub
Private Sub BotonIzquierda_Click
	transmiTrama(15)
End Sub
Private Sub BotonAbajo_Click
	transmiTrama(14)
End Sub
Private Sub BotonArriba_Click
	transmiTrama(13)
End Sub
Private Sub BotonMenos_Click
	transmiTrama(4)
End Sub
Private Sub BotonMas_Click
	transmiTrama(3)
End Sub
Private Sub BotonCanalAbajo_Click
	transmiTrama(6)
End Sub
Private Sub BotonCanalArriba_Click
	transmiTrama(5)
End Sub
Private Sub BotonPower_Click
	transmiTrama(1)
End Sub
Private Sub BotonPrev_Click
	transmiTrama(10)
End Sub
Private Sub BotonGuia_Click
	transmiTrama(8)
End Sub
Private Sub BotonTvAv_Click
	transmiTrama(7)
End Sub
Private Sub BotonMenu_Click
	transmiTrama(9)
End Sub
Private Sub BotonMute_Click
	transmiTrama(11)
End Sub
Private Sub BotonPowerDeco_Click
	transmiTrama(2)
End Sub
Private Sub BotonVolver_Click
	StartActivity(SeleccionarMando)
	Socket1.Close
	Activity.Finish
End Sub
Private Sub BotonGrabarTeclas_Click
	StartActivity(GrabarTeclasDecoder)
	Socket1.Close
	Activity.Finish
End Sub
Private Sub BotonNumerico_Click
	StartActivity(MandoDecoderNumerico)
	Socket1.Close
	Activity.Finish
End Sub

Sub transmiTrama(numero As Int)
	If AStreams.IsInitialized = False Then Return
	If (myIp = "127.0.0.1") = False Then
		Connection.myCUR = Connection.mySQL.ExecQuery("SELECT Trama FROM TramasDecodificador WHERE id='"& numero &"'") 'TramasTv = nombre de la tabla en la bd
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