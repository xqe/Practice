package com.example.xieqe.test001.Bean;

import java.nio.ByteBuffer;

/**
 * Created by xqe on 2018/3/24.
 */

public class BindPackage {
    private byte userNameBytes[];
    private byte passwordBytes[];
    private byte deviceIdListBytes[]; //兼容批量绑定

    private String userName;
    private String password;
    private String[] deviceIds;

    private BindPackage() {
        userNameBytes = new byte[20]; // 用户名不允许使用中文,一个char 2个字节，最多容纳10个char
        passwordBytes = new byte[16]; // 密码，一个char 2个字节，密码为八位数
        deviceIdListBytes = new byte[110];//设备id，11位数，一个deviceId占22个字节，最多同时绑定五台110字节
    }

    private ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(200); //110 + 20 + 16 = 146，预留64字节
        byteBuffer.put(userNameBytes);
        byteBuffer.put(passwordBytes);
        byteBuffer.put(deviceIdListBytes);
        byteBuffer.flip();//转换为输出状态
        return byteBuffer;
    }

    static class Builder {
        private String userName;
        private String password;
        private String[] deviceIds;

        public Builder deviceIds(String[] deviceIds) {
            this.deviceIds = deviceIds;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public ByteBuffer build() {
            BindPackage bindPackage = new BindPackage();
            bindPackage.userName = userName;
            bindPackage.password = password;
            bindPackage.deviceIds = deviceIds;
            return bindPackage.toByteBuffer();
        }
    }
}
