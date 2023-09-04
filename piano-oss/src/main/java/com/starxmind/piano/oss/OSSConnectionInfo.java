package com.starxmind.piano.oss;

import lombok.Builder;
import lombok.Data;

/**
 * OSS connection info
 *
 * @author Xpizza
 * @since piano1.0
 */
@Builder(toBuilder = true)
@Data
public class OSSConnectionInfo {
    private String endpoint;
    private String accessKey;
    private String secureKey;
}
