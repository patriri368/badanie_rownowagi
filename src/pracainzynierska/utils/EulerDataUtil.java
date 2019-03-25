package pracainzynierska.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EulerDataUtil {

    public static Map<String, List<EulerAngles>> readExaminationData() throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("data.txt"))) {

            String inputData;
            String[] eulerDataBuffer;

            List<EulerAngles> eulerData1 = new ArrayList<>();
            List<EulerAngles> eulerData2 = new ArrayList<>();

            while ((inputData = bufferedReader.readLine()) != null) {

                eulerDataBuffer = inputData.split(" ");

                if (eulerDataBuffer[0].equals("euler0:")) {
                    addToAnglesList(eulerDataBuffer, eulerData1);
                } else if (eulerDataBuffer[0].equals("euler1:")) {
                    addToAnglesList(eulerDataBuffer, eulerData2);
                }
            }

            Map<String, List<EulerAngles>> eulerAnglesMap = new HashMap<>();
            eulerAnglesMap.put("euler0", eulerData1);
            eulerAnglesMap.put("euler1", eulerData2);

            return eulerAnglesMap;
        }
    }

    private static void addToAnglesList(String[] eulerDataBuffer, List<EulerAngles> eulerData) {
        EulerAngles eulerAngles = new EulerAngles(
                Double.parseDouble(eulerDataBuffer[1]),
                Double.parseDouble(eulerDataBuffer[2]),
                Double.parseDouble(eulerDataBuffer[3])
        );

        eulerData.add(eulerAngles);
    }
}