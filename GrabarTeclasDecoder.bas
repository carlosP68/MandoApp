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
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private SpinnerTeclas As Spinner
	Private LabelTeclaAGrabar As Label
	Private BotonLimpiar As Button
	Private PanelTramaRecibida As Panel
	Private LabelTramaRecibida As Label
	Private BotonGrabarTecla As Button
	Private EditTextTramaRecibida As EditText
	'Private nServer As String
	'Private nPort As String
	Private BotonVolver As Button
	Private LabelInstruccion As Label
	Private BtnProbarTrama As Button
	Private BtnIniciarComunicacion As Button
	Private Trama As String
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("LayoutGrabarTeclasDecoder")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	SpinnerTeclas.AddAll(Array As String("Power","Power Deco","Volumen +", "Volumen -","Canal +","Canal -","Tv/Av", "Guia", "Menu","Prev","Mute","Ok","Arriba","Abajo","Izquierda","Derecha","numero 1","numero 2","numero 3","numero 4","numero 5","numero 6","numero 7","numero 8","numero 9","numero 0"))
	ConectarServidor
End Sub

Sub Activity_Resume

End Sub
Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Datos_Validos As Boolean
	If MandoDecodificador.myIp = "127.0.0.1" Then
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
Sub AStreams_NewData(Buffer() As Byte)
	Dim msg As String
	msg = BytesToString(Buffer,0,Buffer.Length,"UTF8")
	ToastMessageShow(msg,False)
	EditTextTramaRecibida.Text = ""
	EditTextTramaRecibida.Text = msg
End Sub
Sub AStreams_Error
	'ToastMessageShow(LastException.Message,True)
End Sub


Private Sub BotonGrabarTecla_Click
	'comando para guardar en la base de datos lo que esta en EditTextTramaRecibida indicandole lo
	'que se marca en el spinner
	
	If EditTextTramaRecibida.Text = "" Then
		ToastMessageShow("La trama no puede estar vacía",False)
	End If
	
	Select SpinnerTeclas.SelectedIndex
		Case 0
			GuardarTramaDeco(1)
		Case 1
			GuardarTramaDeco(2)
		Case 2
			GuardarTramaDeco(3)
		Case 3
			GuardarTramaDeco(4)
		Case 4
			GuardarTramaDeco(5)
		Case 5
			GuardarTramaDeco(6)
		Case 6
			GuardarTramaDeco(7)
		Case 7
			GuardarTramaDeco(8)
		Case 8
			GuardarTramaDeco(9)
		Case 9
			GuardarTramaDeco(10)
		Case 10
			GuardarTramaDeco(11)
		Case 11
			GuardarTramaDeco(12)
		Case 12
			GuardarTramaDeco(13)
		Case 13
			GuardarTramaDeco(14)
		Case 14
			GuardarTramaDeco(15)
		Case 15
			GuardarTramaDeco(16)
		Case 16
			GuardarTramaDeco(17)
		Case 17
			GuardarTramaDeco(18)
		Case 18
			GuardarTramaDeco(19)
		Case 19
			GuardarTramaDeco(20)
		Case 20
			GuardarTramaDeco(21)
		Case 21
			GuardarTramaDeco(22)
		Case 22
			GuardarTramaDeco(23)
		Case 23
			GuardarTramaDeco(24)
		Case 24
			GuardarTramaDeco(25)
		Case 25
			GuardarTramaDeco(26)
	End Select
End Sub
Private Sub BotonLimpiar_Click
	EditTextTramaRecibida.Text = ""
End Sub
Private Sub BotonVolver_Click
	StartActivity(MandoDecodificador)
	Socket1.Close
	Activity.Finish
End Sub
Private Sub BtnIniciarComunicacion_Click
	
	If AStreams.IsInitialized = False Then Return
	
	If MandoDecodificador.myIp.Length > 0 Then
		Dim buffer() As Byte
		Dim str As String = MandoDecodificador.myIp
		str = str & Chr(10) & Chr(13)
		buffer = str.GetBytes("UTF8")
		AStreams.Write(buffer)
	End If
End Sub
Private Sub BtnProbarTrama_Click
	'probar la trama que se encuentra en el edittext de recepcion
	If AStreams.IsInitialized = False Then Return
	If (MandoTV.MyIp = "127.0.0.1") = False Then
		Trama = EditTextTramaRecibida.Text
		Dim buffer() As Byte
		Trama = Trama & Chr(10) & Chr(13)
		buffer = Trama.GetBytes("UTF8")
		AStreams.Write(buffer)
	End If
End Sub

Sub GuardarTramaDeco(numero As Int)
	Connection.myCUR = Connection.mySQL.ExecQuery("SELECT Trama FROM TramasDecodificador WHERE id='"& numero &"'") 'TramasTv = nombre de la tabla en la bd
	If Connection.myCUR.RowCount > 0 Then
		'update
		Connection.mySQL.ExecNonQuery("UPDATE TramasDecodificador SET Trama='" & EditTextTramaRecibida.Text & "' WHERE id='" & numero & "'") 'contacts_table = table name in the database
	Else
		'save
		Connection.mySQL.ExecNonQuery("INSERT INTO TramasDecodificador (id,Trama) VALUES('" & numero & "','" & EditTextTramaRecibida.Text & "')") 'contacts_table = table name in the database
	End If
	ToastMessageShow("Tecla Grabada",False)
End Sub