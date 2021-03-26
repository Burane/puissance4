package utils;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;


public abstract class WindowsConsole {

	// changer le terminal en mode utf-8
	public static void changeCMDcodepage() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			Function SetConsoleCP = Function.getFunction("kernel32", "SetConsoleCP");
			Function SetConsoleOutputCP = Function.getFunction("kernel32", "SetConsoleOutputCP");
			int codepage = 65001;

			UINTByReference uintByReference = new UINTByReference(new UINT(0));

			UINT uintCodepage = uintByReference.getValue();

			uintCodepage.setValue(uintCodepage.intValue() | codepage);
			SetConsoleCP.invoke(BOOL.class, new Object[] { uintCodepage });
			SetConsoleOutputCP.invoke(BOOL.class, new Object[] { uintCodepage });
		}
	}

	// enable ANSI escape char for coloration
	public static void enableAnsiEscapeCMD() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
			DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
			HANDLE hOut = (HANDLE) GetStdHandleFunc.invoke(HANDLE.class, new Object[] { STD_OUTPUT_HANDLE });

			DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
			Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
			GetConsoleModeFunc.invoke(BOOL.class, new Object[] { hOut, p_dwMode });

			int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
			DWORD dwMode = p_dwMode.getValue();
			dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
			Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
			SetConsoleModeFunc.invoke(BOOL.class, new Object[] { hOut, dwMode });
		}
	}
}
