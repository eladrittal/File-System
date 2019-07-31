package FSPackage;

public class Main {
    static String mainRoot;


    /**
     * @param args
     */
    public static void main(String[] args) {
        String root = System.getProperty("user.dir") + ">";
        mainRoot = root;
        FileSystem fileSystem = new FileSystem(root);
        fileSystem.getAction();

    }
    }
