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
	Public UdpS As UDPSocket
	Public UdpP As UDPPacket
	Private server As ServerSocket
	Public ShrIP, ShrPort As String
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private PanelHeader As Panel
	Private LabelMyIP As Label
	Private LabelSender As Label
	Private PanelTextSend As Panel
	Private ButtonSend As Button
	Private ButtonClearEditTextSend As Button
	Private LabelReceiver As Label
	Private PanelReceiver As Panel
	Private ButtonClearEditTextReceiver As Button
	Private LabelHeader As Label
	Private EditTextSend As EditText
	Private EditTextReceiver As EditText
	Private DestinationIp As String
	Private DestinationPort As String
	Private BotonSiguiente As Button
	Private LabelIntruccion As Label
	Private EditTextClave As EditText
	Private PanelClave As Panel
	Dim Wifi1 As WiFiConnect
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	If FirstTime Then
		UdpS.Initialize("UdpSEvent", 2000, 1024)
	End If
	Activity.LoadLayout("LayoutConectar")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	LabelMyIP.Text = "My IP : " & server.GetMyWifiIP
	
	DestinationIp = ShrIP
	DestinationPort = ShrPort
	
	DestinationIp = "192.168.4.1"
	DestinationPort = "80"
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub UdpSEvent_PacketArrived (Packet As UDPPacket)
	EditTextReceiver.Text = BytesToString(Packet.Data, Packet.Offset, Packet.Length, "ASCII")
End Sub

Private Sub ButtonClearEditTextReceiver_Click
	EditTextReceiver.Text = ""
End Sub

Private Sub ButtonClearEditTextSend_Click
	EditTextSend.Text = ""
	EditTextClave.Text = ""
End Sub

Private Sub ButtonSend_Click
	Dim Str As String = EditTextSend.Text & ";" & EditTextClave.Text
	UdpP.Initialize(Str.GetBytes("UTF8"), DestinationIp, DestinationPort)
	UdpS.Send(UdpP)
	ToastMessageShow("Intentando conectar...",False)
	Wifi1.connectToSSID(Wifi1.WIFI_WPA, EditTextSend.Text, EditTextClave.Text)
End Sub

Private Sub BotonSiguiente_Click
	StartActivity(SeleccionarMando)
	Activity.Finish
End Sub