package FSPackage;

import java.util.List;
import java.util.Map;

public abstract class FileInput {

    String root;
    abstract Map<String, List> create();
    abstract boolean validateName();
}
