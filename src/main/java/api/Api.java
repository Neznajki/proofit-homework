package api;

import api.factory.ResponseFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import data.object.IncomingRequest;
import json.Parser;
import helper.FileSystem;
import helper.Debug;
import task.PremiumCalculator;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Api {
    protected String filePath;
    protected FileSystem fileSystem;
    protected PrintStream out;
    protected ResponseFactory responseFactory;
    protected Debug debug;
    protected Parser parser;


    public Api(String filePath)
    {
        this.filePath = filePath;
        this.fileSystem = new FileSystem();
        this.out = System.out;
        this.responseFactory = new ResponseFactory();
        this.debug = new Debug();
        this.parser = new Parser();
    }

    public void handle() {
        File file = createFile();

        try {
            if (file.isDirectory()) {
                handleDirectory(file).dispatchResponse();
            } else {
                handleFile(file).dispatchResponse();
            }
        } catch (Exception e) {
            this.getDebug().handleException(e);
            Response errorResponse = this.getResponseFactory().createResponse(e);
            try {
                errorResponse.dispatchResponse();
            } catch (JsonProcessingException ex) {
                this.getDebug().handleException(ex);
                this.getOut().println("API broken please report");
            }
        }
    }

    protected Response handleDirectory(File file) throws Exception {
        boolean handled = false;

        List<File> files = this.getFileSystem().getAllDirectoryFiles(file, false);
        List<Response> responses = new ArrayList<>();

        for (File currentFile : files) {
            try {
                responses.add(handleFile(currentFile));

                handled = true;
            } catch (Exception e) {
                this.getDebug().handleException(e);
                responses.add(this.getResponseFactory().createResponse(e));
            }
        }

        if (!handled) {
            throw new Exception(String.format("there are no valid files in folder : %s", file.getAbsolutePath()));
        }

        return this.getResponseFactory().createResponse(responses);
    }

    protected Response handleFile(File file) throws Exception {
        if (!file.getName().matches(".*\\.json")) {
            throw new Exception(String.format("file should be json : %s", file.getAbsolutePath()));
        }

        IncomingRequest incomingRequest = this.getParser().readFile(file);

        return this.getResponseFactory().createResponse(incomingRequest, this.getCalculator());
    }

    protected File createFile() {
        return new File(this.filePath);
    }

    protected FileSystem getFileSystem() {
        return fileSystem;
    }

    protected PrintStream getOut() {
        return out;
    }

    protected ResponseFactory getResponseFactory() {
        return responseFactory;
    }

    protected Debug getDebug() {
        return debug;
    }

    protected Parser getParser() {
        return parser;
    }

    protected PremiumCalculator getCalculator() {
        return new PremiumCalculator();
    }
}
