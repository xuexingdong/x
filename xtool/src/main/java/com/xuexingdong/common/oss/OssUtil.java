package cool.xxd.mapstruct.oss;

import java.io.InputStream;
import java.util.List;

public interface OssUtil {

    void upload(String bucketName, String key, InputStream input);

    void delete(String bucketName, String key);

    List<OssFile> listOssFiles(String bucketName);

}
