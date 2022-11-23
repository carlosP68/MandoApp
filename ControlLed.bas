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

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Private ButtonBack As Button
	Private SwitchLED As Switch
	
	Dim DesIP,DesPort As String
	Dim StrCmd As String
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("LayoutControlLed")
	Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bg.jpg"))
	DesIP = "192.168.4.1"
	DesPort = "80"
	If DesIP = "" Or DesPort = "" Then
		ToastMessageShow("The Switch button is disabled because the Destination IP or Destination Port is Empty.", True)
		SwitchLED.Enabled = False
	Else
		SwitchLED.Enabled = True
	End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Private Sub SwitchLED_CheckedChange(Checked As Boolean)
	If Checked Then StrCmd = "led on" Else StrCmd = "led off"
	Conectar.UdpP.Initialize(StrCmd.GetBytes("ASCII"), DesIP, DesPort)
	Conectar.UdpS.Send(Conectar.UdpP)
End Sub

Private Sub ButtonBack_Click
	Conectar.ShrIP = DesIP
	Conectar.ShrPort = DesPort
	StartActivity(Conectar)
	Activity.Finish
End Sub