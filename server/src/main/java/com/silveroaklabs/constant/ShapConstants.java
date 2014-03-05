package com.silveroaklabs.constant;

public interface ShapConstants {
	public interface PduType {
		public static final int ConfirmedReq = 0;
		public static final int UnconfirmedReq = 1;
		public static final int SimpleAck = 2;
		public static final int ComplexAck = 3;
		public static final int Error = 4;
	}

	public interface Vde {
		public static final int JSON = 0;
		public static final int BINARY = 1;
	}
	public interface ServiceType {
		public static final int ReadProperty = 0;
		public static final int ReadPropertyMultiple = 1;
		public static final int WriteProperty = 2;
		public static final int WritePropertyMultiple = 3;
		public static final int ReadFile = 4;
		public static final int WriteFile = 5;
		public static final int InvokeMethod = 6;
		public static final int ReceiveRequest = 7;
		public static final int SendRequest = 8;
		public static final int WhoIs = 80;
		public static final int IAm = 81;
	}
	public interface PropertyId {
		public static final int Application_Software_Version = 11;
		public static final int Bind_Device = 36;
		public static final int Change_User_Password = 34;
		public static final int Create_User = 30;
		public static final int Date_Time = 17;
		public static final int Description = 9;
		public static final int Effective_Period = 18;
		public static final int Enable_Binding = 35;
		public static final int Exception_Schedule = 19;
		public static final int File_Attribute = 14;
		public static final int File_Size = 15;
		public static final int File_Type = 16;
		public static final int Firmware_Version = 10;
		public static final int Get_Account_Information = 40;
		public static final int Mac_Address = 26;
		public static final int Max_APDU_Length = 13;
		public static final int Minimum_Off_Time = 27;
		public static final int Minimum_On_Time = 28;
		public static final int Logon_User = 31;
		public static final int Logout_User = 32;
		public static final int Object_Identifier = 1;
		public static final int Object_Name = 2;
		public static final int Object_List = 3;
		public static final int Present_Value = 4;
		public static final int Priority_Array = 5;
		public static final int Program_Change = 22;
		public static final int Program_Config = 23;
		public static final int Program_State = 24;
		public static final int Protocol_Version = 12;
		public static final int Register_Device = 29;
		public static final int Relinquish_Default = 6;
		public static final int Schedule_Default = 21;
		public static final int Send_Notification = 39;
		public static final int Serial_Number = 25;
		public static final int Set_Account_Information = 41;
		public static final int Store_Data = 38;
		public static final int Status_Flags = 7;
		public static final int Unbind_Device = 37;
		public static final int Units = 8;
		public static final int Upgrade_Firmware = 33;
		public static final int Weekly_Schedule = 20;

        public static final int Find_Device_User = 50;

		//extension properties
		public static final int Change_Of_State_Count = 513;
		public static final int Change_Of_State_Count_Transition = 514;
		public static final int Change_Of_State_Time = 515;
		public static final int Elapsed_Active_Time = 517;
		public static final int Time_Of_Active_Time_Reset = 518;
		public static final int Time_Of_State_Count_Reset = 516;
		public static final int Time_Of_Total_Reset = 520;
		public static final int Total = 519;
		public static final int Totalization_Interval = 521;
	}
	public interface ObjectType {
		public static final int Device = 0;
		public static final int BinaryInput = 1;
		public static final int BinaryOutput = 2;
		public static final int MultistateInput = 3;
		public static final int MultistateOutput = 4;
		public static final int AnalogInput = 5;
		public static final int AnalogOutput = 6;
		public static final int File = 7;
		public static final int Schedule = 8;
		public static final int Calender = 9;
		public static final int Program = 10;
		public static final int UserAccountService = 11;
		public static final int DeviceService = 12;
	}
	public interface EngineeringUnits {
		public static final int No_Units = 95;
	}
	public interface ProgramState {
		public static final int Idle = 0;
		public static final int Loading = 1;
		public static final int Running = 2;
		public static final int Waiting = 3;
		public static final int Halted = 4;
		public static final int Unloading = 5;
	}
	public interface ProgramChange {
		public static final int Ready = 0;
		public static final int Load = 1;
		public static final int Run = 2;
		public static final int Halt = 3;
		public static final int Restart = 4;
		public static final int Unload = 5;
	}
	public interface FileAttribute {
		public static final int NoAccess = 0;
		public static final int ReadOnly = 1;
		public static final int WriteOnly = 2;
		public static final int ReadWrite = 3;
	}
}
