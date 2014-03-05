package com.silveroaklabs.constant;

public final class ShapError {
	private int mError;
	public static ShapError create(int error)
	{
		return new ShapError(error);
	}
	public static ShapError create(int errorClass, int errorCode)
	{
		return new ShapError(errorClass, errorCode);
	}
	public static int makeErrorNumber(int errorClass, int errorCode)
	{
		return (errorClass&0xff)<<24 | (errorCode&0x00ffffff);
	}
	public ShapError(int error)
	{
		mError = error;
	}
	public ShapError(int errorClass, int errorCode)
	{
		mError = makeErrorNumber(errorClass, errorCode);
	}
	public String toString()
	{
		return String.format("Error{\"class\":%d, \"code\":%d}", getErrorClass(), getErrorCode());
	}
	public int getError() { return mError; }
	public int getErrorClass() { return mError>>>24; }
	public int getErrorCode() { return mError&0x00ffffff; }
	
	public interface Class {
		public static final int Device = 0;
		public static final int Object = 1;
		public static final int Property = 2;
		public static final int Resources = 3;
		public static final int Security = 4;
		public static final int Services = 5;
		public static final int File = 6;
		public static final int User = 7;
		public static final int Account = 8;
		public static final int Abort = 9;
		public static final int Reject = 10;
	}
	public interface Code {
		public static final int UnknownDevice = 1;
		public static final int UnknownObject= 2;
		public static final int UnknownProperty = 3;
		public static final int InvalidDataType = 4;
		public static final int ValueOutOfRange = 5;
		public static final int FileAccessDenied = 6;
		public static final int ReadAccessDenied = 7;
		public static final int WriteAccessDenied = 8;
		public static final int InvokeAccessDenied = 9;
		public static final int InvalidFileStartPosition = 10;
		public static final int NoSpaceToWriteProperty = 11;
		public static final int AbortBufferOverflow = 12;
		public static final int RejectBufferOverflow = 13;
		public static final int RejectInconsistentParameters = 14;
		public static final int RejectInvalidParameterDataType = 15;
		public static final int RejectMissingRequiredParameter = 16;
		public static final int RejectParameterOutOfRange = 17;
		public static final int RejectTooManyArguments = 18;
		public static final int RejectUnrecognizedService = 19;
		public static final int RrejectInvalidVDE = 20;
		public static final int RrejectUnsupported = 21;
		public static final int RrejectNotImplemented = 22;
		public static final int RrejectOther = 23;
		public static final int InvalidAck = 24;
		public static final int ServiceTimeout = 25;
		public static final int ServiceException = 26;
	}
}
