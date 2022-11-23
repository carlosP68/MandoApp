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
	Activity.LoadLayout("LayoutGrabarTeclasAire")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	'nServer = "192.168.0.19"
	'nPort = 8080
	SpinnerTeclas.AddAll(Array As String("Power","Temperatura +", "Temperatura -","Mode","Sleep","Fan Speed", "Swing","Air Direction","Led Display", "Timer On","Timer Off","Turbo"))
	ConectarServidor
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Datos_Validos As Boolean
	If MandoAire.myIp = "127.0.0.1" Then
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
			GuardarTramaAire(1)
		Case 1
			GuardarTramaAire(2)
		Case 2
			GuardarTramaAire(3)
		Case 3
			GuardarTramaAire(4)
		Case 4
			GuardarTramaAire(5)
		Case 5
			GuardarTramaAire(6)
		Case 6
			GuardarTramaAire(7)
		Case 7
			GuardarTramaAire(8)
		Case 8
			GuardarTramaAire(9)
		Case 9
			GuardarTramaAire(10)
		Case 10
			GuardarTramaAire(11)
		Case 11
			GuardarTramaAire(12)
	End Select
End Sub
Private Sub BotonLimpiar_Click
	EditTextTramaRecibida.Text = ""
End Sub
Private Sub BotonVolver_Click
	StartActivity(MandoAire)
	Socket1.Close
	Activity.Finish
End Sub
Private Sub BtnIniciarComunicacion_Click
	
	If AStreams.IsInitialized = False Then Return
	
	If MandoAire.myIp.Length > 0 Then
		Dim buffer() As Byte
		Dim str As String = MandoAire.myIp
		str = str & Chr(10) & Chr(13)
		buffer = str.GetBytes("UTF8")
		AStreams.Write(buffer)
	End If
End Sub
Private Sub BtnProbarTrama_Click
	'probar la trama que se encuentra en el edittext de recepcion
	If AStreams.IsInitialized = False Then Return
	If (MandoAire.MyIp = "127.0.0.1") = False Then
		Trama = EditTextTramaRecibida.Text
		Dim buffer() As Byte
		Trama = Trama & Chr(10) & Chr(13)
		buffer = Trama.GetBytes("UTF8")
		AStreams.Write(buffer)
	End If
End Sub

Sub GuardarTramaAire(numero As Int)
	Connection.myCUR = Connection.mySQL.ExecQuery("SELECT Trama FROM TramasAire WHERE id='"& numero &"'") 'TramasTv = nombre de la tabla en la bd
	If Connection.myCUR.RowCount > 0 Then
		'update
		Connection.mySQL.ExecNonQuery("UPDATE TramasAire SET Trama='" & EditTextTramaRecibida.Text & "' WHERE id='" & numero & "'") 'contacts_table = table name in the database
	Else
		'save
		Connection.mySQL.ExecNonQuery("INSERT INTO TramasAire (id,Trama) VALUES('" & numero & "','" & EditTextTramaRecibida.Text & "')") 'contacts_table = table name in the database
	End If
	ToastMessageShow("Tecla Grabada",False)
End Sub