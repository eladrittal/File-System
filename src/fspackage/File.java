package fspackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class File extends  FileInput{

    String currFile;
    String root;
    Map<String, List> filesMap = new HashMap<>();

    public File(String currFile, String root, Map<String, List> filesMap) {
        this.currFile = currFile;
        this.root = root;
        this.filesMap = filesMap;
    }

    public File() {
    }

    public Map<String, List> create()
    {
        List tempList = new ArrayList();
        if (filesMap.isEmpty()) {
            tempList.add(currFile);
            filesMap.put(root, tempList);
        }
        if (filesMap.containsKey(root))
        {
            if (!filesMap.get(root).contains(currFile) )
            {
                tempList = filesMap.get(root);
                tempList.add(currFile);
                filesMap.put(root,tempList);
            }

        }
        else
        {
            tempList.add(currFile);
            filesMap.put(root,tempList);
        }
        return  filesMap;
    }

    public boolean validateName()
    {
        Pattern p = Pattern.compile("^[\\w\\-. ]+$");
        Matcher m = p.matcher(currFile);
        boolean isMatch = m.matches();
        return  isMatch;
    }
}

