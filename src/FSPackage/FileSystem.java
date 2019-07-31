package FSPackage;

import java.util.*;

import static FSPackage.Main.mainRoot;

public class FileSystem {
    String root;
    String trimmedInput;
    String folderName;
    String [] splittedBySpace;

    Map<String, List> folderMap = new HashMap<>();   // save hashmap of path and folders. key = path, value = folders list
    Map <String, List>  filesMap = new HashMap<>();  // save hashmap of path and files. key = path, value = files list
    final String VIEW_FILES = "ls";
    final String BACKWARDS = "..";

    enum ACTION {
        CREATE_FILE,
        CREATE_FOLDER,
        OPEN_FOLDER,
        UP_FOLDER,
        VIEW_FILES,
        CLEAR
    }
    ACTION action;

    // get the input from the user and determine which action should be performd
    public void getAction() {
        String input = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(root);
            input = sc.nextLine();
            trimmedInput = input.trim();
            splittedBySpace = trimmedInput.split(" ");
            folderName = concatNameWords(splittedBySpace);
            // check if the input is empty
            if (trimmedInput.equals("\n") || trimmedInput.equals("")) {
                action = ACTION.CLEAR;
            } else if (trimmedInput.toLowerCase().startsWith("mkdir")) {
                action = ACTION.CREATE_FOLDER;
                performAction();
            } else if (trimmedInput.toLowerCase().startsWith("mkfile")) {
                action = ACTION.CREATE_FILE;
                performAction();
            } else if (trimmedInput.toLowerCase().startsWith("cd")) {
                // check if the input command is cd ..
                if (getCDType(trimmedInput).equals(BACKWARDS)) {
                    action = ACTION.UP_FOLDER;
                    performAction();
                    // check if the input contains only dots, and more than 2. if so, ignore
                } else if (checkOnlyDots(splittedBySpace) && checkMoreThanTwo()) {
                    action = ACTION.CLEAR;
                    performAction();
                } else {
                    action = ACTION.OPEN_FOLDER;
                    performAction();
                }
            } else if (trimmedInput.toLowerCase().equals(VIEW_FILES)) {
                action = ACTION.VIEW_FILES;
                performAction();
            } else {
                System.out.println(input + " " + "is not recognized as an internal or external command,\n" +
                        "operable program or batch file.");
            }


        } while (true);

    }

    private void  performAction(){
        try {
            switch (action) {
                case CREATE_FOLDER: {
                    FileInput folder = new Folder(folderName, root, folderMap);
                    if (folder.validateName()) {
                        folderMap = folder.create();
                    }
                    action = ACTION.CLEAR;
                    break;
                }
                case CREATE_FILE: {
                    String fileName = concatNameWords(splittedBySpace);
                    FileInput file = new File(fileName, root, filesMap);
                    if (file.validateName()) {
                        filesMap = file.create();
                    }
                    action = ACTION.CLEAR;
                    break;
                }
                case OPEN_FOLDER: {
                    boolean isFolderExists = folderMap.get(root) != null && folderMap.get(root).contains(folderName);
                    if (isFolderExists) {
                        setRootForward(folderName);
                    } else {
                        System.out.println("The system cannot find the path specified.");
                    }
                    action = ACTION.CLEAR;
                    break;
                }
                case UP_FOLDER: {
                    if (!root.equals(mainRoot)) {

                        setRootBackward();
                    }
                    action = ACTION.CLEAR;
                    break;
                }
                case VIEW_FILES: {

                    printAllSubFoldersAndFiles();
                    action = ACTION.CLEAR;
                    break;
                }
                default: {
                    action = ACTION.CLEAR;
                    break;
                }
            }
        } catch (Exception e) {
            // continue working
        }


    }
    // return the type of cd action, backwards (..) or forward (cd name)
    private String getCDType(String input)
    {
        if (input.toLowerCase().split("cd")[1].trim().equals(".."))
            return BACKWARDS;
        else
            return input.toLowerCase().split("cd")[1];
    }
    // set the current path forward to the new path, including folder name
    private void setRootForward(String folderName)
    {
        root = root.substring(0, root.length() - 1) +"\\" + folderName + ">";
    }
    // set the current path backwards to the new path, removing the lase folder name
    private void setRootBackward()
    {
        String [] splittedArray;
        root = root.substring(0, root.length() - 1);
        splittedArray = root.split("\\\\");
        root = root.substring(0,root.length()-splittedArray[splittedArray.length-1].length()-1) + ">";
    }

    public FileSystem(String root) {
        this.root = root;
    }

    private void printAllSubFoldersAndFiles()
    {
        List<String> subFolders = new ArrayList<>();
        List<String> subFiles = new ArrayList<>();
        for(Map.Entry<String, List> folder : folderMap.entrySet()) {
            if (folder.getKey().equals(root))
            {
                subFolders = folder.getValue();
            }
        }
        for(Map.Entry<String, List> folder : filesMap.entrySet()) {
            if (folder.getKey().equals(root))
            {
                subFiles = folder.getValue();
            }
        }
        if (subFolders.isEmpty() && subFiles.isEmpty())
        {
            System.out.println("" +
                    "0 File(s)              0 bytes");
        }
        for (String folder:subFolders) {
            System.out.println(folder + "\\");
        }
        for (String file:subFiles) {
            System.out.println(file);
        }
    }
    // returns true if the input contains only dots
    private boolean checkOnlyDots(String[] splittedBySpace)
    {
        String folderName = concatNameWords(splittedBySpace);
        folderName = folderName.replaceAll("\\.", "");
        if (folderName.length() == 0)
            return true;
        else
            return false;
    }
    // concat the input words separated by space. if it's the lase word it doesn't concat space after.
    private String concatNameWords(String [] splittedBySpace)
    {
        String file = "";
        for (int i=1; i< splittedBySpace.length; i++)
        {
            if (splittedBySpace[i] != "") {
                if (i != splittedBySpace.length -1 ) {
                    file += splittedBySpace[i] + " ";
                }
                else
                {
                    file += splittedBySpace[i];
                }
            }
        }
        return  file.trim();
    }

    // return true if the input contains more than 2 dots, false otherwise
    private boolean checkMoreThanTwo()
    {
        return getCDType(trimmedInput).length() >2;
    }

    }


