package com.starxmind.piano.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.*;
import com.starxmind.bass.io.core.IOUtils;
import com.starxmind.bass.sugar.Asserts;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Integration with OSS
 *
 * @author Xpizza
 * @since piano1.0
 */
public class OSSClient implements AutoCloseable {
    /**
     * OSS connection info
     */
    @Getter
    private OSSConnectionInfo oSSConnectionInfo;

    /**
     * Aliyun OSSClient
     */
    private OSS ossClient;

    /**
     * Constructor with OSSConnectionInfo
     *
     * @param oSSConnectionInfo OSSConnectionInfo
     */
    public OSSClient(OSSConnectionInfo oSSConnectionInfo) {
        this.oSSConnectionInfo = oSSConnectionInfo;
//        ossClient = new OSSClient(oSSConnectionInfo.getEndpoint(), oSSConnectionInfo.getAccessKey(), oSSConnectionInfo.getSecureKey());
        ossClient = new OSSClientBuilder().build(oSSConnectionInfo.getEndpoint(), oSSConnectionInfo.getAccessKey(), oSSConnectionInfo.getSecureKey());
    }

    /**
     * Close releases
     *
     * @throws Exception Exception during close
     */
    @Override
    public void close() throws Exception {
        if (ossClient != null) {
            try {
                ossClient.shutdown();
            } catch (Throwable thr) {
                // close quietly
            }
        }
    }

    /**
     * determine an object of a bucket exists
     *
     * @param bucketName bucket name
     * @param objectKey  OSS object key
     * @return exists
     */
    public boolean doesObjectExist(String bucketName, String objectKey) {
        return ossClient.doesObjectExist(bucketName, objectKey);
    }

    /**
     * determine an object of a bucket not exists
     *
     * @param bucketName bucket name
     * @param objectKey  OSS object key
     * @return not exists
     */
    public boolean doesObjectNotExist(String bucketName, String objectKey) {
        return !doesObjectExist(bucketName, objectKey);
    }

    /**
     * list objects by conditions
     *
     * @param bucketName   bucket name
     * @param prefix       object name's prefix
     * @param lowestMarker the marker of an object's lowest name, that results must greater than it
     * @param limit        result's limit amount
     * @return objects list that satisfies the conditions
     */
    public List<OSSObjectSummary> listObjects(String bucketName, String prefix, String lowestMarker, int limit) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName, prefix, lowestMarker, null, limit);
        return ossClient.listObjects(listObjectsRequest).getObjectSummaries();
    }

    /**
     * read an object into a input stream
     *
     * @param bucketName bucket name
     * @param objectKey  OSS object key
     * @return input stream from an object int OSS
     */
    public InputStream readObject(String bucketName, String objectKey) {
        OSSObject ossObject = ossClient.getObject(bucketName, objectKey);
        return ossObject.getObjectContent();
    }

    /**
     * upload local file to aliyun's OSS
     *
     * @param bucketName    bucket name
     * @param localFilepath local  file path
     * @param objectKey     oss object key
     * @throws FileNotFoundException
     */
    public void upload(String bucketName, String localFilepath, String objectKey) throws FileNotFoundException {
        File file = new File(localFilepath);
        uploadByStream(bucketName, new FileInputStream(file), objectKey, null, CannedAccessControlList.Private);
    }

    /**
     * upload input stream to aliyun's OSS
     *
     * @param bucketName    bucket name
     * @param inputStream   input stream
     * @param objectKey     oss object key
     * @param contentType   object's content type
     * @param accessControl access control level
     */
    public void uploadByStream(String bucketName,
                               InputStream inputStream,
                               String objectKey,
                               String contentType,
                               CannedAccessControlList accessControl) {
        // 构造上传请求
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey, inputStream);

        // 设置对象的元信息:如访问权限,内容类型等
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setObjectAcl(accessControl);
        if (StringUtils.isNotEmpty(contentType)) {
            metadata.setContentType(contentType);
        }
        putObjectRequest.setMetadata(metadata);

        // 执行上传
        PutObjectResult result = ossClient.putObject(putObjectRequest);
        parseResult(result);
    }

    /**
     * delete OSS objects
     *
     * @param bucketName bucket name
     * @param objectKeys object keys
     */
    public void delete(String bucketName, List<String> objectKeys) {
        if (CollectionUtils.isEmpty(objectKeys)) {
            return;
        }

        try {
            DeleteObjectsResult result = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(objectKeys));
            parseResult(result);
        } catch (Throwable thr) {
            throw new RuntimeException("Fatal: an error occurred while deleting objects", thr);
        }
    }

    /**
     * delete OSS objects by key prefix
     *
     * @param bucketName bucket name
     * @param prefix     OSS object key prefix
     */
    public void deleteByPrefix(String bucketName, String prefix) {
        if (StringUtils.isEmpty(prefix)) {
            throw new RuntimeException("Dangerous operation: cannot delete root");
        }

        String nextMarker = null;
        ObjectListing objectListing;
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName)
                    .withPrefix(prefix)
                    .withMarker(nextMarker);
            objectListing = ossClient.listObjects(listObjectsRequest);

            if (objectListing.getObjectSummaries().size() > 0) {
                List<String> keys = objectListing.getObjectSummaries()
                        .stream()
                        .map(x -> x.getKey())
                        .collect(Collectors.toList());
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys);
                ossClient.deleteObjects(deleteObjectsRequest);
            }
            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());
    }

    /**
     * parse OSS result
     *
     * @param result OSS result
     * @return parsed result
     */
    private String parseResult(GenericResult result) {
        Asserts.notNull(result);

        ResponseMessage response = result.getResponse();
        if (response == null) {
            return null;
        }

        Asserts.isTrue(response.isSuccessful(), response.getErrorResponseAsString());

        try {
            return IOUtils.readStreamAsString(response.getContent());
        } catch (IOException e) {
            throw new RuntimeException("Fatal: an error occurred while parsing result", e);
        }
    }
}
