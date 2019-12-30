package api;

import data.object.IncomingRequest;
import json.Parser;
import staticAccess.FileSystem;
import staticAccess.Helper;
import task.PremiumCalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

public class EntryPoint {
    public static void main(String[] args) {
        String filePath = args[0];
        File file = new File(filePath);

        try {
            if (file.isDirectory()) {
                handleDirectory(filePath).dispatchResponse();
            } else {
                handleFile(file).dispatchResponse();
            }
        } catch (Exception e) {
            Helper.handleException(e);
            Response errorResponse = new Response(e);
            errorResponse.dispatchResponse();
        }
    }

    private static Response handleDirectory(String filePath) {
        boolean handled = false;

        List<File> files = FileSystem.getAllDirectoryFiles(filePath, false);
        List<Response> responses = new ArrayList<>();

        for (File currentFile : files) {
            try {
                responses.add(handleFile(currentFile));

                handled = true;
            } catch (Exception e) {
                Helper.handleException(e);
                responses.add(new Response(e));
            }
        }

        if (!handled) {
            throw new RuntimeException("there are no valid files in folder");
        }

        return new Response(responses);
    }

    private static Response handleFile(File file) throws Exception {
        if (!file.getName().matches(".*\\.json")) {
            throw new InvalidPropertiesFormatException("file should be json");
        }

        Parser parser = new Parser();

        IncomingRequest incomingRequest = parser.readFile(file);
        PremiumCalculator calculator = new PremiumCalculator();

        return new Response(calculator.calculate(incomingRequest));
    }
}
