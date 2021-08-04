package com.atu.monitor.utils;

import com.alibaba.fastjson.JSONObject;
import com.atu.monitor.check.vo.ContainerStatisticsInfo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Author: Tom
 * @Date: 2021/7/29 4:39 下午
 * @Description: docker连接、操作工具类
 */
@Slf4j
public class DockerClientUtil {

    public DockerClient connectDocker() {
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
        Info info = dockerClient.infoCmd().exec();

        log.info("docker的环境信息如下:{}", JSONObject.toJSONString(info));
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

        DockerClientUtil dockerClientService = new DockerClientUtil();

        //连接docker服务器
        DockerClient client = dockerClientService.connectDocker();
        ContainerStatisticsInfo statistics = ContainerStatisticsInfo.builder().build();
        ResultCallback<Statistics> resultCallback = new ResultCallback<Statistics>() {

            @Override
            public void close() throws IOException {
            }

            @Override
            public void onStart(Closeable closeable) {
            }

            @Override
            public void onNext(Statistics object) {
                statistics.setStatistics(object);

            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
            }
        };
        client.statsCmd("bd77fab91456").exec(resultCallback);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(JSONObject.toJSONString(statistics));
    }
}

