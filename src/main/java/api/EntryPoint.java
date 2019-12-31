package api;

public class EntryPoint {
    public static Api testInstance = null;//just to decrease time for investigations how to mock that correctly
    public static void main(String[] args) {
        String filePath = args[0];

        if (testInstance != null) {
            handle(testInstance);
            return;
        }
        //not sure how to test this will delay on later on
        Api executor = getExecutor(filePath);

        handle(executor);
    }

    private static void handle(Api executor) {
        executor.handle();
    }

    private static Api getExecutor(String filePath) {
        return new Api(filePath);
    }
}
