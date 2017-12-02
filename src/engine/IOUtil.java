package engine;

import java.util.Scanner;

public class IOUtil {
	// dynopoly ascii wording developed using
	// http://patorjk.com/software/taag/#p=display&f=Mer&t=Dynopoly!%20
	// world ascii art found at http://www.chris.com/ascii/
	public static final String logo =  
	  "             __gggrgM**M#mggg__" + System.lineSeparator()
	+ "         __wgNN@\"B*P\"\"mp\"\"@d#\"@N#Nw__" + System.lineSeparator()
	+ "       _g#@0F_a*F#  _*F9m_ ,F9*__9NG#g_" + System.lineSeparator()
	+ "    _mN#F  aM\"    #p\"    !q@    9NL \"9#Qu_" + System.lineSeparator()
	+ "   g#MF _pP\"L  _g@\"9L_  _g\"\"#__  g\"9w_ 0N#p" + System.lineSeparator()
	+ " _0F jL*\"   7_wF     #_gF     9gjF   \"bJ  9h_" + System.lineSeparator()
	+ "j#  gAF    _@NL     _g@#_      J@u_    2#_  #_" + System.lineSeparator()
	+ ",FF_#\" 9_ _#\"  \"b_  g@   \"hg  _#\"  !q_ jF \"*_09_" + System.lineSeparator()
	+ "F N\"    #p\"      Ng@       `#g\"      \"w@    \"# t" + System.lineSeparator()
	+ "j p#    g\"9_     g@\"9_      gP\"#_     gF\"q    Pb L" + System.lineSeparator()
	+ "0J  k _@   9g_ j#\"   \"b_  j#\"   \"b_ _d\"   q_ g  ##" + System.lineSeparator()
	+ "#F  `NF     \"#g\"       \"Md\"       5N#      9W\"  j#" + System.lineSeparator()
	+ "  _____                                _       _  " + System.lineSeparator()
	+ " |  __ \\                              | |     | | " + System.lineSeparator()
	+ " | |  | |_   _ _ __   ___  _ __   ___ | |_   _| |" + System.lineSeparator()
	+ " | |  | | | | | '_ \\ / _ \\| '_ \\ / _ \\| | | | | | " + System.lineSeparator()
	+ " | |__| | |_| | | | | (_) | |_) | (_) | | |_| |_| " + System.lineSeparator()
	+ " |_____/ \\__, |_| |_|\\___/| .__/ \\___/|_|\\__, (_) " + System.lineSeparator()
	+ "          __/ |           | |             __/ | " + System.lineSeparator()
	+ "         |___/            |_|            |___/   " + System.lineSeparator()
 
	+ "#k  jFb_    g@\"q_     _*\"9m_     _*\"R_    _#Np  J#" + System.lineSeparator()
	+ "tApjF  9g  J\"   9M_ _m\"    9%_ _*\"   \"#  gF  9_jNF" + System.lineSeparator()
	+ "k`N    \"q#       9g@        #gF       ##\"    #\"j" + System.lineSeparator()
	+ "`_0q_   #\"q_    _&\"9p_    _g\"`L_    _*\"#   jAF,'" + System.lineSeparator()
	+ "9# \"b_j   \"b_ g\"    *g _gF    9_ g#\"  \"L_*\"qNF" + System.lineSeparator()
	+ " \"b_ \"#_    \"NL      _B#      _I@     j#\" _#\"" + System.lineSeparator()
	+ "   NM_0\"*g_ j\"\"9u_  gP  q_  _w@ ]_ _g*\"F_g@" + System.lineSeparator()
	+ "    \"NNh_ !w#_   9#g\"    \"m*\"   _#*\" _dN@\"" + System.lineSeparator()
	+ "       9##g_0@q__ #\"4_  j*\"k __*NF_g#@P\"" + System.lineSeparator()
	+ "         \"9NN#gIPNL_ \"b@\" _2M\"Lg#N@F\"" + System.lineSeparator()
	+ "             \"\"P@*NN#gEZgNN@#@P\"\"";
	
	static boolean askUserQuestion(Scanner in) {
		String answer = in.nextLine();
		while (!(answer.startsWith("Y") || answer.startsWith("N"))) {
			System.out.println("Please enter either Y/N.");
			answer = Main.in.nextLine();
		}
		return answer.startsWith("Y");
	}

	public static void printLogo() {
		System.out.println(logo);
	}

}
