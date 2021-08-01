package com.atu.monitor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Tom
 * @Date: 2021/7/29 4:39 下午
 * @Description: docker连接、操作工具类
 */
@Slf4j
public class DockerClientUtil {
    public DockerClient connectDocker() {
        /**
         * 连接docker服务器(安全认证方式)
         * @return DockerClient
         * @param  dockerHost：docker服务器ip地址和端口号
         *         dockercertPath：windows的密钥文件存放地址
         *         dockerconfig：同Path，配置地址
         *         apiVersion：dockerAPI的版本，通过docker version命令在docker服务器上获取版本号
         */
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerTlsVerify(true)
                .withDockerCertPath("D:/docker-java/").withDockerHost("tcp://192.168.184.128:2375")
                .withDockerConfig("D:/docker-java/").withApiVersion("1.40").withRegistryUrl("https://index.docker.io/v1/")
                .withRegistryUsername("dockeruser").withRegistryPassword("ilovedocker")
                .withRegistryEmail("dockeruser@github.com").build();
        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                .withReadTimeout(1000)
                .withConnectTimeout(1000)
                .withMaxTotalConnections(100)
                .withMaxPerRouteConnections(10);
        // 连接
        DockerClient dockerClient = DockerClientBuilder.getInstance(config).withDockerCmdExecFactory(dockerCmdExecFactory).build();
        Info info = dockerClient.infoCmd().exec();
        log.info("docker的环境信息如下：=================", JSONObject.toJSON(info));
        return dockerClient;
    }

    /**
     * 创建容器
     *
     * @param client,imageName
     * @return
     */
    public CreateContainerResponse createContainers(DockerClient client, String imageName) {
        // 暴露的端口
        ExposedPort tcp80 = ExposedPort.tcp(5000);
        Ports portBindings = new Ports();
        // 绑定主机随机端口 -> docker服务器5000端口
        portBindings.bind(tcp80, Ports.Binding.empty());
        CreateContainerResponse container = client.createContainerCmd(imageName)
                .withPortBindings(portBindings)
                .withExposedPorts(tcp80).exec();
        return container;
    }

    /**
     * 启动容器
     *
     * @param client,containerId
     * @param containerId
     */
    public void startContainer(DockerClient client, String containerId) {
        client.startContainerCmd(containerId).exec();
    }

    /**
     * 关闭容器
     *
     * @param client,containerId
     * @param containerId
     */
    public void stopContainer(DockerClient client, String containerId) {
        client.stopContainerCmd(containerId).exec();
    }

    /**
     * 删除容器
     *
     * @param client,containerId
     * @param containerId
     */
    public void removeContainer(DockerClient client, String containerId) {
        client.removeContainerCmd(containerId).exec();
    }

    /**
     * 删除镜像
     *
     * @param client,imageId
     * @return
     */
    public void removeImage(DockerClient client, String imageId) {
        client.removeImageCmd(imageId).exec();
    }

    public static void main(String[] args) {
        DockerClient dockerClient = DockerClientBuilder.getInstance("http://10.104.6.208:2376").build();
        Info info = dockerClient.infoCmd().exec();
        System.out.print(info);
    }

}

