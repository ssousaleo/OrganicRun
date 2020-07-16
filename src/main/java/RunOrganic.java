import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RunOrganic {
    private final static String EQUINOX = "/Applications/Eclipse.app/Contents/Eclipse/plugins/org.eclipse.equinox.launcher_1.5.700.v20200207-2156.jar";

    /**
     * Run the Organic to collect the smells
     * @param outputFile the path to the json file where the smells will be stored
     * @param srcPath the path to the src folder with the java classes to analyze
     */
    public static void run(String outputFile, String srcPath){
        String javaCmd = "java";
        Map map = new HashMap();
        map.put("EQUINOX", EQUINOX);
        map.put("ORGANIC", "organic.Organic");
        map.put("outputFile", outputFile);
        map.put("srcFolder", srcPath);

        CommandLine cmdLine = CommandLine.parse(javaCmd);
        cmdLine.addArgument("-jar");
        cmdLine.addArgument("-XX:MaxPermSize=2560m");
        cmdLine.addArgument("-Xmx2500m");
        cmdLine.addArgument("${EQUINOX}");
        cmdLine.addArgument("-application");
        cmdLine.addArgument("${ORGANIC}");
        cmdLine.addArgument("-sf");
        cmdLine.addArgument("${outputFile}");
        cmdLine.addArgument("-src");
        cmdLine.addArgument("${srcFolder}");
        cmdLine.setSubstitutionMap(map);

        DefaultExecutor executor = new DefaultExecutor();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        executor.setWatchdog(watchdog);
        try {
            int exitValue = executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
