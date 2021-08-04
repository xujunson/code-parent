package com.atu.monitor.utils;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Tom
 * @Date: 2021/7/29 4:39 下午
 * @Description: docker连接、操作工具类
 */
@Slf4j
public class DockerClientUtil {
    private static String stats;

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
                .withDockerCertPath("D:/docker-java/").withDockerHost("tcp://192.168.137.140:2375")
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

        return dockerClient;
    }

    public DockerClient connectDockerAndLog() {
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://192.168.137.140:2375").build();
        Info info = dockerClient.infoCmd().exec();

        log.info("docker的环境信息如下:{}", JSONObject.toJSONString(info));
        System.out.println(JSONObject.toJSONString(info));
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

    public void stat() {

        new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("开始监控程序...");
                    String statCmd = "docker stats ";
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(Runtime.getRuntime().exec(statCmd).getInputStream()));
                    // StringBuffer b = new StringBuffer();
                    String line = null;
                    StringBuffer b = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        if (line.indexOf("NAME") != -1 && b.length() != 0) {
                            stats = b.toString();
                            System.out.println("监控结果：" + stats);
                            b.delete(0, b.length());
                            b.append(
                                    "<span style='background-color: #0033dd;font-size:20px'>CONTAINER ID        NAME             CPU %           MEM USAGE / LIMIT     MEM %               NET I/O             BLOCK I/O     PIDS</span><br/>");
                        } else {
                            b.append(line + "<br/>");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //statsbegin = true;

    }


    public static void main(String[] args) {
        /*DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://192.168.137.140:2375").build();
        Info info = dockerClient.infoCmd().exec();
        System.out.print(info);*/
        DockerClientUtil dockerClientService = new DockerClientUtil();
        //连接docker服务器
        DockerClient client = dockerClientService.connectDockerAndLog();
        ResultCallback<Statistics> result = client.statsCmd("bd77fab91456").exec(new ResultCallback<Statistics>() {

            @Override
            public void close() throws IOException {

            }

            @Override
            public void onStart(Closeable closeable) {

            }

            @Override
            public void onNext(Statistics statistics) {
                log.info("statistics", JSONObject.toJSONString(statistics));
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
            }
        });
        System.out.println(JSONObject.toJSONString(result));
    }

}

