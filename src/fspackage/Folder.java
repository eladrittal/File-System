package fspackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Folder extends  FileInput {
String currFolder;
String root;
    Map <String, List>  folderMap = new HashMap<>();
    public Folder(String currFolder, String root, Map<String, List> folderMap) {
        this.currFolder = currFolder;
        this.root = root;
        this.folderMap = folderMap;
    }

    public Folder() {

    }
    public Map <String, List> create() {
        List tempList = new ArrayList();
        if (folderMap.isEmpty()) {
            tempList.add(currFolder);
            folderMap.put(root, tempList);
        }

        if (folderMap.containsKey(root))
        {
            if (!folderMap.get(root).contains(currFolder) )
            {
                tempList = folderMap.get(root);
                tempList.add(currFolder);
                folderMap.put(root,tempList);
            }
        }
        else
        {
                tempList.add(currFolder);
                folderMap.put(root,tempList);
        }
        return  folderMap;
    }

    public boolean validateName()
    {
        Pattern p = Pattern.compile("^[\\w\\-. ]+$");
        Matcher m = p.matcher(currFolder);
        boolean isMatch = m.matches();
        return  isMatch;
    }
}
