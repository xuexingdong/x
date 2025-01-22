package cool.xxd.mapstruct.oss.impl;

import com.aliyun.oss.OSS;
import cool.xxd.mapstruct.oss.OssFile;
import cool.xxd.mapstruct.oss.OssUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "alibaba.cloud.oss",
        name = {"enabled"},
        matchIfMissing = true
)
public class AliyunOssClient implements OssUtil {

    private final OSS ossClient;

    @Override
    public void upload(String bucketName, String key, InputStream input) {
        ossClient.putObject(bucketName, key, input);
    }

    @Override
    public void delete(String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }

    @Override
    public List<OssFile> listOssFiles(String bucketName) {
        var objectListing = ossClient.listObjects(bucketName);
        var objectSummaries = objectListing.getObjectSummaries();
        return objectSummaries.stream().map(ossObjectSummary -> {
            var ossFile = new OssFile();
            ossFile.setBucketName(ossObjectSummary.getBucketName());
            ossFile.setKey(ossObjectSummary.getKey());
            return ossFile;
        }).toList();
    }
}
